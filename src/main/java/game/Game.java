package game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Board board;
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    public void addPlayer(String playerName) {
        Cell startingCell = board.getStartingCell();
        Player player = new Player(playerName, startingCell);
        players.add(player);
    }

    public void currentPlayerChooseColor(Color color) {
        board.changeColor(getCurrentPlayerStartingCell(), color);
        currentPlayerIndex++;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex % players.size());
    }

    public Cell getCurrentPlayerStartingCell() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.getStartingCell();
    }

    public Score getCurrentPlayerScore() {
        Player currentPlayer = getCurrentPlayer();
        List<Cell> currentPlayerCells = board.getContiguousColor(currentPlayer.getStartingCell());
        return Score.valueOf(currentPlayerCells.size());
    }
}
