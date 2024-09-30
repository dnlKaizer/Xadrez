package br.xadrez.utils;

import java.util.ArrayList;
import java.util.List;

import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public class MoveUtils {
    
    public static List<Position> getAllPossibleMoves(Board board, Position from, Direction[] directions) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < directions.length; i++) {
            while (true) {
                Direction direction = directions[i];
                Position to = direction.getNextPosition(from);
                if (board.isPathClear(from, to)) possibleMoves.add(to);
                else break;
                from = to;
            };
        }
        return possibleMoves;
    }
}
