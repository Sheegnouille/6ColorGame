package domain.board;

import domain.IO.Display;
import domain.color.Color;
import domain.color.ColorGenerator;

import java.util.*;
import java.util.stream.Collectors;

import static domain.board.Position.PositionBuilder.aPosition;

public final class RectangularBoard implements Board {

    private final List<Cell> cells = new ArrayList<>();
    private final Dimension dimension;
    private final Queue<Cell> possibleStartingCells = new LinkedList<>();

    public RectangularBoard(Dimension dimension, ColorGenerator colorGenerator) {
        this.dimension = dimension;
        for (int row = 0; row < dimension.getHeight(); row++) {
            for (int column = 0; column < dimension.getWidth(); column++) {
                Cell cellToAdd = new Cell(new Position(column, row), colorGenerator.getNextColor());
                cells.add(cellToAdd);
            }
        }
        populatePossibleStartingCell(dimension);
    }

    private RectangularBoard(Dimension dimension, List<Cell> cells) {
        this.dimension = dimension;
        this.cells.addAll(cells.stream().map(Cell::copyInstance).collect(Collectors.toList()));
    }

    @Override
    public void show(Display display) {
        for (int row = 0; row < dimension.getHeight(); row++) {
            for (int column = 0; column < dimension.getWidth(); column++) {
                Position position = new Position(column, row);
                Cell cell = cells.get(position.transformIntoIndex(dimension));
                cell.show(display);
            }
            display.returnLine();
        }
        display.returnLine();
    }

    @Override
    public void changeColor(Cell cellToChange, Color color) {
        List<Cell> cellsToChange = determineTerritory(cellToChange);
        for (Cell cell : cells) {
            if (cellsToChange.contains(cell))
                cell.changeColor(color);
        }
    }

    @Override
    public int determineTerritorySizeFromCell(Cell referenceCell) {
        List<Cell> territory = determineTerritory(referenceCell);
        return territory.size();
    }

    @Override
    public int determineHypotheticalTerritorySize(Cell referenceCell, Color color) {
        RectangularBoard tempBoard = new RectangularBoard(this.dimension, this.cells);
        Cell tempCell = Cell.copyInstance(referenceCell);
        tempBoard.changeColor(tempCell, color);
        tempCell.changeColor(color);

        return tempBoard.determineTerritorySizeFromCell(tempCell);
    }

    @Override
    public Cell provideFreeStartingCell() {
        return possibleStartingCells.poll();
    }

    @Override
    public int getBoardSize() {
        return cells.size();
    }

    public Cell getFirstCell() {
        return cells.get(0);
    }

    List<Cell> determineTerritory(Cell cell) {
        if (!cells.contains(cell)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(computeContiguousColor(cell));
    }

    private void populatePossibleStartingCell(Dimension dimension) {
        Position topLeft = aPosition()
                .withColumn(0)
                .withRow(0).build();
        Position bottomRight = aPosition()
                .withColumn(dimension.getWidth() - 1)
                .withRow(dimension.getHeight() - 1).build();
        Position topRight = aPosition()
                .withColumn(dimension.getWidth() - 1)
                .withRow(0).build();
        Position bottomLeft = aPosition()
                .withColumn(0)
                .withRow(dimension.getHeight() - 1).build();

        addStartingCell(dimension, topLeft);
        addStartingCell(dimension, bottomRight);
        addStartingCell(dimension, topRight);
        addStartingCell(dimension, bottomLeft);
    }

    private void addStartingCell(Dimension dimension, Position position) {
        int topLeftCellIndex = position.transformIntoIndex(dimension);
        possibleStartingCells.add(cells.get(topLeftCellIndex));
    }

    private Set<Cell> computeContiguousColor(Cell cell) {
        Set<Cell> result = new HashSet<>();
        Set<Cell> processed = new HashSet<>();
        Optional<Cell> currentProcessingCell = Optional.of(cell);
        while (currentProcessingCell.isPresent()) {
            result.addAll(determineAdjacentCellsOfSameColor(currentProcessingCell.get()));
            result.add(currentProcessingCell.get());
            processed.add(currentProcessingCell.get());
            currentProcessingCell = findFirstUnprocessedCell(result, processed);
        }
        return result;
    }

    private List<Cell> determineAdjacentCellsOfSameColor(Cell cell) {
        return cells.stream()
                .filter(cell::isSameColor)
                .filter(cell::isAdjacentTo)
                .collect(Collectors.toList());
    }

    private Optional<Cell> findFirstUnprocessedCell(Set<Cell> cells, Set<Cell> processedCells) {
        return cells.stream()
                .filter(newCell -> !processedCells.contains(newCell))
                .findFirst();
    }

    public static final class RectangularBoardBuilder {
        private int width;
        private int height;
        private ColorGenerator colorGenerator;

        private RectangularBoardBuilder() {
        }

        public static RectangularBoardBuilder aRectangularBoard() {
            return new RectangularBoardBuilder();
        }

        public RectangularBoardBuilder withWidth(int width) {
            this.width = width;
            return this;
        }

        public RectangularBoardBuilder withHeight(int height) {
            this.height = height;
            return this;
        }

        public RectangularBoardBuilder withColorGenerator(ColorGenerator colorGenerator) {
            this.colorGenerator = colorGenerator;
            return this;
        }

        public RectangularBoard build() {
            return new RectangularBoard(new Dimension(width, height), colorGenerator);
        }
    }
}
