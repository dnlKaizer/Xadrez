package br.xadrez.controller;

import java.util.List;

import br.xadrez.model.Position;
import br.xadrez.model.board.*;
import br.xadrez.model.pieces.*;
import br.xadrez.view.BoardView;

public class BoardController {
    BoardBuilder boardBuilder;
    Board board;
    BoardView boardView;

    public BoardController(Board board, BoardView boardView, BoardBuilder boardBuilder) {
        this.board = board;
        this.boardView = boardView;
        this.boardBuilder = boardBuilder;
    }

    public BoardController() {
        this.boardView = new BoardView();
        this.boardBuilder = new BoardBuilder();
        this.board = boardBuilder.buildStandard();
    }

    public void game() {
        boardView.printBoardComplete(board);
        while (true) {
            round();
        }
    }

    public void round() {
        boardView.printMenu(board);
        Piece piece = boardView.selectPiece(board);
        Position currentPosition = piece.getPosition();
        List<Position> validMoves = piece.getValidMoves(board);
        boardView.highlightSquares(board, validMoves);
        Position newPosition;
        boardView.printSubMenu(piece);
        newPosition = boardView.selectPosition();
        if (!validMoves.contains(newPosition)) {
            boardView.invalidMove();
        } else {
            board.move(piece, newPosition);
            boardView.updateBoard(board, currentPosition, newPosition);
        }
        boardView.restoreSquares(board, validMoves);
    }

}
