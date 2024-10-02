package br.xadrez.model.board;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.pieces.*;

public class BoardBuilder {
    private Piece[][] board;
    private King whiteKing;
    private King blackKing;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private Color turn;

    public BoardBuilder() {
        this.board = new Piece[8][8];
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.turn = Color.WHITE;
    }

    /**
     * Cria um tabuleiro no formato padrão.
     * 
     * @return {@code Board} no formato padrão
      */
    public Board buildStandard() {
        standard();
        return config();
    }

    /**
     * Cria um tabuleiro a partir das peças 
     * adicionadas.
     * 
     * @return {@code Board} se for um tabuleiro
     * válido, {@code null} se não
     */
    public Board build() {
        if (verifyBoard()) {
            return config();
        } else return null;
    }

    private Board config() {
        List<Piece> whitePieces = new ArrayList<>();
        for (int i = 0; i < whitePieces.size(); i++) {
            whitePieces.add(this.whitePieces.get(i).clone());
        }
        List<Piece> blackPieces = new ArrayList<>();
        for (int i = 0; i < blackPieces.size(); i++) {
            blackPieces.add(this.blackPieces.get(i).clone());
        }
        return new Board(
            board.clone(),
            whiteKing.clone(),
            blackKing.clone(),
            whitePieces,
            blackPieces,
            turn
        );
    }

    private boolean verifyBoard() {
        if (whiteKing == null || blackKing == null) return false;
        if (whiteKing.getPosition().isNear(blackKing.getPosition())) return false;
        return true;
    }

    /**
     * Coloca todas as peças na posição inicial padrão.
      */
    public void standard() {
        this.board = new Piece[8][8];
        List<Class<? extends Piece>> piecesClasses = Arrays.asList(
            Rook.class, Knight.class, Bishop.class, Queen.class,
            King.class, Bishop.class, Knight.class, Rook.class
        );
        placeRowPieces(7, Color.WHITE, piecesClasses);
        placeRowPieces(0, Color.BLACK, piecesClasses);
        placeRowPawns(6, Color.WHITE);
        placeRowPawns(1, Color.BLACK);
    }

    /**
     * Coloca um conjunto de peões em uma linha.
     * 
     * @param row {@code int} coordenada da linha
     * @param color {@code Color} cor das peças
      */
    public void placeRowPawns(int row, Color color) {
        for (int col = 0; col < 8; col++) {
            placePiece(Position.create(row, col), Pawn.class, color);
        }
    }

    /**
     * Coloca um conjunto de peças em uma linha.
     * As peças são colocadas na ordem da lista
     * de classes.
     * 
     * @param row {@code int} coordenada da linha
     * @param color {@code Color} cor das peças
     * @param piecesClasses {@code List<Class<? extends Piece>>}
     * lista das classes das peças
      */
    public void placeRowPieces(int row, Color color, List<Class<? extends Piece>> piecesClasses) {
        for (int col = 0; col < 8; col++) {
            placePiece(Position.create(row, col), piecesClasses.get(col), color);
        }
    }

    /**
     * Cria e coloca uma peça em função da classe e da cor
     * 
     * @param position {@code Position} da peça
     * @param pieceClass {@code Class<Piece>} da peça
     * @param color {@code Color} da peça
      */
    public void placePiece(Position position, Class<? extends Piece> pieceClass, Color color) {
        placeAtBoard(PieceFactory.createPiece(pieceClass, color), position);
    }

    /**
     * Coloca as peças na posição inicial (Módulo de Editor).
     * Se a peça já tiver em uma posição, ela será removida dela.
     * 
     * @param piece {@code Piece} a ser posicionada
     * @param newPosition {@code Position} da peça
      */
    public void placePiece(Position newPosition, Piece piece) {
        // Remove a peça da posição atual, se houver
        Position currentPosition = piece.getPosition();
        if (currentPosition != null && !currentPosition.equals(newPosition)) {
            board[currentPosition.getRow()][currentPosition.getCol()] = null;
        }

        // Coloca a peça na nova posição
        placeAtBoard(piece, newPosition);
    }

    private void placeAtBoard(Piece piece, Position position) {
        verifyKing(piece, position);
        storePiece(piece);
        board[position.getRow()][position.getCol()] = piece;
        piece.setPosition(position);
    }

    private void verifyKing(Piece piece, Position position) {
        if (!(piece instanceof King)) return;
        
        if (piece.getColor().isWhite()) {
            if (this.whiteKing == null) this.whiteKing = (King) piece;
            else if (!this.whiteKing.equals(piece)) 
                throw new IllegalArgumentException("Inserção inválida: rei branco duplicado");
        } else {
            if (this.blackKing == null) this.blackKing = (King) piece;
            else if (!this.blackKing.equals(piece)) 
                throw new IllegalArgumentException("Inserção inválida: rei preto duplicado");
        }
    }

    private void storePiece(Piece piece) {
        List<Piece> pieces = piece.getColor().isWhite() ? whitePieces : blackPieces;
        int index = pieces.indexOf(piece);
        if (index == -1) pieces.add(piece);
        else pieces.set(index, piece);
    }

    public void changeTurn(Color color) {
        if (color != null) this.turn = color;
    }

}
