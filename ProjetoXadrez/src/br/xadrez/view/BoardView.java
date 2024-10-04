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

    /**
     * Imprime o tabuleiro e prepara o terminal
     * para executar os menus.
     * 
     * @param board {@code Board} tabuleiro
      */
    public void printBoardComplete(Board board) {
        System.out.println();
        printBoard(board);
        System.out.println(br + br + br);
    }

    /**
     * Imprime o tabuleiro no terminal
     * 
     * @param board {@code Board} tabuleiro
      */
    public void printBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            System.out.println(printRow(board, row));
        }
        System.out.println("   a  b  c  d  e  f  g  h ");
    }

    /**
     * Retorna a {@code String} de uma linha do tabuleiro.
     * 
     * @param board {@code Board} tabuleiro
     * @param row {@code int} número da linha
     * @return {@code String} texto da linha
      */
    public String printRow(Board board, int row) {
        String line = "";
        line += (8 - row) + " ";
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(Position.create(row, col));
            line += printSquare(piece, row, col);
        }
        return line;
    }

    /**
     * Retorna a {@code String} de uma casa do tabuleiro.
     * 
     * @param piece {@code Piece} peça
     * @param row {@code int} número da linha
     * @param col {@code int} número da coluna
     * @return {@code String} texto da casa
      */
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

    /**
     * Destaca as casas do tabuleiro, tais como:
     * <br></br>
     * Movimentos válidos das peças;
     * <br></br>
     * Possível captura.
     * 
     * @param board {@code Board} tabuleiro
     * @param positions {@code List<Position>} lista de posições
     * @param piece {@code Piece} peça
      */
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

    /**
     * Retorna a {@code String} de uma casa
     * do tabuleiro destacada.
     * 
     * @param board {@code Board} tabuleiro
     * @param row {@code int} linha
     * @param col {@code int} coluna
     * @return {@code String} texto da casa
      */
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

    /**
     * Retorna a {@code String} da casa da
     * tomada de passagem do tabuleiro. 
     * 
     * @param row {@code int} linha
     * @param col {@code int} coluna
     * @return {@code String} texto da casa
      */
    public String highlightEnPassant(int row, int col) {
        return ansi.printRed("   ");
    }

    /**
     * Destaca as peças que estão executando
     * um xeque no rei.
     * 
     * @param board {@code Board} tabuleiro
     */
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

    /**
     * Retorna a {@code String} da casa em amarelo
     * de uma peça que está aplicando xeque.
     * 
     * @param piece {@code Piece} peça
     * @return {@code String} texto da casa
      */
    public String yellowPiece(Piece piece) {
        return ansi.printYellow(" " + piece.getSymbol() + " ");
    }

    /**
     * Atualiza o tabuleiro.
     * 
     * @param board {@code Board} tabuleiro
      */
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

    /**
     * Imprime o Menu de seleção de peça.
     * 
     * @param board {@code Board} board
      */
    public void printMenu(Board board) {
        menuPrinter(strPrintTurn(board.getTurn()));
    }

    /**
     * Imprime o SubMenu de seleção da
     * próxima posição de uma peça.
     * 
     * @param piece {@code Piece} peça
      */
    public void printSubMenu(Piece piece) {
        menuPrinter(strPrintPiece(piece));
    }

    /**
     * Atualiza o menu por meio
     * de um texto. 
     * 
     * @param str {@code String} texto
      */
    private void menuPrinter(String str) {
        ansi.moveCursorUp(3);
        ansi.start();
        ansi.replaceLine(str);
        ansi.moveCursorDown(2);
        ansi.start();
        ansi.save();
    }

    /**
     * Retorna o texto que mostra
     * o turno no menu.
     * 
     * @param turn {@code Color} turno
     * @return {@code String} texto
      */
    private String strPrintTurn(Color turn) {
        return "Turno: " + turn.getName();
    }

    /**
     * Retorna o texto que mostra
     * a peça selecionada no menu.
     * 
     * @param piece {@code Piece} peça
     * @return {@code String} texto
      */
    private String strPrintPiece(Piece piece) {
        return "Selecionado(a): "
            + piece.getName() + " (" + piece.getColor().toString() + ") em " + piece.getPosition().toString();
    }

    /**
     * Recebe do usuário a peça selecionada.
     * Só retorna a peça se ela for válida,
     * se não, continua rodando.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code Piece} peça selecionada
     */
    public Piece selectPiece(Board board) {
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
    
    /**
     * Recebe do usuário a posição selecionada.
     * Só retorna a posição se ela for válida,
     * se não, continua rodando.
     * 
     * @return {@code Position} posição
      */
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

    /**
     * Automaticamente substitui a linha
     * do cursor salva por um texto.
     * 
     * @param str {@code String} texto
      */
    public void replace(String str) {
        ansi.restore();
        ansi.replaceLine(str);
    }

    /** 
     * Imprime a mensagem de movimento inválido.
     */
    public void invalidMove() {
        replace("Movimento inválido.");
        wait(1500);
        ansi.moveCursorDown(1);
    }

    /**
     * Faz a máquina esperar o tempo
     * em milissegundos para continuar
     * a rodar o código.
     * 
     * @param time {@code int} tempo, em milissegundos
      */
    private void wait(int time) {
        try {Thread.sleep(time);} 
        catch (Exception e) {}
    }

}
