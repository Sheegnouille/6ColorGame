package game.color;

import game.Color;
import game.ColorGenerator;

import java.util.Random;

public class ColorGeneratorRandom implements ColorGenerator {

    private final Random random = new Random();
    private final Color[] colorValues = Color.values();

    @Override
    public Color getRandomColor() {
        int randomIndex = random.nextInt(colorValues.length);
        return colorValues[randomIndex];
    }
}
