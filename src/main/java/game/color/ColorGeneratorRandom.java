package game.color;

import java.util.Random;

public class ColorGeneratorRandom implements ColorGenerator {

    private final Random random = new Random();
    private final Color[] colorValues = Color.values();

    @Override
    public Color getNextColor() {
        int randomIndex = random.nextInt(colorValues.length);
        return colorValues[randomIndex];
    }
}
