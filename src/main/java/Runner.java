import game.ConsolePrinter;
import game.Game;
import game.Player;
import game.board.Board;
import game.board.Dimension;
import game.board.RectangularBoard;
import game.color.Color;
import game.color.ColorGeneratorRandom;

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
        Scanner scanner = new Scanner(System.in);
        while (!game.isFinished()) {
            askColor(game);
            String chosenColor = scanner.nextLine().toUpperCase();
            if (!Color.exists(chosenColor) || !game.currentPlayerChooseColor(Color.valueOf(chosenColor))) {
                System.out.println("Color not available");
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
        game.showBoard();
        System.out.print(game.getCurrentPlayerName() + " : Choose color (Available colors : ");
        List<Color> availableColors = game.determineAvailableColors();
        availableColors.forEach(color -> System.out.print(color.toString()));
        System.out.println(")");
    }

    private static void displayScores(Game game) {
        Map<Player, Integer> scoresByPlayer = game.getScoresByPlayer();
        for (Player player : scoresByPlayer.keySet()) {
            System.out.println(player + "Score : " + scoresByPlayer.get(player));
        }
    }
}
