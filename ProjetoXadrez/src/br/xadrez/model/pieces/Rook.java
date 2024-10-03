package br.xadrez.model.pieces;

import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.utils.MoveUtils;

public class Rook extends Piece {

    // Armazena se a torre já se moveu (crucial para o roque)
    private boolean hasMoved;
    // Direções de movimento/ataque da torre
    private final Direction[] directions = {
        Direction.UP,
        Direction.DOWN,
        Direction.RIGHT,
        Direction.LEFT
    };

    protected Rook(Color color, Position position) {
        super(color, position);
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
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
        // Direção entre a posição da torre e a casa que está sendo analisada
        Direction direction = Direction.create(this.position, square);
        // Verifica se a direção é horizontal/vertical e se o caminho está livre
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
