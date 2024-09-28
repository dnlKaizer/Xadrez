package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class Pawn extends Piece {

    protected Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Peão";
    }

    @Override
    public Pawn clone() {
        return (Pawn) super.clone();
    }

}
