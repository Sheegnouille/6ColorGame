package domain.game;

import domain.board.Board;
import domain.board.Cell;
import domain.color.Color;

import java.util.List;
import java.util.Random;

public class AI {
    static Color determineColorToPlayGreedy(Board board, Cell referenceCell, List<Color> possibleColors) {
        Color bestColor = possibleColors.get(0);
        int territorySizeForBestColor = 0;

        for (Color color : possibleColors) {
            int territorySize = board.determineHypotheticalTerritorySize(referenceCell, color);
            if (territorySize > territorySizeForBestColor) {
                territorySizeForBestColor = territorySize;
                bestColor = color;
            }
        }
        return bestColor;
    }

    static Color determineColorToPlaySlow(Board board, Cell referenceCell, List<Color> possibleColors) {
        Color worstColor = possibleColors.get(0);
        int baseTerritorySize = board.determineTerritorySizeFromCell(referenceCell);
        int territorySizeForWorstColor = board.getBoardSize();

        for (Color color : possibleColors) {
            int territorySize = board.determineHypotheticalTerritorySize(referenceCell, color);
            if (territorySize > baseTerritorySize && territorySize < territorySizeForWorstColor) {
                territorySizeForWorstColor = territorySize;
                worstColor = color;
            }
        }
        return worstColor;
    }

    static Color determineColorToPlayRandom(List<Color> possibleColors) {
        Random random = new Random();
        int randomColorIndex = random.nextInt(possibleColors.size());
        return possibleColors.get(randomColorIndex);
    }

    static Color determineColorToPlayAnnoying(Board board, List<Color> possibleColors) {
        return determineColorToPlayGreedy(board, board.getFirstCell(), possibleColors);
    }

    static Color determineColorToPlaySmart(Board board, Cell referenceCell, List<Color> possibleColors) {
        int baseTerritorySize = board.determineTerritorySizeFromCell(referenceCell);

        Color greedyColor = determineColorToPlayGreedy(board, referenceCell, possibleColors);
        int territorySizeIfGreedy = board.determineHypotheticalTerritorySize(referenceCell, greedyColor);
        int gainForGreedyColor = territorySizeIfGreedy - baseTerritorySize;

        Color annoyingColor = determineColorToPlayAnnoying(board, possibleColors);
        int territorySizeIfAnnoying = board.determineHypotheticalTerritorySize(referenceCell, annoyingColor);
        int gainForAnnoyingColor = territorySizeIfAnnoying - baseTerritorySize;

        Cell firstCell = board.getFirstCell();
        int territorySizeOfOtherPlayer = board.determineHypotheticalTerritorySize(firstCell, annoyingColor);
        int gainForOtherPlayerIfGreedy = territorySizeOfOtherPlayer - board.determineTerritorySizeFromCell(firstCell);

        return gainForGreedyColor - gainForAnnoyingColor > gainForOtherPlayerIfGreedy
                ? greedyColor
                : annoyingColor;
    }
}
