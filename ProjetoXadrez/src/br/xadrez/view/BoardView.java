package br.xadrez.view;

import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.model.pieces.Piece;

public class BoardView {

    public void printBoard(Board board) {
        System.out.println();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(
                    Position.create(row, col)
                );
                if (piece == null) System.out.print("-\t");
                else System.out.print(piece.getName() + "\t");
            }
            System.out.println();
        }
    }
}
