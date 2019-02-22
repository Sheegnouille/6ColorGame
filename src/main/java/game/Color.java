package game;

import java.util.stream.Stream;

public enum Color {
    BLUE, GREEN, RED, YELLOW, ORANGE, PURPLE;

    public static boolean exists(String colorName) {
        return Stream.of(Color.values()).anyMatch(color -> color.name().equals(colorName));
    }
}
