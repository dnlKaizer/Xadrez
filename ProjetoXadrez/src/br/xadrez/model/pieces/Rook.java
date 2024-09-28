package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class Rook extends Piece {

    protected Rook(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Torre";
    }

    @Override
    public Rook clone() {
        return (Rook) super.clone();
    }

}
