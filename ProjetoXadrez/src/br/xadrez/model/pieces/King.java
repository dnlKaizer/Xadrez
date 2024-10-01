package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class King extends Piece {

    private final boolean hasMoved;
    private final Direction[] directions = {
        Direction.UP_RIGHT,
        Direction.UP_LEFT,
        Direction.DOWN_RIGHT,
        Direction.DOWN_LEFT,
        Direction.UP,
        Direction.DOWN,
        Direction.RIGHT,
        Direction.LEFT
    };

    protected King(Color color, Position position) {
        super(color, position);
        if (color.isWhite()) hasMoved = !Position.create(7, 4).equals(position);
        else hasMoved = !Position.create(0, 4).equals(position);
    }

    @Override
    public String getName() {
        return "Rei";
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        return false;
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            Position to = direction.getNextPosition(this.position);
            if (board.isDestinationClear(this.position, to)) possibleMoves.add(to);
        }
        if (canCastle(board)) {
            if (canCastleKingSide(board)) 
                possibleMoves.add(Position.create(this.position.getRow(), this.position.getCol() + 2));
            if (canCastleQueenSide(board)) 
                possibleMoves.add(Position.create(this.position.getRow(), this.position.getCol() - 2));
        }
        return possibleMoves;
    }

    private boolean canCastle(Board board) {
        return !(hasMoved || board.isInCheck(this.color));
    }

    private boolean canCastleKingSide(Board board) {
        // VERIFICAR TORRE E CASAS QUE O REI PASSA
        return true;
    } 

    private boolean canCastleQueenSide(Board board) {
        // VERIFICAR TORRE E CASAS QUE O REI PASSA
        return true;
    } 

    @Override
    public King clone() {
        return (King) super.clone();
    }

}
