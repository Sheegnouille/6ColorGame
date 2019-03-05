package domain.board;

import domain.IO.Display;
import domain.color.Color;

public interface Board {

    void show(Display display);

    void changeColor(Cell cellToChange, Color color);

    Cell provideFreeStartingCell();

    int determineTerritorySizeFromCell(Cell referenceCell);

    int determineHypotheticalTerritorySize(Cell referenceCell, Color color);

    int getBoardSize();

    Cell getFirstCell();
}
