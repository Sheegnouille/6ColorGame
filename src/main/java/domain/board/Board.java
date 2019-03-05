package domain.board;

import domain.color.Color;

import java.util.List;

public interface Board {

    void show();

    void changeColor(Cell cellToChange, Color color);

    Cell provideFreeStartingCell();

    int determineTerritorySizeFromCell(Cell referenceCell);

    int determineHypotheticalTerritorySize(Cell referenceCell, Color color);

    int getBoardSize();

    Cell getFirstCell();
}
