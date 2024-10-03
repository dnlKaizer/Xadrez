package br.xadrez.model.board;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.pieces.*;

public class Board {
    
    private Piece[][] board;
    private King whiteKing;
    private King blackKing;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private List<Piece> capturedWhitePieces;
    private List<Piece> capturedBlackPieces;
    private Color turn;
    private int enPassantCol;
    
    protected Board(Piece[][] board, King whiteKing, King blackKing, List<Piece> whitePieces, List<Piece> blackPieces, Color turn) {
        this.board = board;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.capturedWhitePieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.turn = turn;
        this.enPassantCol = -1;
    }

    public Color getTurn() {
        return this.turn;
    }

    public int getEnPassantCol() {
        return enPassantCol;
    }

    /**
     * Retorna um clone da peça na posição
     * 
     * @param position {@code Position} da peça
     * @return {@code Piece} na posição do parâmetro ou {@code null} se a posição não for válida ou não tiver peça
      */
    public Piece getPieceAt(Position position) {
        if (position == null) return null;
        Piece piece = board[position.getRow()][position.getCol()];
        if (piece == null) return null;
        else return piece.clone(); 
    }

    private Piece getPieceNotCloned(Position position) {
        if (position == null) return null;
        Piece piece = board[position.getRow()][position.getCol()];
        return piece;
    }

    /**
     * Verifica se não existe peça bloqueando o caminho.
     * 
     * @param from {@code Position} da peça
     * @param to {@code Position} de destino
     * @return {@code true} se o caminho está livre, {@code false} se não
      */
    public boolean isPathAndDestinationClear(Position from, Position to) {
        return (isPathClear(from, to) && isDestinationClear(from, to));
    }

    public boolean isPathClear(Position from, Position to) {
        Direction direction = Direction.create(from, to);
        if (direction == null) return false;

        // Lógica para verificar os caminhos intermediários
        Position between = direction.getNextPosition(from);
        while (!to.equals(between)) {
            if (getPieceNotCloned(between) != null) return false;
            between = direction.getNextPosition(between);
        }
        return true;
    }

    /**
     * Verifica se existe uma peça de mesma cor no destino
     * 
     * @param from {@code Position} da peça
     * @param to {@code Position} de destino
     * @return {@code true} se o destino estiver livre ou se 
     * existir peça de cor oposta, {@code false} se não
      */
    public boolean isDestinationClear(Position from, Position to) {
        if (to == null) return false;
        Piece pieceFrom = getPieceNotCloned(from);
        Piece pieceTo = getPieceNotCloned(to);
        return pieceTo == null || (!pieceFrom.getColor().equals(pieceTo.getColor()));
    }

    public List<Piece> getPiecesChecking(Color color) {
        List<Piece> piecesChecking = new ArrayList<>();
        List<Piece> pieces;
        King king;
        if (color.isWhite()) {
            king = whiteKing;
            pieces = blackPieces;
        } else {
            king = blackKing;
            pieces = whitePieces;
        }
        for (Piece piece : pieces) {
            if (piece.isAttacking(king.getPosition(), this)) piecesChecking.add(piece);
        }
        return piecesChecking;
    }

    /**
     * Verifica se o rei da cor está em xeque.
     * 
     * @param color {@code Color} do rei
     * @return {@code true} se estiver em xeque, {@code false} se não
      */
    public boolean isInCheck(Color color) {
        List<Piece> pieces;
        King king;
        if (color.isWhite()) {
            king = whiteKing;
            pieces = blackPieces;
        } else {
            king = blackKing;
            pieces = whitePieces;
        }
        return isAtLeastOnePieceAttacking(king.getPosition(), pieces);
    }

