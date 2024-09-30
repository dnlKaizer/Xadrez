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
    private int enPassantCol;
    
    protected Board(Piece[][] board, King whiteKing, King blackKing, List<Piece> whitePieces, List<Piece> blackPieces) {
        this.board = board;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.capturedWhitePieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.enPassantCol = -1;
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

    /**
     * Verifica se não existe peça bloqueando o caminho.
     * 
     * @param from {@code Position} da peça
     * @param to {@code Position} de destino
     * @return {@code true} se o caminho está livre, {@code false} se não
      */
    public boolean isPathClear(Position from, Position to) {
        Direction direction = Direction.create(from, to);
        if (direction == null) return false;

        // Lógica para verificar os caminhos intermediários
        Position between = direction.getNextPosition(from);
        while (!to.equals(between)) {
            if (getPieceAt(between) != null) return false;
            between = direction.getNextPosition(between);
        }
        
        if (isDestinationClear(from, to)) return true;
        else return false;
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
        Piece pieceFrom = getPieceAt(from);
        Piece pieceTo = getPieceAt(to);
        return pieceTo == null || (!pieceFrom.getColor().equals(pieceTo.getColor()));
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
        for (Piece piece : pieces) {
            if (piece.isAttacking(king.getPosition(), this)) return true;
        }
        return false;
    }

    public void move(Position from, Position to) {
        Piece piece = getPieceAt(from);
        if (piece == null) return;
        if (piece.getValidMoves(this).contains(to)) {
            System.out.println(to.toString());
            makeMove(piece, to);
        };
    }

    private void makeMove(Piece piece, Position newPosition) {
        Piece pieceAtNewPosition = getPieceAt(newPosition);
        Position currentPosition = piece.getPosition();
        this.board[currentPosition.getRow()][currentPosition.getCol()] = null;

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
        newBoard.makeMove(piece, newPosition);
        boolean isMoveValid = !(newBoard.isInCheck(piece.getColor()));
        return isMoveValid;
    }

    private Board copyBoard() {
        Piece[][] newBoard = new Piece[8][8];
        King newWhiteKing = this.whiteKing.clone();
        King newBlackKing = this.blackKing.clone();
        List<Piece> newWhitePieces = new ArrayList<>();
        List<Piece> newBlackPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = this.board[i][j];
                if (piece != null) {
                    piece = piece.clone();
                    if (piece.getColor().isWhite()) newWhitePieces.add(piece);
                    else newBlackPieces.add(piece);
                }
                newBoard[i][j] = piece;
            }
        }
        return new Board(newBoard, newWhiteKing, newBlackKing, newWhitePieces, newBlackPieces);
    }
    
}
