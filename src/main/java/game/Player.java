package game;

import java.util.Objects;

public final class Player {

    private final String name;
    private final Cell startingCell;

    Player(String playerName, Cell startingCell) {
        this.name = playerName;
        this.startingCell = startingCell;
    }

    Cell getStartingCell() {
        return startingCell;
    }

    boolean hasName(String name) {
        return name.equals(this.name);
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
                ", \nstarting cell: " + startingCell;
    }
}
