package br.xadrez.main;

import br.xadrez.controller.BoardController;
import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;
import br.xadrez.model.board.BoardBuilder;
import br.xadrez.model.pieces.*;
import br.xadrez.view.BoardView;

public class App {
    public static void main(String[] args) throws Exception {
        BoardBuilder boardBuilder = new BoardBuilder();
        boardBuilder.standard();
        boardBuilder.placePiece(
            Position.create(4, 4),
            PieceFactory.createPiece(Pawn.class, Color.WHITE, Position.create(6, 4))
        );
        Board board = boardBuilder.build();
        BoardController boardController = new BoardController(board, new BoardView());
        boardController.printBoard();
        Piece piece = board.getPieceAt(Position.create(7, 5));
        for (Position position : piece.getValidMoves(board)) {
            System.out.println(position.toString());
        }
    }
}