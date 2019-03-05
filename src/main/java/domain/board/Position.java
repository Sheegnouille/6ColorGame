package domain.board;

import java.util.Objects;

public final class Position {
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
        return "{" + row +
                ", " + col +
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

    public static final class PositionBuilder {
        private int row;
        private int col;

        private PositionBuilder() {
        }

        public static PositionBuilder aPosition() {
            return new PositionBuilder();
        }

        public PositionBuilder withRow(int row) {
            this.row = row;
            return this;
        }

        public PositionBuilder withColumn(int col) {
            this.col = col;
            return this;
        }

        public Position build() {
            return new Position(col, row);
        }
    }
}