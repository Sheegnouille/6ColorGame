import game.ConsolePrinter;
import game.Game;
import game.board.Board;
import game.board.Dimension;
import game.board.RectangularBoard;
import game.color.Color;
import game.color.ColorGenerator;
import game.color.ColorGeneratorRandom;

import java.util.Scanner;

class Runner {
    public static void main(String[] args) {
        Dimension boardDimension = new Dimension(40, 40);
        ColorGenerator colorGenerator = new ColorGeneratorRandom();
        Board board = new RectangularBoard(boardDimension, colorGenerator, new ConsolePrinter());
        Game game = new Game(board);

        Scanner scanner = new Scanner(System.in);
        boolean addAnotherPlayer = true;
        while ((game.getNumberOfPlayers() < 4) && addAnotherPlayer) {
            System.out.println("New player name :");
            game.addPlayer(scanner.nextLine());
            if (game.getNumberOfPlayers() > 1 && game.getNumberOfPlayers() < 4) {
                String answer;
                do {
                    System.out.println("Would you like to add another player (Yes/No)");
                    answer = scanner.nextLine();
                } while (!(answer.equalsIgnoreCase("Yes") ||
                        answer.equalsIgnoreCase("No")));
                if (answer.equalsIgnoreCase("No")) {
                    addAnotherPlayer = false;
                }
            }
        }

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

}
