package domain.game;

import domain.IO.Display;
import domain.board.Board;
import domain.board.Cell;
import domain.color.Color;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    void showBoard(Display display) {
        board.show(display);
    }

    Map<Player, Integer> getScoresByPlayer() {
        Map<Player, Integer> scoresByPlayer = new HashMap<>();
        for (Player player : players) {
            scoresByPlayer.put(player, board.determineTerritorySizeFromCell(player.getStartingCell()));
        }
        return scoresByPlayer;
    }

    public void addPlayer(String playerName) {
        Cell startingCell = board.provideFreeStartingCell();
        Player player = new Player(playerName, startingCell, true);
        players.add(player);
    }

    void addComputer(String computerName) {
        Cell startingCell = board.provideFreeStartingCell();
        Player player = new Player(computerName, startingCell, false);
        players.add(player);
    }

    @Deprecated
    public boolean currentPlayerChooseColor(Color color) {
        if (!isColorAvailable(color))
            return false;

        currentPlayerPlays(color);
        return true;
    }

    List<Color> determineAvailableColors() {
        return Arrays.stream(Color.values())
                .filter(this::isColorAvailable)
                .collect(Collectors.toList());
    }

    private boolean isColorAvailable(Color color) {
        Optional<Cell> sameColorCell = players.stream()
                .map(Player::getStartingCell)
                .filter(cell -> cell.isOfColor(color))
                .findFirst();
        return !sameColorCell.isPresent();
    }

    void currentPlayerPlays(Color color) {
        board.changeColor(getCurrentPlayerStartingCell(), color);
        if (!isFinished()) {
            nextPlayer();
        }
    }

    void computerPlays() {
        Color color = AI.determineColorToPlaySmart(board, getCurrentPlayerStartingCell(), determineAvailableColors());
        currentPlayerPlays(color);
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean isFinished() {
        int playerTerritorySize = board.determineTerritorySizeFromCell(getCurrentPlayerStartingCell());
        int boardSize = board.getBoardSize();
        return playerTerritorySize >= boardSize / players.size();
    }

    boolean isPlayerInGame(String playerName) {
        for (Player player : players) {
            if (playerName.equals(player.getName()))
                return true;
        }
        return false;
    }

    Cell getCurrentPlayerStartingCell() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.getStartingCell();
    }

    String getCurrentPlayerName() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.getName();
    }

    boolean currentPlayerIsHuman() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.isHuman();
    }
}
