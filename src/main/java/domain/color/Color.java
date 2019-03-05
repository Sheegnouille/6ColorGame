package domain.color;

import java.util.stream.Stream;

public enum Color {
    BLUE,
    GREEN,
    RED,
    YELLOW,
    CYAN,
    PURPLE;

    public static boolean exists(String colorName) {
        return Stream.of(Color.values()).anyMatch(color -> color.name().equals(colorName));
    }
}
