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
        Dimension boardDimension = new Dimension(10, 10);
        ColorGenerator colorGenerator = new ColorGeneratorRandom();
        Board board = new RectangularBoard(boardDimension, colorGenerator, new ConsolePrinter());
        Game game = new Game(board);
        game.addPlayer("Benoit");
        game.addPlayer("Yann");
        game.addPlayer("Clément");
        game.addPlayer("Jean-Baptiste");
        Scanner scanner = new Scanner(System.in);
        while (!game.isFinished()) {
            System.out.println(game.getCurrentPlayer() + " : Choose color");
            String chosenColor = scanner.nextLine();
            if (!Color.exists(chosenColor) || !game.currentPlayerChooseColor(Color.valueOf(chosenColor))) {
                System.out.println("Color not available");
            }
        }
        System.out.println(game.getCurrentPlayer());
    }
}
