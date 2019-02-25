package game.board;

import game.color.Color;

public interface Board {

    void show();

    void changeColor(Cell cellToChange, Color color);

    Cell provideFreeStartingCell();

    int determineTerritorySizeFromCell(Cell cell);

    int determineBoardSize();
}
