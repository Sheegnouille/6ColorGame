package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private List<Player> playerz = new ArrayList<>();
    private Map<Player, Cell> players = new HashMap<>();
    private int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    public void add(Player player) {
        playerz.add(player);
        players.put(player, board.getStartingCell());
    }

    public void currentPlayerChooseColor(Color color) {
        Cell playerCell = players.entrySet().iterator().next().getValue();
        board.changeColor(playerCell, color);
        currentPlayerIndex++;
    }

    public Cell getStartingCell(Player player) {
        return players.get(player);
    }

    public Player getCurrentPlayer() {
        return playerz.get(currentPlayerIndex % playerz.size());
    }
}
