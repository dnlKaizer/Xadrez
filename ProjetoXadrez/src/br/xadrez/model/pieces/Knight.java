package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class Knight extends Piece {

    protected Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Cavalo";
    }

    @Override
    public Knight clone() {
        return (Knight) super.clone();
    }

}
