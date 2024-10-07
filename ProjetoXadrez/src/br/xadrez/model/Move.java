package br.xadrez.model;

import br.xadrez.model.pieces.Piece;

public class Move {
    
    private final Piece piece;
    private final Position from;
    private final Position to;
    private final Piece capturedPiece;

    private Move(Piece piece, Position to, Piece capturedPiece) {
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.capturedPiece = capturedPiece;
    }

    public static Move create(Piece piece, Position to, Piece capturedPiece) {
        if (piece == null || to == null) return null;
        return new Move(piece, to, capturedPiece);
    }

    public Piece getPiece() {
        return piece.clone();
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getCapturedPiece() {
        return capturedPiece.clone();
    }

}
