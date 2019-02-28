import game.ConsolePrinter;
import game.Game;
import game.board.Board;
import game.board.Dimension;
import game.board.RectangularBoard;
import game.color.Color;
import game.color.ColorGeneratorRandom;

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
            game.askColor();
            String chosenColor = scanner.nextLine().toUpperCase();
            if (!Color.exists(chosenColor) || !game.currentPlayerChooseColor(Color.valueOf(chosenColor))) {
                System.out.println("Color not available");
            }
        }
        System.out.println(" ");
        System.out.println("Game finished");
        game.displayScore();
    }

    private static void addPlayersFromInput(Game game) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Set Number of players (2-4) :");
        int totalNumberOfPlayers = Integer.parseInt(scanner.nextLine());

        int currentNumberOfPlayers = game.getNumberOfPlayers();
        while (currentNumberOfPlayers < totalNumberOfPlayers) {
            int playerId = currentNumberOfPlayers + 1;
            System.out.println("Player " + playerId + " name :");
            game.addPlayer(scanner.nextLine());
            currentNumberOfPlayers = game.getNumberOfPlayers();
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
}
