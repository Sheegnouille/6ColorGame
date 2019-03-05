package domain;

public class ConsolePrinter implements Printer {

    @Override
    public void printText(String text) {
        System.out.print(text);
    }

    @Override
    public void returnLine() {
        System.out.println();
    }
}
