package domain.IO;

import domain.color.Color;
import domain.game.Player;

import java.util.List;
import java.util.Map;

public interface Display {

    void rawText(String text);

    void textBlock(String text);

    void warning(String message);

    void endGame(Map<Player, Integer> scoresByPlayer);

    void returnLine();

    void promptPlayerForColor(String playerName, List<Color> colors);

    void possibleColors(List<Color> colors);

    void scoresTable(Map<Player, Integer> scoresByPlayer);

    void color(Color colorToDisplay);

}
