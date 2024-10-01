package br.xadrez.view;

import java.util.Scanner;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.model.pieces.Piece;
import br.xadrez.utils.AnsiUtils;

public class BoardView {
    private Scanner scan = new Scanner(System.in);
    private String br = System.lineSeparator();
    private final AnsiUtils ansi = new AnsiUtils();

    public void printBoard(Board board) {
        System.out.println();
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(
                    Position.create(row, col)
                );
                String str;
                if (piece == null) str = "   ";
                else {
                    if (piece.getColor().isWhite()) {
                        str = AnsiUtils.CYAN_TEXT + " " + piece.getSymbol() + " ";
                    } else {
                        str = AnsiUtils.BLACK_TEXT + " " + piece.getSymbol() + " ";
                    }
                }
                if ((row + col) % 2 == 0) ansi.printGreen(str);
                else ansi.printBlue(str);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h ");
    }
}
