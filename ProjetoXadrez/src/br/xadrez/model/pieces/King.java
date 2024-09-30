package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class King extends Piece {

    protected King(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Rei";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        return false;
    }

    @Override
    public King clone() {
        return (King) super.clone();
    }

}
