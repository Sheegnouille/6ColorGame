package domain.game;

import domain.IO.ConsoleController;
import domain.IO.ConsoleDisplay;
import domain.IO.Controller;
import domain.IO.Display;
import domain.board.Board;
import domain.board.Dimension;
import domain.board.RectangularBoard;
import domain.color.Color;
import domain.color.ColorGeneratorRandom;

import java.util.List;

class Runner {

    private final Display display;
    private final Controller controller;

    private Runner(Display display, Controller controller) {
        this.display = display;
        this.controller = controller;
    }

    public static void main(String[] args) {
        Display display = new ConsoleDisplay();
        Controller controller = new ConsoleController();

        Runner runner = new Runner(display, controller);

        Game game = runner.initGameWithBoardDimensionFromInput();
        runner.addPlayersFromInput(game);
        runner.run(game);
    }

    private void run(Game game) {
        while (!game.isFinished()) {
            game.showBoard(display);
            if (game.currentPlayerIsHuman()) {
                Color color = askColor(game);
                game.currentPlayerPlays(color);
            } else {
                game.computerPlays();
            }
        }
        game.showBoard(display);
        display.endGame(game.getScoresByPlayer());
    }

    public void addPlayersFromInput(Game game) {
        display.textBlock("Set Number of players (2-4) :");
        int requestedNumberOfPlayers = controller.getNumber();

        for (int i = 0; i < requestedNumberOfPlayers; i++) {
            int playerId = i + 1;
            display.textBlock("Player " + playerId + " name :");
            game.addPlayer(controller.getPlayerName());
        }

        if (requestedNumberOfPlayers == 1) {
            game.addComputer("GLaDOS");
        }
    }

    public Game initGameWithBoardDimensionFromInput() {
        display.textBlock("Set Board width :");
        int width = controller.getNumber();

        display.textBlock("Set Board height :");
        int height = controller.getNumber();

        Dimension boardDimension = new Dimension(width, height);
        Board board = new RectangularBoard(boardDimension, new ColorGeneratorRandom());
        return new Game(board);
    }

    private Color askColor(Game game) {
        List<Color> availableColors = game.determineAvailableColors();

        display.promptPlayerForColor(game.getCurrentPlayerName(), availableColors);
        return controller.getColorInput(availableColors);
    }
}
