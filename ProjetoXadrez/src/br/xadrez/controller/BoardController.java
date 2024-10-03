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
            if (board.isCheckMate(board.getTurn())) {
                boardView.highlightCheck(board);
                break;
            }
        }
        System.out.println("XEQUE-MATE PALHAÇO");
    }

    public void round() {
        // Verifica se o rei está em xeque
        if (board.isInCheck(board.getTurn())) boardView.highlightCheck(board);

        // Imprime o Menu padrão para selecionar peças
        boardView.printMenu(board);
        Piece piece = boardView.selectPiece(board);

        // Pega os movimentos válidos da peça e mostra no tabuleiro
        List<Position> validMoves = piece.getValidMoves(board);
        boardView.highlightSquares(board, validMoves, piece);

        // Imprime o SubMenu para selecionar a posição final da peça
        boardView.printSubMenu(piece);
        Position newPosition;
        newPosition = boardView.selectPosition();

        // Verifica se a posição escolhida é um movimento válido
        if (!validMoves.contains(newPosition)) boardView.invalidMove();
        else board.move(piece, newPosition);
        
        // Restaura o tabuleiro
        boardView.restoreSquares(board);
    }

}
