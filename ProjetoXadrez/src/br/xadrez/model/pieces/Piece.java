package br.xadrez.model.pieces;

import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public abstract class Piece implements Cloneable {
    
    protected Color color;
    protected Position position;

    protected Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        if (position != null) this.position = position;
    }

    public abstract String getName();

    public abstract boolean isAttacking(Position square, Board board);

    public abstract List<Position> getPossibleMoves(Board board);

    public List<Position> getValidMoves(Board board) {
        List<Position> moves = getPossibleMoves(board);
        for (int i = 0; i < moves.size(); i++) {
            Position move = moves.get(i);
            if (!board.isMoveValid(this, move)) {
                moves.remove(move);
                i--;
            } 
        }
        return moves;
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return piece.getColor().equals(this.getColor()) && piece.getPosition().equals(this.getPosition());
    }
    
}
