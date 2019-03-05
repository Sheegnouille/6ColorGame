package domain.color;

import java.util.stream.Stream;

public enum Color {
    BLUE((char) 27 + "[44m"),
    GREEN((char) 27 + "[42m"),
    RED((char) 27 + "[41m"),
    YELLOW((char) 27 + "[103m"),
    CYAN((char) 27 + "[46m"),
    PURPLE((char) 27 + "[45m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public static boolean exists(String colorName) {
        return Stream.of(Color.values()).anyMatch(color -> color.name().equals(colorName));
    }

    @Override
    public String toString() {
        return colorCode + "  " + (char) 27 + "[0m";
    }
}
