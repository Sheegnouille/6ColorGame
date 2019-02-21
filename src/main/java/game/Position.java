package game;

public class Position {
    final int row;
    final int col;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    boolean isDiagonal(Position otherPosition) {
        return otherPosition.row != row && otherPosition.col != col;
    }

    boolean isLeft(Position otherPosition) {
        return col - 1 == otherPosition.col;
    }

    boolean isRight(Position otherPosition) {
        return col + 1 == otherPosition.col;
    }

    boolean isAbove(Position otherPosition) {
        return row - 1 == otherPosition.row;
    }

    boolean isUnder(Position otherPosition) {
        return row + 1 == otherPosition.row;
    }
}