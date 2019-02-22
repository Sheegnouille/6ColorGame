package game;

import java.util.Objects;

public class Cell {

    private final Position position;
    private Color color;

    Cell(Position position, Color color) {
        this.position = position;
        this.color = color;
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

    boolean isOfColor(Color color) {
        return this.color == color;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "\nposition=" + position +
                ", \ncolor=" + color +
                '}';
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

    String showColor() {
        return color.toString();
    }
}
