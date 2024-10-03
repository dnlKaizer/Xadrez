package br.xadrez.utils;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class MoveUtils {
    
    /**
     * Retorna todos os movimentos possíveis em função de uma posição de origem
     * e de um vetor de direções. Verifica se o caminho está bloqueado.
     * 
     * @param board {@code Board} tabuleiro
     * @param from {@code Position} posição de origem
     * @param directions {@code Direction[]} vetor de direções 
     * @return {@code List<Position>} lista de posições
      */
    public static List<Position> getAllPossibleMoves(Board board, Position from, Direction[] directions) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < directions.length; i++) {
            Position to = from;
            while (true) {
                Direction direction = directions[i];
                to = direction.getNextPosition(to);
                if (board.isPathAndDestinationClear(from, to)) possibleMoves.add(to);
                else break;
            };
        }
        return possibleMoves;
    }
}
