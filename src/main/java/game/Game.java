package game;

import game.board.Board;
import game.board.Cell;
import game.color.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Game {

    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    // TODO rename
    public void toto() {
        board.show();
        System.out.print(getCurrentPlayer() + " : Choose color (Available colors : ");
        showAvailableColor();
        System.out.println(")");
    }

    public void displayScore() {
        board.show();
        for (Player player : players) {
            System.out.println(player +
                    "Score : " +
                    board.determineTerritorySizeFromCell(player.getStartingCell()));
        }
    }

    private void showAvailableColor() {
        Arrays.stream(Color.values())
                .filter(this::isColorAvailable)
                .forEach(color -> System.out.print(color.toString()));
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
        int playerTerritorySize = board.determineTerritorySizeFromCell(getCurrentPlayerStartingCell());
        int boardSize = board.determineBoardSize();
        return playerTerritorySize >= boardSize / players.size();
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

    public int getNumberOfPlayers() {
        return players.size();
    }


}
