package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Pawn extends Piece {
    // Linha inicial do peão (útil para cálculos)
    private final int initialRow;
    // Direção de movimento do peão
    private final Direction moveDirection;
    // Direções de ataque do peão
    private final List<Direction> attackDirections;

    protected Pawn(Color color, Position position) {
        super(color, position);
        this.attackDirections = new ArrayList<>();

        // Verifica a cor do peão e salva:
        // linha inicial
        // direção de movimento
        // direções de ataque (duas) 
        if (color.isWhite()) {
            initialRow = 6;
            moveDirection = Direction.UP;
            attackDirections.add(Direction.UP_RIGHT);
            attackDirections.add(Direction.UP_LEFT);
        } else {
            initialRow = 1;
            moveDirection = Direction.DOWN;
            attackDirections.add(Direction.DOWN_RIGHT);
            attackDirections.add(Direction.DOWN_LEFT);
        }  
    }

    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    @Override
    public String getName() {
        return "Peão";
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        // Direção entre a posição do peão e a casa que está sendo analisada
        Direction direction = Direction.create(this.position, square);
        // Verifica se a posição está próxima da casa e se a direção é uma das direções de ataque do peão
        return !(direction == null) && this.position.isNear(square) && (
            direction.equals(attackDirections.get(0)) ||
            direction.equals(attackDirections.get(1)));
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();

        // Verifica se o peão pode se mover para frente 1 casa
        Position newPosition = moveDirection.getNextPosition(this.position);
        if (board.getPieceAt(newPosition) == null) possibleMoves.add(newPosition);

        // Verifica se o peão pode capturar alguma peça
        for (int i = 0; i < 2; i++) {
            newPosition = attackDirections.get(i).getNextPosition(this.position);
            Piece piece = board.getPieceAt(newPosition);
            if (piece != null && !piece.getColor().equals(this.getColor())) possibleMoves.add(newPosition);
        }

        // Verifica se o peão pode mover 2 casas para frente (somente movimento inicial)
        if (position.getRow() == initialRow) {
            newPosition = Position.create(initialRow + (2 * moveDirection.getX()), position.getCol());
            if (board.getPieceAt(newPosition) == null) possibleMoves.add(newPosition);
        }

        // Verifica se o peão pode fazer uma tomada de passagem
        if (canEnPassant(board)) {
            newPosition = Position.create(initialRow + (4 * moveDirection.getX()), board.getEnPassantCol());
            possibleMoves.add(newPosition);
        }
        return possibleMoves;
    }

    private boolean canEnPassant(Board board) {
        // Verifica se o peão está na sexta horizontal
        if (!(initialRow + (3 * moveDirection.getX()) == this.position.getRow())) return false;
        // Verifica se o peão pode executar o movimento com base no tabuleiro 
        if (board.getEnPassantCol() != -1) return true;
        else return false;
    }

    @Override
    public Pawn clone() {
        return (Pawn) super.clone();
    }

}
