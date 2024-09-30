package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Bishop extends Piece {

    protected Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Bispo";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        Direction direction = Direction.create(this.position, square);
        return !(direction == null) && direction.isDiagonal() && board.isPathClear(this.position, square);
    }

    @Override
    public Bishop clone() {
        return (Bishop) super.clone();
    }

}
