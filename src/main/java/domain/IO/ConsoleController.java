package domain.IO;

import domain.color.Color;

import java.util.List;
import java.util.Scanner;

public class ConsoleController implements Controller {

    private Scanner scanner = new Scanner(System.in);

    private String getInput() {
        return scanner.nextLine();
    }

    @Override
    public Color getColorInput(List<Color> availableColors) {
        String inputColor;
        do {
            inputColor = getInput().toUpperCase();
            if (!Color.exists(inputColor)) {
                System.out.println("Color not available");
            }
        } while (!Color.exists(inputColor) || !availableColors.contains(Color.valueOf(inputColor)));

        return Color.valueOf(inputColor);
    }

    @Override
    public String getPlayerName() {
        return getInput();
    }

    @Override
    public int getNumber() {
        return Integer.parseInt(getInput());
    }
}
