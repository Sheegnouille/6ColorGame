package domain.game;

import domain.board.Cell;

import java.util.Objects;

public final class Player {

    private final String name;
    private final Cell startingCell;
    private final boolean human;

    Player(String playerName, Cell startingCell, boolean human) {
        this.name = playerName;
        this.startingCell = startingCell;
        this.human = human;
    }

    String getName() {
        return name;
    }

    Cell getStartingCell() {
        return startingCell;
    }

    boolean isHuman() {
        return human;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                Objects.equals(startingCell, player.startingCell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startingCell);
    }

    @Override
    public String toString() {
        return "Player name: " + name +
                " " + startingCell;
    }
}
