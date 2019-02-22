package game.color;

public class ColorGeneratorFake implements ColorGenerator {

    private final Color[] colors;
    private int i = 0;

    public ColorGeneratorFake(Color... colors) {

        this.colors = colors;
    }

    @Override
    public Color getRandomColor() {
        return colors[i++ % colors.length];
    }
}
