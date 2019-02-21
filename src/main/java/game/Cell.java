package game;

import java.util.Objects;

public class Cell {

    private final int row;
    private final int col;
    private Color color;

    Cell(int col, int row, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    boolean isSameColor(Cell otherCell) {
        return this.color.equals(otherCell.color);
    }

    void changeColor(Color color) {
        this.color = color;
    }

    boolean isAdjacentTo(Cell otherCell) {
        if (isDiagonal(otherCell)) {
            return false;
        }
        return isUnder(otherCell) || isAbove(otherCell) ||
                isRight(otherCell) || isLeft(otherCell);
    }

    private boolean isDiagonal(Cell otherCell) {
        return otherCell.row != row && otherCell.col != col;
    }

    private boolean isLeft(Cell otherCell) {
        return col - 1 == otherCell.col;
    }

    private boolean isRight(Cell otherCell) {
        return col + 1 == otherCell.col;
    }

    private boolean isAbove(Cell otherCell) {
        return row - 1 == otherCell.row;
    }

    private boolean isUnder(Cell otherCell) {
        return row + 1 == otherCell.row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col &&
                color == cell.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, color);
    }

    @Override
    public String toString() {
        return "row=" + row +
                ", col=" + col +
                ", color=" + color ;
    }
}
