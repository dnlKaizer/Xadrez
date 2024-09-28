package br.xadrez.main;

import br.xadrez.controller.BoardController;
import br.xadrez.model.Board;
import br.xadrez.view.BoardView;

public class App {
    public static void main(String[] args) throws Exception {
        BoardController boardController = new BoardController(new Board(), new BoardView());
        boardController.setUpBoard();
        boardController.printBoard();
    }
}