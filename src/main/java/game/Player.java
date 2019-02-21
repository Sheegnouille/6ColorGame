package game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;

public class Player {

    private final String playerName;
    private final Cell startingCell;

    public Player(String playerName) {
        this(playerName, null);
    }

    public Player(String playerName, Cell startingCell) {
        this.playerName = playerName;
        this.startingCell = startingCell;
    }

    public Score getScore() {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName) &&
                Objects.equals(startingCell, player.startingCell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, startingCell);
    }

    @Override
    public String toString() {
        return "Player " + playerName;
    }
}
