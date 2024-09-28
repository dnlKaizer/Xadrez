package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class King extends Piece {

    protected King(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Rei";
    }

    @Override
    public King clone() {
        return (King) super.clone();
    }

}
