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
            printRow(board, row);
        }
        System.out.println("   a  b  c  d  e  f  g  h ");
    }

    public void printRow(Board board, int row) {
        System.out.print((8 - row) + " ");
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(Position.create(row, col));
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

    public void printMenu(Board board, Color turn) {
        printBoard(board);
        printTurn(turn);
    }

    public Piece selectPiece(Board board, Color turn) {
        System.out.println();
        ansi.save();
        System.out.println();
        ansi.restore();
        return getPiece(board, turn);
    }

    public void printTurn(Color turn) {
        System.out.println(br + "Turno: " + turn.getName());
    }

    private Position getPosition() {
        while (true) {
            ansi.replaceLine("Selecione uma peça: ");
            String str = scan.next();
            Position pos = Position.create(str);
            if (pos != null) return pos;
            ansi.replaceLine("Posição inválida.");
            wait(1500);
        }
    }

    private Piece getPiece(Board board, Color turn) {
        while (true) {
            Piece piece = board.getPieceAt(getPosition());
            if (piece != null) {
                if (piece.getColor().equals(turn)) return piece;
                else {
                    ansi.replaceLine("Peça incorreta. Turno das " + turn.getName().toLowerCase() + ".");
                    wait(1500);
                }
            } else {
                ansi.replaceLine("Posição sem peça.");
                wait(1500);
            }
        }
    }

    private void wait(int time) {
        try {Thread.sleep(time);} 
        catch (Exception e) {}
    }

}
