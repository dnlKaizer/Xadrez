package br.xadrez.view;

import java.util.Scanner;
import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Direction;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.model.pieces.Pawn;
import br.xadrez.model.pieces.Piece;
import br.xadrez.utils.AnsiUtils;

public class BoardView {
    private Scanner scan = new Scanner(System.in);
    private String br = System.lineSeparator();
    private final AnsiUtils ansi = new AnsiUtils();

    public void printBoardComplete(Board board) {
        System.out.println();
        printBoard(board);
        System.out.println(br + br + br);
    }

    public void printBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            System.out.println(printRow(board, row));
        }
        System.out.println("   a  b  c  d  e  f  g  h ");
    }

    public void updateBoard(Board board, Position currentPosition, Position newPosition) {
        ansi.placeBoard(currentPosition.getRow(), currentPosition.getCol());
        System.out.print(printSquare(board.getPieceAt(currentPosition), currentPosition.getRow(), currentPosition.getCol()));

        ansi.placeBoard(newPosition.getRow(), newPosition.getCol());
        System.out.print(printSquare(board.getPieceAt(newPosition), newPosition.getRow(), newPosition.getCol()));

        ansi.restore();
        ansi.moveCursorDown(1);
    }

    public String printRow(Board board, int row) {
        String line = "";
        line += (8 - row) + " ";
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(Position.create(row, col));
            line += printSquare(piece, row, col);
        }
        return line;
    }

    public String printSquare(Piece piece, int row, int col) {
        String str;
        if (piece == null) str = "   ";
        else {
            if (piece.getColor().isWhite()) {
                str = AnsiUtils.CYAN_TEXT + " " + piece.getSymbol() + " ";
            } else {
                str = AnsiUtils.BLACK_TEXT + " " + piece.getSymbol() + " ";
            }
        }
        if ((row + col) % 2 == 0) return ansi.printGreen(str);
        else return ansi.printBlue(str);
    }

    public void highlightSquares(Board board, List<Position> positions, Piece piece) {
        for (Position position : positions) {
            int row = position.getRow();
            int col = position.getCol();
            ansi.placeBoard(row, col);
            if (piece instanceof Pawn && board.getPieceAt(position) == null &&
            Direction.create(piece.getPosition(), position).isDiagonal()) {
                System.out.print(highlightEnPassant(row, col));
            } else {
                System.out.print(highlightSquare(board, row, col));
            }
        }
        ansi.restore();
        ansi.moveCursorDown(1);
    }

    public String highlightSquare(Board board, int row, int col) {
        Piece piece = board.getPieceAt(Position.create(row, col));
        String str = "";

        if (piece == null) str = AnsiUtils.WHITE_TEXT + " * ";
        else {
            str = " " + piece.getSymbol() + " ";
            return ansi.printRed(str);
        }

        if ((row + col) % 2 == 0) return ansi.printGreen(str);
        else return ansi.printBlue(str);
    }

    public String highlightEnPassant(int row, int col) {
        return ansi.printRed("   ");
    }

    public void highlightCheck(Board board) {
        List<Piece> pieces = board.getPiecesChecking(board.getTurn());
        for (Piece piece : pieces) {
            int row = piece.getPosition().getRow();
            int col = piece.getPosition().getCol();
            ansi.placeBoard(row, col);
            System.out.print(yellowPiece(piece));
        }
        ansi.restore();
        ansi.moveCursorDown(1);
    }

    public String yellowPiece(Piece piece) {
        return ansi.printYellow(" " + piece.getSymbol() + " ");
    }

    public void restoreSquares(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(Position.create(row, col));
                ansi.placeBoard(row, col);
                System.out.print(printSquare(piece, row, col));
            }
        }
        ansi.restore();
        ansi.moveCursorDown(1);
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
                    replace("Peça incorreta. Turno das " + board.getTurn().getName().toLowerCase() + ".");
                    wait(1500);
                }
            } else {
                replace("Posição sem peça.");
                wait(1500);
            }
        }
    }
    
    public Position selectPosition() {
        while (true) {
            replace("Escolha uma posição: ");
            String str = scan.next();
            Position pos = Position.create(str);
            if (pos != null) return pos;
            replace("Posição inválida.");
            wait(1500);
        }
    }

    public void replace(String str) {
        ansi.restore();
        ansi.replaceLine(str);
    }

    public void invalidMove() {
        replace("Movimento inválido.");
        wait(1500);
        ansi.moveCursorDown(1);
    }

    private void wait(int time) {
        try {Thread.sleep(time);} 
        catch (Exception e) {}
    }

}
