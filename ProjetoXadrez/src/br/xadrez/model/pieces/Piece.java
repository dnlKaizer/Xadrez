package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

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
