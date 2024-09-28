package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class Queen extends Piece {

    protected Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Dama";
    }

    @Override
    public Queen clone() {
        return (Queen) super.clone();
    }

}
