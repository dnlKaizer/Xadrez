package br.xadrez.model.pieces;

import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.utils.MoveUtils;

public class Rook extends Piece {

    private final Direction[] directions = {
        Direction.UP,
        Direction.DOWN,
        Direction.RIGHT,
        Direction.LEFT
    };

    protected Rook(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Torre";
    }

    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        Direction direction = Direction.create(this.position, square);
        return !(direction == null) && !(direction.isDiagonal()) && board.isPathClear(this.position, square);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        return MoveUtils.getAllPossibleMoves(board, this.position, this.directions);
    }

    @Override
    public Rook clone() {
        return (Rook) super.clone();
    }

}
