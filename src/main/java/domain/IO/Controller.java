package domain.IO;

import domain.color.Color;

import java.util.List;

public interface Controller {
    Color getColorInput(List<Color> availableColors);

    String getPlayerName();

    int getNumber();
}
