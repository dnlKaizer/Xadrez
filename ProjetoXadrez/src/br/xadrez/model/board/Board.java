package br.xadrez.model.board;

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
    
    protected Board(Piece[][] board, King whiteKing, King blackKing, List<Piece> whitePieces, List<Piece> blackPieces) {
        this.board = board;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
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

}
