package domain.game;

import domain.ConsolePrinter;
import domain.board.Board;
import domain.board.Dimension;
import domain.board.RectangularBoard;
import domain.color.Color;
import domain.color.ColorGeneratorRandom;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Runner {
    public static void main(String[] args) {
        Game game = initGameWithBoardDimensionFromInput();
        addPlayersFromInput(game);
        run(game);
    }

    private static void run(Game game) {
        while (!game.isFinished()) {
            game.showBoard();
            if (game.currentPlayerIsHuman()) {
                askColor(game);
            } else {
                game.computerPlays();
            }
        }
        System.out.println(" ");
        System.out.println("Game finished");
        game.showBoard();
        displayScores(game);
    }

    private static void addPlayersFromInput(Game game) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Set Number of players (2-4) :");
        int requestedNumberOfPlayers = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < requestedNumberOfPlayers; i++) {
            int playerId = i + 1;
            System.out.println("Player " + playerId + " name :");
            game.addPlayer(scanner.nextLine());
        }

        if (requestedNumberOfPlayers == 1) {
            game.addComputer("GLaDOS");
        }
    }

    private static Game initGameWithBoardDimensionFromInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Set Board width :");
        int width = Integer.parseInt(scanner.nextLine());

        System.out.println("Set Board height :");
        int height = Integer.parseInt(scanner.nextLine());

        Dimension boardDimension = new Dimension(width, height);
        Board board = new RectangularBoard(boardDimension, new ColorGeneratorRandom(), new ConsolePrinter());
        return new Game(board);
    }

    private static void askColor(Game game) {
        System.out.print(game.getCurrentPlayerName() + game.getCurrentPlayerStartingCell() +  " : Choose color (Available colors : ");
        List<Color> availableColors = game.determineAvailableColors();
        availableColors.forEach(color -> System.out.print(color.toString()));
        System.out.println(")");

        Scanner scanner = new Scanner(System.in);
        String chosenColor = scanner.nextLine().toUpperCase();
        if (!Color.exists(chosenColor) || !game.currentPlayerChooseColor(Color.valueOf(chosenColor))) {
            System.out.println("Color not available");
        }
    }

    private static void displayScores(Game game) {
        Map<Player, Integer> scoresByPlayer = game.getScoresByPlayer();
        scoresByPlayer.keySet().forEach(player -> System.out.println(player + "Score : " + scoresByPlayer.get(player)));
    }
}
