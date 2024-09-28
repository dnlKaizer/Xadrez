package br.xadrez.controller;

import br.xadrez.model.Board;
import br.xadrez.view.BoardView;

public class BoardController {
    Board board;
    BoardView boardView;

    public BoardController(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }
    
    public void setUpBoard() {
        board.setUpBoard();
    }

    public void printBoard() {
        boardView.printBoard(board);
    }
}
