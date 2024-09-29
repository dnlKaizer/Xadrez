package br.xadrez.main;

import br.xadrez.controller.BoardController;
import br.xadrez.model.Board;
import br.xadrez.model.board.BoardBuilder;
import br.xadrez.view.BoardView;

public class App {
    public static void main(String[] args) throws Exception {
        BoardBuilder boardBuilder = new BoardBuilder();
        BoardController boardController = new BoardController(boardBuilder.buildStandard(), new BoardView());
        boardController.printBoard();
    }
}