package br.xadrez.model;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.pieces.*;

public class Board {
    
    private Piece[][] board;
    
    /**
     * Cria um tabuleiro 8x8
     */
    public Board() {
        board = new Piece[8][8];
    }

    /**
     * Coloca as peças na posição inicial (Módulo de Editor).
     * Se a peça já tiver em uma posição, ela será removida dela.
     * 
     * @param piece {@code Piece} a ser posicionada
     * @param newPosition {@code Position} da peça
      */
    public void setPieceToPosition(Piece piece, Position newPosition) {
        if (piece == null || newPosition == null) return;

        Position currentPosition = piece.getPosition();
        if (currentPosition != null) board[currentPosition.getRow()][currentPosition.getCol()] = null;
        
        board[newPosition.getRow()][newPosition.getCol()] = piece;
    }

    /**
     * Retorna um clone da peça na posição
     * 
     * @param position {@code Position} da peça
     * @return {@code Piece} na posição do parâmetro ou {@code null} se a posição não for válida ou não tiver peça
      */
    public Piece getPieceAt(Position position) {
        Piece piece = board[position.getRow()][position.getCol()];
        if (piece == null) return null;
        else return piece.clone(); 
    }

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

    public boolean isDestinationClear(Position from, Position to) {
        if (from == null || to == null) return false;
        Piece pieceFrom = getPieceAt(from);
        if (pieceFrom == null) return false;
        Piece pieceTo = getPieceAt(to);
        return pieceTo == null || (!pieceFrom.getColor().equals(pieceTo.getColor()));
    }

    /**
     * Método que busca a próxima peça na direção.
     * 
     * @param position Posição de origem
     * @param direction Vetor direção
     * @return {@code Piece} se houver, {@code null} se não
      */
    public Piece getNextPieceFromDirection(Position position, Direction direction) {
        if (position == null || direction == null) return null; 
        while (true) {
            position = direction.getNextPosition(position);
            if (position == null) break;
            Piece piece = getPieceAt(position);
            if (piece != null) return piece;
        }
        return null;
    }

    /**
     * Método que busca a todas as peças na direção.
     * 
     * @param position Posição de origem
     * @param direction Vetor direção
     * @return {@code List<Piece>}, se não tiver peça, retorna lista vazia
      */
    public List<Piece> getAllPiecesFromDirection(Position position, Direction direction) {
        List<Piece> pieces = new ArrayList<>();
        if (position == null || direction == null) return pieces; 
        while (true) {
            Piece piece = getNextPieceFromDirection(position, direction);
            if (piece == null) break;
            pieces.add(piece);
            position = piece.getPosition();
        }
        return pieces;
    }

}
