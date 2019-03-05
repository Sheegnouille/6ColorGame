package domain.IO;

import domain.color.Color;
import domain.game.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.color.Color.*;

public class ConsoleDisplay implements Display {

    private static final Map<Color, String> colorPallet = new HashMap<>();

    static {
        colorPallet.put(BLUE, ("[44m"));
        colorPallet.put(GREEN, ("[42m"));
        colorPallet.put(RED, ("[41m"));
        colorPallet.put(YELLOW, ("[103m"));
        colorPallet.put(CYAN, ("[46m"));
        colorPallet.put(PURPLE, ("[45m"));
    }

    @Override
    public void rawText(String text) {
        System.out.print(text);
    }

    @Override
    public void textBlock(String text) {
        System.out.println(text);
    }

    @Override
    public void warning(String message) {
        textBlock(message);
    }

    @Override
    public void endGame(Map<Player, Integer> scoresByPlayer) {
        textBlock("Game Finished!");
        scoresTable(scoresByPlayer);
    }

    @Override
    public void returnLine() {
        System.out.println();
    }

    @Override
    public void promptPlayerForColor(String playerName, List<Color> colors) {
        rawText(playerName + " : Choose color (Available colors : ");
        possibleColors(colors);
        textBlock(")");
    }

    @Override
    public void possibleColors(List<Color> colors) {
        colors.forEach(this::color);
    }

    @Override
    public void scoresTable(Map<Player, Integer> scoresByPlayer) {
        scoresByPlayer.keySet().forEach(player -> textBlock(player + "Score : " + scoresByPlayer.get(player)));
    }

    @Override
    public void color(Color colorToDisplay) {
        rawText((char) 27 + colorPallet.get(colorToDisplay) + "  " + (char) 27 + "[0m");
    }

}
