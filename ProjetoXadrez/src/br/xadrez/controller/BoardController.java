package br.xadrez.controller;

import br.xadrez.model.board.Board;
import br.xadrez.model.board.BoardBuilder;
import br.xadrez.view.BoardView;

public class BoardController {
    BoardBuilder boardBuilder;
    Board board;
    BoardView boardView;

    public BoardController(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
        this.boardBuilder = new BoardBuilder();
    }
    
    public void setUpBoard() {
        this.board = boardBuilder.buildStandard();
    }

    public void printBoard() {
        boardView.printBoard(board);
    }
}
