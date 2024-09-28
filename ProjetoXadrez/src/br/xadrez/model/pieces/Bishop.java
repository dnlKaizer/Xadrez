package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class Bishop extends Piece {

    protected Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Bispo";
    }

    @Override
    public Bishop clone() {
        return (Bishop) super.clone();
    }

}
