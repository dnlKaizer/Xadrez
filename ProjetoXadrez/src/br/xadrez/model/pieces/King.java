package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class King extends Piece {

    // Armazena se o rei já se moveu (crucial para o roque)
    private boolean hasMoved;
    // Direções de movimento/ataque do rei
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
        return "Rei";
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        // Verifica se a posição do rei está próxima da casa
        return this.position.isNear(square);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();
        // Verifica as posições padrão de movimento do rei
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            Position to = direction.getNextPosition(this.position);
            if (board.isDestinationClear(this.position, to)) possibleMoves.add(to);
        }
        // Verifica o roque
        if (canCastle(board)) {
            // Verifica roque na ala do rei
            if (canCastleKingSide(board)) 
                possibleMoves.add(Position.create(this.position.getRow(), this.position.getCol() + 2));
            // Verifica roque na ala da dama
            if (canCastleQueenSide(board)) 
                possibleMoves.add(Position.create(this.position.getRow(), this.position.getCol() - 2));
        }
        return possibleMoves;
    }

    /**
     * Analisa se o rei está em condições para
     * fazer o roque. Verifica se ele já se moveu
     * e se ele está em xeque.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code true} se pode fazer roque, 
     * {@code false} se não
      */
    private boolean canCastle(Board board) {
        return !(hasMoved || board.isInCheck(this.color));
    }

    /**
     * Analisa se as condições do tabuleiro permitem o
     * roque na ala do rei. Verifica se a torre já se
     * moveu e se nenhuma peça está atacando as casas
     * em que o rei anda.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code true} se pode fazer roque, 
     * {@code false} se não
      */
    private boolean canCastleKingSide(Board board) {
        return board.canKingSideRookCastle(color);
    } 

    /**
     * Analisa se as condições do tabuleiro permitem o
     * roque na ala da dama. Verifica se a torre já se
     * moveu e se nenhuma peça está atacando as casas
     * em que o rei anda.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code true} se pode fazer roque, 
     * {@code false} se não
      */
    private boolean canCastleQueenSide(Board board) {
        return board.canQueenSideRookCastle(color);
    } 

    @Override
    public King clone() {
        return (King) super.clone();
    }

}
