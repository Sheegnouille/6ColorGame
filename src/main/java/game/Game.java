package game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    public void addPlayer(String playerName) {
        Cell startingCell = board.provideFreeStartingCell();
        Player player = new Player(playerName, startingCell);
        players.add(player);
    }

    public void currentPlayerChooseColor(Color color) {
        board.changeColor(getCurrentPlayerStartingCell(), color);
        nextPlayer();
    }

    public boolean isFinished() {
        throw new NotImplementedException();
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    Cell getCurrentPlayerStartingCell() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.getStartingCell();
    }

    Score calculateCurrentPlayerScore() {
        Player currentPlayer = getCurrentPlayer();
        Cell startingCell = currentPlayer.getStartingCell();
        return Score.valueOf(board.determineTerritorySizeFromCell(startingCell));
    }
}
