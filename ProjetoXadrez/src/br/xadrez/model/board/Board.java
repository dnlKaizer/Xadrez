package br.xadrez.model.board;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.pieces.*;

public class Board {
    // Tabuleiro
    private Piece[][] board;
    // Referências dos reis
    private King whiteKing;
    private King blackKing;
    // Lista de peças do jogo
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    // Lista de peças que foram capturadas
    private List<Piece> capturedWhitePieces;
    private List<Piece> capturedBlackPieces;
    // Cor do turno
    private Color turn;
    // Coluna do peão que se moveu 2 casas
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

    /**
     * Retorna uma peça a partir da posição.
     * 
     * @param position {@code Position} da peça
     * @return {@code Piece} na posição do parâmetro ou {@code null} se a posição não for válida ou não tiver peça
      */
    private Piece getPieceNotCloned(Position position) {
        if (position == null) return null;
        Piece piece = board[position.getRow()][position.getCol()];
        return piece;
    }

    /**
     * Verifica se não existe peça bloqueando o caminho
     * e a posição de destino.
     * 
     * @param from {@code Position} da peça
     * @param to {@code Position} de destino
     * @return {@code true} se tudo estiver livre, {@code false} se não
      */
    public boolean isPathAndDestinationClear(Position from, Position to) {
        return (isPathClear(from, to) && isDestinationClear(from, to));
    }

