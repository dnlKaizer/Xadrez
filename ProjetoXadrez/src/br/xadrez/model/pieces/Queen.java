package br.xadrez.model.pieces;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Queen extends Piece {

    protected Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Dama";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        Direction direction = Direction.create(this.position, square);
        return !(direction == null) && board.isPathClear(this.position, square);
    }

    @Override
    public Queen clone() {
        return (Queen) super.clone();
    }

}
