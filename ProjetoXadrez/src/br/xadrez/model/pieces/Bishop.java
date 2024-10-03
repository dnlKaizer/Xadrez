package br.xadrez.model.pieces;

import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.utils.MoveUtils;

public class Bishop extends Piece {

    // Direções de movimento/ataque do bispo
    private final Direction[] directions = {
        Direction.UP_RIGHT,
        Direction.UP_LEFT,
        Direction.DOWN_RIGHT,
        Direction.DOWN_LEFT
    };

    protected Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public String getName() {
        return "Bispo";
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        // Direção entre a posição do bispo e a casa que está sendo analisada
        Direction direction = Direction.create(this.position, square);
        // Verifica se a direção é diagonal e se o caminho está livre
        return !(direction == null) && direction.isDiagonal() && board.isPathClear(this.position, square);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        return MoveUtils.getAllPossibleMoves(board, this.position, this.directions);
    }

    @Override
    public Bishop clone() {
        return (Bishop) super.clone();
    }

}
