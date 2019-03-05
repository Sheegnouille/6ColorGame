package domain;

import domain.IO.ConsoleDisplay;
import domain.color.Color;
import org.junit.Test;

public class ConsoleDisplayTest {

    @Test
    public void board_display() {
        ConsoleDisplay printer = new ConsoleDisplay();
        for (Color color : Color.values()) {
            printer.color(color);
        }
    }
}