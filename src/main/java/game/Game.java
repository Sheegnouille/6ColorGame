package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private Map<Player, Cell> players = new HashMap<>();

    public Game(Board board) {
        this.board = board;
    }

    public void add(Player player) {
        players.put(player, board.getStartingCell());
    }

    public void currentPlayerChooseColor(Color color) {
    }

    public Cell getStartingCell(Player player) {
        return players.get(player);
    }
}
