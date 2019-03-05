package game;

public class ConsolePrinter implements Printer {

    @Override
    public void printCell(String cell) {
        System.out.print(cell);
    }

    @Override
    public void returnLine() {
        System.out.println();
    }
}
