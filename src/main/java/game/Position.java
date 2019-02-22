package game;

import java.util.Objects;

final class Position {
    private final int row;
    private final int col;

    Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    int transformIntoIndex(Dimension dimension) {
        return this.row * dimension.getWidth() + this.col;
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

    @Override
    public String toString() {
        return "Position{" +
                "\nrow=" + row +
                ", \ncol=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    static final class PositionBuilder {
        private int row;
        private int col;

        private PositionBuilder() {
        }

        static PositionBuilder aPosition() {
            return new PositionBuilder();
        }

        PositionBuilder withRow(int row) {
            this.row = row;
            return this;
        }

        PositionBuilder withColumn(int col) {
            this.col = col;
            return this;
        }

        Position build() {
            return new Position(col, row);
        }
    }
}