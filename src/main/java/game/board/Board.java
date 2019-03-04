package game.board;

import game.color.Color;

import java.util.List;

public interface Board {

    void show();

    void changeColor(Cell cellToChange, Color color);

    Cell provideFreeStartingCell();

    int determineTerritorySizeFromCell(Cell cell);

    int determineBoardSize();

    Color determineColorToPlayGreedy(Cell referenceCell, List<Color> possibleColors);
}
