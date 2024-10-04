package br.xadrez.model.pieces;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class Knight extends Piece {

    // Pulos de movimento/ataque do cavalo
    private final int[][] jumps = {
        {-2,-1}, {-2,1},
        {2,-1}, {2,1},
        {-1,-2}, {1,-2},
        {-1,2}, {1,2}
    };

    protected Knight(Color color, Position position) {
        super(color, position);
    }

    /**
     * Retorna as posições possíveis de pulo do
     * cavalo. Somente retorna posições que estão
     * dentro tabuleiro.
     * 
     * @return {@code List<Position>} movimentos.
     * Sempre retorna no mínimo dois movimentos,
     * mas eles não são validados.
      */
    private List<Position> getJumpsPositions() {
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
    public String getSymbol() {
        return "C";
    }

    @Override
    public boolean isAttacking(Position square, Board board) {
        List<Position> list = getJumpsPositions();
        // Verifica se a posição passada está entre as casas de pulo do cavalo
        return list.contains(square);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        // Verifica cada posição de getJumpsPositions
        for (Position newPosition : getJumpsPositions()) {
            // Verifica se na posição de destino tem uma peça da mesma cor
            if (board.isDestinationClear(this.position, newPosition)) {
                validMoves.add(newPosition);
            }
        }
        return validMoves;
    }

    @Override
    public Knight clone() {
        return (Knight) super.clone();
    }

}
