package br.xadrez.controller;

import br.xadrez.model.Color;
import br.xadrez.model.board.*;
import br.xadrez.model.pieces.*;
import br.xadrez.view.BoardView;

public class BoardController {
    BoardBuilder boardBuilder;
    Board board;
    BoardView boardView;
    Color turn;

    public BoardController(Board board, BoardView boardView, BoardBuilder boardBuilder, Color turn) {
        this.board = board;
        this.boardView = boardView;
        this.boardBuilder = boardBuilder;
        this.turn = turn;
    }

    public BoardController() {
        this.boardView = new BoardView();
        this.boardBuilder = new BoardBuilder();
        this.board = boardBuilder.buildStandard();
        this.turn = Color.WHITE;
    }

    public void printBoard() {
        boardView.printBoard(board);
    }
}
