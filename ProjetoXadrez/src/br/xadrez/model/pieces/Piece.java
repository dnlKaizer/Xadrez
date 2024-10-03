package br.xadrez.model.pieces;

import java.util.List;

import br.xadrez.model.Color;
import br.xadrez.model.Position;
import br.xadrez.model.board.Board;

public abstract class Piece implements Cloneable {
    
    protected Color color;
    protected Position position;

    protected Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        if (position != null) this.position = position;
    }

    /**
     * Retorna o nome da peça.
     * 
     * @return {@code String} nome da peça
      */
    public abstract String getName();

    /**
     * Retorna a primeira letra da peça.
     * 
     * @return {@code String} inicial
      */
    public abstract String getSymbol();

    /**
     * Retorna se a peça consegue atacar a posição.
     * 
     * @param square {@code Position} quadrado
     * @param board {@code Board} tabuleiro
     * @return {@code true} se ela ataca a posição, {@code false} se não
      */
    public abstract boolean isAttacking(Position square, Board board);

    /**
     * Retorna uma lista com os movimentos padrão da peça.
     * Também verifica se o caminho da peça até o destino
     * não possui nenhuma peça bloqueando.
     * <br></br>
     * <b>Exemplo</b>: bispo retorna todas as posições de todas as 
     * diagonais não bloqueadas.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code List<Position>} movimentos. Se não possuir
     * movimentos devido ao bloqueio das peças, retorna uma lista
     * vazia.
      */
    public abstract List<Position> getPossibleMoves(Board board);

    /**
     * Chama o método <b>getPossibleMoves()</b>, valida todos os seus
     * movimentos e retorna uma lista de movimentos válidos.
     * <br></br>
     * Faz uso do método <b>isMoveValid()</b> de {@code Board}.
     * 
     * @param board {@code Board} tabuleiro
     * @return {@code List<Position>} movimentos. Se não possuir
     * movimentos válidos, retorna uma lista vazia.
      */
    public List<Position> getValidMoves(Board board) {
        List<Position> moves = getPossibleMoves(board);
        for (int i = 0; i < moves.size(); i++) {
            Position move = moves.get(i);
            if (!board.isMoveValid(this.clone(), move)) {
                moves.remove(move);
                i--;
            } 
        }
        return moves;
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return piece.getColor().equals(this.getColor()) && piece.getPosition().equals(this.getPosition());
    }
    
}
