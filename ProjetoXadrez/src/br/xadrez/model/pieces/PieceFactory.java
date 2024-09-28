package br.xadrez.model.pieces;

import java.lang.reflect.Constructor;

import br.xadrez.model.Color;
import br.xadrez.model.Position;

public class PieceFactory {
    
    /**
     * Método que cria uma {@code Piece} genérica
     * 
     * @param <T> {@code King}, {@code Queen}, {@code Rook}, {@code Bishop}, {@code Knight} ou {@code Pawn}
     * @param pieceClass {@code Class} da peça
     * @param color {@code Color} da peça
     * @return {@code Piece} se os argumentos forem válidos, {@code null} se não
      */
    public static <T extends Piece> T createPiece(Class<T> pieceClass, Color color) {
        return createPiece(pieceClass, color, null);
    }

    /**
     * Método que cria uma {@code Piece} genérica
     * 
     * @param <T> {@code King}, {@code Queen}, {@code Rook}, {@code Bishop}, {@code Knight} ou {@code Pawn}
     * @param pieceClass {@code Class} da peça
     * @param color {@code Color} da peça
     * @param position {@code Position} da peça
     * @return {@code Piece} se os argumentos forem válidos, {@code null} se não
      */
    public static <T extends Piece> T createPiece(Class<T> pieceClass, Color color, Position position) {
        verifyPiece(color);
        return instantiatePiece(pieceClass, color, position);
    }

    private static <T extends Piece> T instantiatePiece(Class<T> pieceClass, Color color, Position position) {
        try {
            Constructor<T> constructor = pieceClass.getDeclaredConstructor(Color.class, Position.class);
            T piece = constructor.newInstance(color, position);
            return piece;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Construtor não encontrado na classe " + pieceClass.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar instância da classe: " + pieceClass.getName(), e);
        }
    }

    private static void verifyPiece(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Cor inválida");
        }
    }
}
