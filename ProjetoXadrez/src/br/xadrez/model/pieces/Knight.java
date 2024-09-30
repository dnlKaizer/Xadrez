package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Knight extends Piece {

    private final int[][] jumps = {
        {-2,-1}, {-2,1},
        {2,-1}, {2,1},
        {-1,-2}, {1,-2},
        {-1,2}, {1,2}
    };

    protected Knight(Color color, Position position) {
        super(color, position);
    }

    private List<Position> getJumpPosition() {
        List<Position> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Position newPosition = Position.create(
                this.position.getRow() + jumps[i][0],
                this.position.getCol() + jumps[i][1]
            );
            if (newPosition != null) list.add(newPosition);
        }
        return list;
    }

    @Override
    public String getName() {
        return "Cavalo";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        List<Position> list = getJumpPosition();
        return list.contains(square);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = getJumpPosition();
        for (int i = 0; i < possibleMoves.size(); i++) {
            Position newPosition = possibleMoves.get(i);
            if (!board.isDestinationClear(this.position, newPosition)) {
                possibleMoves.remove(newPosition);
                i--;
            }
        }
        return possibleMoves;
    }

    @Override
    public Knight clone() {
        return (Knight) super.clone();
    }

}
