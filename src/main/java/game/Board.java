package game;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private final List<Cell> cells = new ArrayList<>();

    public Board(int width, int height, ColorGenerator colorGenerator) {
        initBoard(width, height, colorGenerator);
    }

    private void initBoard(int width, int height, ColorGenerator colorGenerator) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++){
               cells.add(new Cell(column, row, colorGenerator.getRandomColor()));
            }
        }
    }

    public List<Cell> getContiguousColor(Cell cell) {
        if(!cells.contains(cell)) {
            return new ArrayList<>();
        }
        Set<Cell> result = new HashSet<>();
        Set<Cell> processed = new HashSet<>();
        Optional<Cell> currentProcessingCell = Optional.of(cell);
        while (currentProcessingCell.isPresent()) {
            result.addAll(getAdjacentSameColorCells(currentProcessingCell.get()));
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

    private List<Cell> getAdjacentSameColorCells(Cell cell) {
        return cells.stream()
                .filter(cell::isSameColor)
                .filter(cell::isAdjacentTo)
                .collect(Collectors.toList());
    }

    public void changeColor(List<Cell> cellsToChange, Color color) {
        for (Cell cell : cells) {
            if (cellsToChange.contains(cell))
                cell.changeColor(color);
        }
    }


}
