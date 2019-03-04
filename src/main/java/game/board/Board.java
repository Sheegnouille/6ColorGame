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

    Color determineColorToPlaySlow(Cell referenceCell, List<Color> possibleColors);

    Color determineColorToPlayAnnoying(List<Color> possibleColors);

    Color determineColorToPlaySmart(Cell referenceCell, List<Color> possibleColors);

    Color determineColorToPlayRandom(List<Color> possibleColors);
}
