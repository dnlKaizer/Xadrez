package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Pawn extends Piece {
    private final List<Direction> attackDirections;

    protected Pawn(Color color, Position position) {
        super(color, position);
        this.attackDirections = new ArrayList<>();
        createAttackDirections(color);
    }

    private void createAttackDirections(Color color) {
        if (color.isWhite()) {
            attackDirections.add(Direction.UP_RIGHT);
            attackDirections.add(Direction.UP_LEFT);
        } else {
            attackDirections.add(Direction.DOWN_RIGHT);
            attackDirections.add(Direction.DOWN_LEFT);
        }   
    }

    @Override
    public String getName() {
        return "Peão";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        Direction direction = Direction.create(this.position, square);
        return !(direction == null) && this.position.isNear(square) && (
            direction.equals(attackDirections.get(0)) ||
            direction.equals(attackDirections.get(1)));
    }

    @Override
    public Pawn clone() {
        return (Pawn) super.clone();
    }

}
