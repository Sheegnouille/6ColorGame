package game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> players = new ArrayList<>();

    public Game(Board board) {
    }

    public void add(Player player) {
        players.add(player);
    }

    public void currentPlayerChooseColor(Color color) {

    }

    public int getNumberOfPlayer() {
        return players.size();
    }
}
