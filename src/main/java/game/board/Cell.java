package game.board;

import game.color.Color;

import java.util.Objects;

public class Cell {

    private final Position position;
    private Color color;

    public Cell(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    static Cell copyInstance(Cell cell) {
        return new Cell(cell.position, cell.color);
    }

    boolean isSameColor(Cell otherCell) {
        return this.color.equals(otherCell.color);
    }

    void changeColor(Color color) {
        this.color = color;
    }

    boolean isAdjacentTo(Cell otherCell) {
        if (position.isDiagonal(otherCell.position)) {
            return false;
        }
        return position.isUnder(otherCell.position) || position.isAbove(otherCell.position) ||
                position.isRight(otherCell.position) || position.isLeft(otherCell.position);
    }

    public boolean isOfColor(Color color) {
        return this.color == color;
    }

    String showColor() {
        return color.toString();
    }

    @Override
    public String toString() {
        return "(" + position +
                ", " + color +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(position, cell.position) &&
                color == cell.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, color);
    }
}
