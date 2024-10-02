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
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
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

    public void printMenu(Board board) {
        menuPrinter(strPrintTurn(board.getTurn()));
    }

    public void printSubMenu(Piece piece) {
        menuPrinter(strPrintPiece(piece));
    }

    private void menuPrinter(String str) {
        ansi.moveCursorUp(3);
        ansi.start();
        ansi.save();
        ansi.replaceLine(str);
        ansi.moveCursorDown(2);
        ansi.start();
        ansi.save();
    }

    private String strPrintTurn(Color turn) {
        return "Turno: " + turn.getName();
    }

    private String strPrintPiece(Piece piece) {
        return "Selecionado(a): "
            + piece.getName() + " (" + piece.getColor().toString() + ") em " + piece.getPosition().toString();
    }

    public Piece selectPiece(Board board) {
        return getPiece(board);
    }
    
    private Piece getPiece(Board board) {
        while (true) {
            Piece piece = board.getPieceAt(selectPosition());
            if (piece != null) {
                if (piece.getColor().equals(board.getTurn())) return piece;
                else {
                    ansi.replaceLine("Peça incorreta. Turno das " + board.getTurn().getName().toLowerCase() + ".");
                    wait(1500);
                }
            } else {
                ansi.replaceLine("Posição sem peça.");
                wait(1500);
            }
        }
    }
    
    public Position selectPosition() {
        while (true) {
            ansi.replaceLine("Escolha uma posição: ");
            String str = scan.next();
            Position pos = Position.create(str);
            if (pos != null) return pos;
            ansi.replaceLine("Posição inválida.");
            wait(1500);
        }
    }

    private void wait(int time) {
        try {Thread.sleep(time);} 
        catch (Exception e) {}
    }

}
