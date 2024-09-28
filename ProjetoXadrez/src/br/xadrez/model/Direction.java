package br.xadrez.model;

public class Direction {
    private final int x;
    private final int y;
    private final boolean isDiagonal;

    public static Direction UP = new Direction(-1, 0);
    public static Direction DOWN = new Direction(1, 0);
    public static Direction RIGHT = new Direction(0, 1);
    public static Direction LEFT = new Direction(0, -1);
    public static Direction UP_RIGHT = new Direction(-1, 1);
    public static Direction UP_LEFT = new Direction(-1, -1);
    public static Direction DOWN_RIGHT = new Direction(1, 1);
    public static Direction DOWN_LEFT = new Direction(1, -1);

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
        this.isDiagonal = Math.abs(x) == Math.abs(y);
    }
    public static Direction create(int x, int y) {
        if (x > 1 || x < -1 || y > 1 || y < -1 || (x == 0 && y == 0)) return null;
        return new Direction(x, y);
    }
    public static Direction create(Position from, Position to) {
        if (from == null || to == null) return null;
        return Direction.create(
            (int) Math.signum(to.getRow() - from.getRow()),
            (int) Math.signum(to.getCol() - from.getCol()));
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isDiagonal() {
        return isDiagonal;
    }

    public Direction invert() {
        return create((-1) * x,(-1) * y);
    }

    /**
     * Método que busca a próxima posição na direção.
     * 
     * @param position Posição de origem
     * @return {@code Position} se for possível andar na direção, {@code null} se não
      */
    public Position getNextPosition(Position position) {
        if (position == null) return null; 
        // Armazena a próxima posição na direção
        return Position.create(position.getRow() + this.getX(), position.getCol() + this.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Direction direction = (Direction) obj;
        return direction.getX() == getX() && direction.getY() == getY();
    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }

}
