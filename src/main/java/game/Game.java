package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean currentPlayerChooseColor(Color color) {
        if (!isColorAvailable(color))
            return false;

        currentPlayerPlays(color);
        return true;
    }

    private void currentPlayerPlays(Color color) {
        board.changeColor(getCurrentPlayerStartingCell(), color);
        if (!isFinished()) {
            nextPlayer();
        }
    }

    private boolean isColorAvailable(Color color) {
        Optional<Cell> sameColorCell = players.stream()
                .map(Player::getStartingCell)
                .filter(cell -> cell.isOfColor(color))
                .findFirst();
        return !sameColorCell.isPresent();
    }

    public boolean isFinished() {
        return board.isTerritoryDominant(getCurrentPlayerStartingCell());
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
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
