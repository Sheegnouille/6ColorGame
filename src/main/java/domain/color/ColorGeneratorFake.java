package domain.color;

public class ColorGeneratorFake implements ColorGenerator {

    private final Color[] colors;
    private int i = 0;

    public ColorGeneratorFake(Color... colors) {

        this.colors = colors;
    }

    @Override
    public Color getNextColor() {
        return colors[i++ % colors.length];
    }
}