    private boolean isAtLeastOnePieceAttacking(Position position, List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.isAttacking(position, this)) return true;
        }
        return false;
    }

    public boolean isCheckMate(Color color) {
        King king;
        List<Piece> pieces;
        if (color.isWhite()) {
            king = whiteKing;
            pieces = whitePieces;
        } else {
            king = blackKing;
            pieces = blackPieces;
        }
        if (!king.getValidMoves(this).isEmpty()) return false;
        for (Piece piece : pieces) {
            if (!piece.getValidMoves(this).isEmpty()) return false;
        }
        return true;
    }

    public void move(Piece piece, Position to) {
        if (piece == null) return;
        if (piece.getValidMoves(this).contains(to)) {
            if (turn.isWhite()) turn = Color.BLACK;
            else turn = Color.WHITE;
            makeMove(piece, to);
        }
    }

    public void makeMove(Piece piece, Position newPosition) {
        Piece pieceAtNewPosition = getPieceNotCloned(newPosition);
        Position currentPosition = piece.getPosition();
        piece = getPieceNotCloned(currentPosition);
        this.board[currentPosition.getRow()][currentPosition.getCol()] = null;

        if (piece instanceof King && !piece.getPosition().isNear(newPosition)) {
            if (newPosition.getCol() > piece.getPosition().getCol()) castleKingSide(piece.getColor());
            else castleQueenSide(piece.getColor());
        }

        if (piece instanceof King && !((King) piece).hasMoved()) ((King) piece).setHasMoved(true);
        if (piece instanceof Rook && !((Rook) piece).hasMoved()) ((Rook) piece).setHasMoved(true);

        if (piece instanceof Pawn) {
            Direction direction = ((Pawn)piece).getMoveDirection();
            currentPosition = direction.getNextPosition(direction.getNextPosition(currentPosition));
            if (currentPosition.equals(newPosition)) enPassantCol = newPosition.getCol();
            else enPassantCol = -1;
        } else enPassantCol = -1;
        
        if (pieceAtNewPosition != null) capture(pieceAtNewPosition);
        this.board[newPosition.getRow()][newPosition.getCol()] = piece;
        piece.setPosition(newPosition);
    }

    /**
     * Captura a peça.
     * 
     * @param piece {@code Piece} peça capturada
      */
    public void capture(Piece piece) {
        if (piece.getColor().isWhite()) {
            this.whitePieces.remove(piece);
            this.capturedWhitePieces.add(piece);
        } else {
            this.blackPieces.remove(piece);
            this.capturedBlackPieces.add(piece);
        }
        piece.setPosition(null);
    }

    public boolean isMoveValid(Piece piece, Position newPosition) {
        Board newBoard = copyBoard();
        Piece newPiece = newBoard.getPieceNotCloned(piece.getPosition());
        newBoard.makeMove(newPiece, newPosition);
        boolean isMoveValid = !(newBoard.isInCheck(piece.getColor()));
        return isMoveValid;
    }

    public Board copyBoard() {
        Piece[][] newBoard = new Piece[8][8];
        King newWhiteKing = null;
        King newBlackKing = null;
        List<Piece> newWhitePieces = new ArrayList<>();
        List<Piece> newBlackPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = this.board[i][j];
                if (piece != null) {
                    piece = piece.clone();
                    if (piece instanceof King) {
                        if (piece.getColor().isWhite()) newWhiteKing = (King) piece;
                        else newBlackKing = (King) piece;
                    } 
                    else if (piece.getColor().isWhite()) newWhitePieces.add(piece);
                    else newBlackPieces.add(piece);
                }
                newBoard[i][j] = piece;
            }
        }
        return new Board(newBoard, newWhiteKing, newBlackKing, newWhitePieces, newBlackPieces, turn);
    }
    
    private Rook getKingSideRook(Color color) {
        Piece piece;
        if (color.isWhite()) piece = getPieceNotCloned(Position.create(7, 7));
        else piece = getPieceNotCloned(Position.create(0, 7));
        if (piece == null || !(piece instanceof Rook)) return null;
        else return (Rook) piece;
    }

    public boolean canKingSideRookCastle(Color color) {
        King king = color.isWhite() ? whiteKing : blackKing;
        Rook rook = getKingSideRook(color);
        List<Position> positions = new ArrayList<>();
        positions.add(Direction.RIGHT.getNextPosition(king.getPosition()));
        positions.add(Direction.RIGHT.getNextPosition(positions.get(0)));
        for (Position position : positions) {
            if (getPieceAt(position) != null) return false;
            if (isAtLeastOnePieceAttacking(position, blackPieces)) return false;
        }
        if (rook == null) return false;
        else return !rook.hasMoved();
    }

    public void castleKingSide(Color color) {
        Rook rook = getKingSideRook(color);
        makeMove(rook, Position.create(rook.getPosition().getRow(), 5));
    }
    
    private Rook getQueenSideRook(Color color) {
        Piece piece;
        if (color.isWhite()) piece = getPieceNotCloned(Position.create(7, 0));
        else piece = getPieceNotCloned(Position.create(0, 0));
        if (piece == null || !(piece instanceof Rook)) return null;
        else return (Rook) piece;
    }

    public boolean canQueenSideRookCastle(Color color) {
        King king = color.isWhite() ? whiteKing : blackKing;
        Rook rook = getQueenSideRook(color);
        List<Position> positions = new ArrayList<>();
        positions.add(Direction.LEFT.getNextPosition(king.getPosition()));
        positions.add(Direction.LEFT.getNextPosition(positions.get(0)));
        for (Position position : positions) {
            if (position != null) return false;
            if (isAtLeastOnePieceAttacking(position, blackPieces)) return false;
        }
        if (rook == null) return false;
        else return !rook.hasMoved();
    }

    public void castleQueenSide(Color color) {
        Rook rook = getQueenSideRook(color);
        makeMove(rook, Position.create(rook.getPosition().getRow(), 3));
    }

}