    /**
     * Verifica se o caminho está livre.
     * 
     * @param from {@code Position} da peça
     * @param to {@code Position} de destino
     * @return {@code true} se o caminho está livre, {@code false} se não
      */
    public boolean isPathClear(Position from, Position to) {
        // Vê se existe uma direção entre as posições
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
     * Verifica se existe uma peça de mesma cor no destino.
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

    /**
     * Retorna uma lista de peças que
     * estão aplicando um xeque no rei.
     * 
     * @param color {@code Color} do rei
     * @return {@code List<Piece>} lista
      */
    public List<Piece> getPiecesChecking(Color color) {
        List<Piece> piecesChecking = new ArrayList<>();
        List<Piece> pieces;
        King king;
        // Seleciona o rei da cor e as peças da cor oposta
        if (color.isWhite()) {
            king = whiteKing;
            pieces = blackPieces;
        } else {
            king = blackKing;
            pieces = whitePieces;
        }
        // Verifica se as peças estão atacando o rei da cor
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
        // Seleciona o rei da cor e as peças da cor oposta
        if (color.isWhite()) {
            king = whiteKing;
            pieces = blackPieces;
        } else {
            king = blackKing;
            pieces = whitePieces;
        }
        // Verifica se pelo menos uma das peças está atacando o rei
        return isAtLeastOnePieceAttacking(king.getPosition(), pieces);
    }

    /**
     * Verifica se existe pelo menos uma peça
     * atacando uma posição.
     * 
     * @param position {@code Position} a ser analisada
     * @param pieces {@code List<Piece>} lista de peças
     * que será verificada
     * @return {@code true} se pelo menos uma peça está
     * atacando a posição, {@code false} se não
      */
    private boolean isAtLeastOnePieceAttacking(Position position, List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.isAttacking(position, this)) return true;
        }
        return false;
    }

    /**
     * Verifica se o rei está em
     * um xeque-mate.
     * 
     * @param color {@code Color} do rei
     * @return {@code true} se foi mate,
     * {@code false} se não
      */
    public boolean isCheckMate(Color color) {
        King king;
        List<Piece> pieces;
        // Seleciona o rei e as peças da cor
        if (color.isWhite()) {
            king = whiteKing;
            pieces = whitePieces;
        } else {
            king = blackKing;
            pieces = blackPieces;
        }
        // Verifica se o rei pode se mover
        if (!king.getValidMoves(this).isEmpty()) return false;
        // Verifica se alguma peça da cor do rei pode se mover
        for (Piece piece : pieces) {
            if (!piece.getValidMoves(this).isEmpty()) return false;
        }
        return true;
    }

    /**
     * Verifica se o movimento é válido
     * e depois o realiza. Também faz a
     * troca de turnos.
     * 
     * @param piece {@code Piece} que fará
     * o movimento
     * @param to {@code Position} de destino
      */
    public void move(Piece piece, Position to) {
        if (piece == null) return;
        // Verifica se o movimento está entre os movimentos válidos da peça
        if (piece.getValidMoves(this).contains(to)) {
            // Troca de turnos
            if (turn.isWhite()) turn = Color.BLACK;
            else turn = Color.WHITE;
            // Realiza o movimento
            makeMove(piece, to);
        }
    }

    /**
     * Realiza o movimento da peça.
     * 
     * @param piece {@code Piece} que se moverá
     * @param newPosition {@code Position} de destino
      */
    private void makeMove(Piece piece, Position newPosition) {
        // Guarda a peça no destino (pode ser null)
        Piece pieceAtNewPosition = getPieceNotCloned(newPosition);
        // Guarda a posição atual da peça que irá se mover
        Position currentPosition = piece.getPosition();
        // Procura a referência da peça (que vai mover) no tabuleiro 
        piece = getPieceNotCloned(currentPosition);
        // Posição atual da peça recebe null (remove a peça)
        this.board[currentPosition.getRow()][currentPosition.getCol()] = null;

        // Verifica se é um movimento de roque
        if (piece instanceof King && !piece.getPosition().isNear(newPosition)) {
            // Faz o roque na ala do rei ou da dama com base na coluna do rei e da nova posição
            if (newPosition.getCol() > piece.getPosition().getCol()) castleKingSide(piece.getColor());
            else castleQueenSide(piece.getColor());
        }

        // Se a peça for rei ou torre que não se mexeu, atualiza o estado deles
        if (piece instanceof King && !((King) piece).hasMoved()) ((King) piece).setHasMoved(true);
        if (piece instanceof Rook && !((Rook) piece).hasMoved()) ((Rook) piece).setHasMoved(true);

        // Verifica se a peça é um peão
        if (piece instanceof Pawn) {
            // Guarda a direção de movimento do peão
            Direction direction = ((Pawn)piece).getMoveDirection();
            // Pega a posição que está 2 casas à frente do peão
            Position position = direction.getNextPosition(direction.getNextPosition(currentPosition));
            // Se essa posição for a posição de destino, atualiza a variável enPassantCol com a coluna do peão
            if (position.equals(newPosition)) enPassantCol = newPosition.getCol();
            else enPassantCol = -1;
        } else enPassantCol = -1;
        
        // Se tiver uma posição no destino, captura
        if (pieceAtNewPosition != null) capture(pieceAtNewPosition);
        // Coloca a peça na nova posição e atualiza seu estado
        this.board[newPosition.getRow()][newPosition.getCol()] = piece;
        piece.setPosition(newPosition);
    }

    /**
     * Captura a peça.
     * 
     * @param piece {@code Piece} peça capturada
      */
    public void capture(Piece piece) {
        // Verifica a cor
        // Remove da lista de peças no jogo
        // Adiciona na lista de peças capturadas
        if (piece.getColor().isWhite()) {
            this.whitePieces.remove(piece);
            this.capturedWhitePieces.add(piece);
        } else {
            this.blackPieces.remove(piece);
            this.capturedBlackPieces.add(piece);
        }
        // Atualiza o estado da peça
        piece.setPosition(null);
    }

    /**
     * Verifica se um movimento é válido.
     * Cria um novo board, faz o movimento
     * e verifica se o rei ficou em xeque.
     * 
     * @param piece {@code Piece} que vai se mover
     * @param newPosition {@code Position} de destino
     * @return {@code true} se for um movimento
     * válido, {@code false} se não
      */
    public boolean isMoveValid(Piece piece, Position newPosition) {
        // Copia o tabuleiro
        Board newBoard = copyBoard();
        // Pega a referência da peça no novo tabuleiro
        Piece newPiece = newBoard.getPieceNotCloned(piece.getPosition());
        // Faz o movimento
        newBoard.makeMove(newPiece, newPosition);
        // Verifica se o rei ficou em xeque depois do movimento
        boolean isMoveValid = !(newBoard.isInCheck(piece.getColor()));
        return isMoveValid;
    }

    /**
     * Retorna uma cópia do tabuleiro.
     * 
     * @return {@code Board} tabuleiro
      */
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
    

    /**
     * Retorna a torre da ala do rei se
     * ela estiver na posição inicial.
     * 
     * @param color {@code Color} da torre
     * @return {@code Rook} torre se estiver na
     * posição inicial, {@code null} se não
      */
    private Rook getKingSideRook(Color color) {
        Piece piece;
        if (color.isWhite()) piece = getPieceNotCloned(Position.create(7, 7));
        else piece = getPieceNotCloned(Position.create(0, 7));
        if (piece == null || !(piece instanceof Rook)) return null;
        else return (Rook) piece;
    }

    /**
     * Analisa se a torre da ala do rei
     * pode realizar o roque. Verifica se
     * ela já se mexeu e se as posições que
     * o rei passa estão sendo atacadas.
     * 
     * @param color {@code Color} da torre
     * @return {@code true} se pode fazer
     * o roque, {@code false} se não
      */
    public boolean canKingSideRookCastle(Color color) {
        // Guarda as referências do rei e da torre
        King king = color.isWhite() ? whiteKing : blackKing;
        Rook rook = getKingSideRook(color);
        // Guarda as posições por onde o rei passa
        List<Position> positions = new ArrayList<>();
        positions.add(Direction.RIGHT.getNextPosition(king.getPosition()));
        positions.add(Direction.RIGHT.getNextPosition(positions.get(0)));
        // Verifica as posições por onde o rei passa
        for (Position position : positions) {
            // Se tiver uma peça, retorna false
            if (getPieceAt(position) != null) return false;
            // Se tiver pelo menos uma peça atacando, retorna false
            if (isAtLeastOnePieceAttacking(position, blackPieces)) return false;
        }
        // Verifica se a torre está na posição incial e se ela se moveu
        if (rook == null) return false;
        else return !rook.hasMoved();
    }

    /**
     * Faz o movimento de roque da
     * torre da ala do rei.
     * 
     * @param color {@code Color} da torre
      */
    public void castleKingSide(Color color) {
        Rook rook = getKingSideRook(color);
        makeMove(rook, Position.create(rook.getPosition().getRow(), 5));
    }
    
    /**
     * Retorna a torre da ala da dama se
     * ela estiver na posição inicial.
     * 
     * @param color {@code Color} da torre
     * @return {@code Rook} torre se estiver na
     * posição inicial, {@code null} se não
      */
    private Rook getQueenSideRook(Color color) {
        Piece piece;
        if (color.isWhite()) piece = getPieceNotCloned(Position.create(7, 0));
        else piece = getPieceNotCloned(Position.create(0, 0));
        if (piece == null || !(piece instanceof Rook)) return null;
        else return (Rook) piece;
    }

    /**
     * Analisa se a torre da ala da dama
     * pode realizar o roque. Verifica se
     * ela já se mexeu e se as posições que
     * o rei passa estão sendo atacadas.
     * 
     * @param color {@code Color} da torre
     * @return {@code true} se pode fazer
     * o roque, {@code false} se não
      */
    public boolean canQueenSideRookCastle(Color color) {
        // Guarda as referências do rei e da torre
        King king = color.isWhite() ? whiteKing : blackKing;
        Rook rook = getQueenSideRook(color);
        // Guarda as posições por onde o rei passa
        List<Position> positions = new ArrayList<>();
        positions.add(Direction.LEFT.getNextPosition(king.getPosition()));
        positions.add(Direction.LEFT.getNextPosition(positions.get(0)));
        // Verifica as posições por onde o rei passa
        for (Position position : positions) {
            // Se tiver uma peça, retorna false
            if (position != null) return false;
            // Se tiver pelo menos uma peça atacando, retorna false
            if (isAtLeastOnePieceAttacking(position, blackPieces)) return false;
        }
        // Verifica se a torre está na posição incial e se ela se moveu
        if (rook == null) return false;
        else return !rook.hasMoved();
    }

    /**
     * Faz o movimento de roque da
     * torre da ala do rei.
     * 
     * @param color {@code Color} da torre
      */
    public void castleQueenSide(Color color) {
        Rook rook = getQueenSideRook(color);
        makeMove(rook, Position.create(rook.getPosition().getRow(), 3));
    }

}
