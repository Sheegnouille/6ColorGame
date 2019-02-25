package game.color;

import java.util.stream.Stream;

public enum Color {
    BLUE("[34m"),
    GREEN("[32m"),
    RED("[31m"),
    ORANGE("[33m"),
    CYAN("[36m"),
    PURPLE("[35m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public static boolean exists(String colorName) {
        return Stream.of(Color.values()).anyMatch(color -> color.name().equals(colorName));
    }

    @Override
    public String toString() {
        return (char) 27 + colorCode + (char) 9632 + (char) 27 + "[0m";
    }
}
