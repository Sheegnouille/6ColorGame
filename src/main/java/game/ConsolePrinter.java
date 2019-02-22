package game;

public class ConsolePrinter implements Printer {

    @Override
    public void printLine(String toPrint) {
        System.out.println(toPrint);
    }
}
