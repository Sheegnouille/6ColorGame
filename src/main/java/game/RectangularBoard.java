package game;

import java.util.*;
import java.util.stream.Collectors;

public class RectangularBoard implements Board {

    private final List<Cell> cells = new ArrayList<>();
    private final int halfBoardSize;

    public RectangularBoard(int width, int height, ColorGenerator colorGenerator) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cells.add(new Cell(new Position(column, row), colorGenerator.getRandomColor()));
            }
        }
        halfBoardSize = cells.size() / 2;
    }

    @Override
    public void changeColor(Cell cellToChange, Color color) {
        List<Cell> cellsToChange = determineContiguousColor(cellToChange);
        for (Cell cell : cells) {
            if (cellsToChange.contains(cell))
                cell.changeColor(color);
        }
    }

    @Override
    public Cell provideFreeStartingCell() {
        return cells.get(0);
    }

    @Override
    public int determineTerritorySizeFromCell(Cell cell) {
        List<Cell> territory = determineContiguousColor(cell);
        return territory.size();
    }

    @Override
    public boolean isTerritoryDominant(Cell cell) {
        return determineTerritorySizeFromCell(cell) > halfBoardSize;
    }

    List<Cell> determineContiguousColor(Cell cell) {
        if (!cells.contains(cell)) {
            return new ArrayList<>();
        }
        Set<Cell> result = new HashSet<>();
        Set<Cell> processed = new HashSet<>();
        Optional<Cell> currentProcessingCell = Optional.of(cell);
        while (currentProcessingCell.isPresent()) {
            result.addAll(determineAdjacentCellsOfSameColor(currentProcessingCell.get()));
            result.add(currentProcessingCell.get());
            processed.add(currentProcessingCell.get());
            currentProcessingCell = findFirstUnprocessedCell(result, processed);
        }

        return new ArrayList<>(result);
    }

    private Optional<Cell> findFirstUnprocessedCell(Set<Cell> cells, Set<Cell> processedCells) {
        return cells.stream()
                .filter(newCell -> !processedCells.contains(newCell))
                .findFirst();
    }

    private List<Cell> determineAdjacentCellsOfSameColor(Cell cell) {
        return cells.stream()
                .filter(cell::isSameColor)
                .filter(cell::isAdjacentTo)
                .collect(Collectors.toList());
    }
}
