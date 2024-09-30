package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Pawn extends Piece {
    private final Direction moveDirection;
    private final List<Direction> attackDirections;

    protected Pawn(Color color, Position position) {
        super(color, position);
        this.attackDirections = new ArrayList<>();

        if (color.isWhite()) {
            moveDirection = Direction.UP;
            attackDirections.add(Direction.UP_RIGHT);
            attackDirections.add(Direction.UP_LEFT);
        } else {
            moveDirection = Direction.DOWN;
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
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();
        Position newPosition = moveDirection.getNextPosition(this.position);
        if (board.getPieceAt(newPosition) == null) possibleMoves.add(newPosition);
        for (int i = 0; i < 2; i++) {
            newPosition = attackDirections.get(i).getNextPosition(this.position);
            Piece piece = board.getPieceAt(newPosition);
            if (piece != null && !piece.getColor().equals(this.getColor())) possibleMoves.add(newPosition);
        }
        return possibleMoves;
    }

    @Override
    public Pawn clone() {
        return (Pawn) super.clone();
    }

}
