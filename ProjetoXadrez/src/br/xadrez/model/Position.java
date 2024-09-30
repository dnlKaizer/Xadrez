package br.xadrez.model;

import java.util.regex.*;

public class Position {
    private final int row;
    private final int col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Position create(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) return null;
        return new Position(row, col);
    }

    public static Position create(String string) {
        Pattern pattern = Pattern.compile("^[a-h][1-8]$");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) return null;
        return Position.create(
            56 - string.charAt(1),
            string.charAt(0) - 97
        );
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public boolean isNear(Position position) {
        if (position == null) return false;
        return Math.abs(this.getRow() - position.getRow()) <= 1 &&
        Math.abs(this.getCol() - position.getCol()) <= 1;
    }
    
    @Override
    public String toString() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return (letters[col] + (8 - row));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position newPosition = (Position) obj;
        return ((this.col == newPosition.getCol()) && (this.row == newPosition.getRow()));
    }
    
}