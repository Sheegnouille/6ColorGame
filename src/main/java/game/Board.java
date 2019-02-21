package game;

public interface Board {
    void changeColor(Cell cellToChange, Color color);

    Cell provideFreeStartingCell();

    int determineTerritorySizeFromCell(Cell cell);

    boolean isTerritoryDominant(Cell cell);
}
