package game.board;

import game.ConsolePrinter;
import game.Printer;
import game.color.Color;
import game.color.ColorGenerator;

import java.util.*;
import java.util.stream.Collectors;

import static game.board.Position.PositionBuilder.aPosition;

public final class RectangularBoard implements Board {

    private final List<Cell> cells = new ArrayList<>();
    private final Dimension dimension;
    private final Printer printer;
    private final Queue<Cell> possibleStartingCells = new LinkedList<>();

    public RectangularBoard(Dimension dimension, ColorGenerator colorGenerator, Printer printer) {
        this.dimension = dimension;
        this.printer = printer;
        for (int row = 0; row < dimension.getHeight(); row++) {
            for (int column = 0; column < dimension.getWidth(); column++) {
                Cell cellToAdd = new Cell(new Position(column, row), colorGenerator.getNextColor());
                cells.add(cellToAdd);
            }
        }
        populatePossibleStartingCell(dimension);
    }

    private RectangularBoard(Dimension dimension, List<Cell> cells, Printer printer) {
        this.dimension = dimension;
        this.printer = printer;
        this.cells.addAll(cells.stream().map(Cell::copyInstance).collect(Collectors.toList()));
    }

    private static RectangularBoard copyInstance(RectangularBoard board) {
        return new RectangularBoard(board.dimension, board.cells, board.printer);
    }

    //TODO move displaying methods
    public void show() {
        for (int row = 0; row < dimension.getHeight(); row++) {
            for (int column = 0; column < dimension.getWidth(); column++) {
                Position position = new Position(column, row);
                Cell cell = cells.get(position.transformIntoIndex(dimension));
                printer.printCell(cell.showColor());
            }
            printer.returnLine();
        }
        printer.returnLine();
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
    public Cell provideFreeStartingCell() {
        return possibleStartingCells.poll();
    }

    @Override
    public int determineTerritorySizeFromCell(Cell cell) {
        List<Cell> territory = determineTerritory(cell);
        return territory.size();
    }

    private int determineHypotheticalTerritorySize(Cell referenceCell, Color color) {
        RectangularBoard tempBoard = RectangularBoard.copyInstance(this);
        Cell tempCell = Cell.copyInstance(referenceCell);
        tempBoard.changeColor(tempCell, color);
        tempCell.changeColor(color);

        return tempBoard.determineTerritorySizeFromCell(tempCell);
    }

    @Override
    public int determineBoardSize() {
        return cells.size();
    }

    @Override
    public Color determineColorToPlayGreedy(Cell referenceCell, List<Color> possibleColors) {
        Color bestColor = possibleColors.get(0);
        int territorySizeForBestColor = 0;

        for (Color color : possibleColors) {
            int territorySize = determineHypotheticalTerritorySize(referenceCell, color);
            if (territorySize > territorySizeForBestColor) {
                territorySizeForBestColor = territorySize;
                bestColor = color;
            }
        }
        return bestColor;
    }

    @Override
    public Color determineColorToPlaySlow(Cell referenceCell, List<Color> possibleColors) {
        Color worstColor = possibleColors.get(0);
        int baseTerritorySize = determineTerritorySizeFromCell(referenceCell);
        int territorySizeForWorstColor = cells.size();

        for (Color color : possibleColors) {
            int territorySize = determineHypotheticalTerritorySize(referenceCell, color);
            if (territorySize > baseTerritorySize && territorySize < territorySizeForWorstColor) {
                territorySizeForWorstColor = territorySize;
                worstColor = color;
            }
        }
        return worstColor;
    }

    @Override
    public Color determineColorToPlayAnnoying(List<Color> possibleColors) {
        return determineColorToPlayGreedy(cells.get(0), possibleColors);
    }

    @Override
    public Color determineColorToPlaySmart(Cell referenceCell, List<Color> possibleColors) {
        int baseTerritorySize = determineTerritorySizeFromCell(referenceCell);

        Color greedyColor = determineColorToPlayGreedy(referenceCell, possibleColors);
        int territorySizeIfGreedy = determineHypotheticalTerritorySize(referenceCell, greedyColor);
        int gainForGreedyColor = territorySizeIfGreedy - baseTerritorySize;

        Color annoyingColor = determineColorToPlayAnnoying(possibleColors);
        int territorySizeIfAnnoying = determineHypotheticalTerritorySize(referenceCell, annoyingColor);
        int gainForAnnoyingColor = territorySizeIfAnnoying - baseTerritorySize;

        Cell firstCell = cells.get(0);
        int territorySizeOfOtherPlayer = determineHypotheticalTerritorySize(firstCell, annoyingColor);
        int gainForOtherPlayerIfGreedy = territorySizeOfOtherPlayer - determineTerritorySizeFromCell(firstCell);

        return gainForGreedyColor - gainForAnnoyingColor > gainForOtherPlayerIfGreedy
                ? greedyColor
                : annoyingColor;
    }

    @Override
    public Color determineColorToPlayRandom(List<Color> possibleColors) {
        Random random = new Random();
        int randomColorIndex = random.nextInt(possibleColors.size());
        return possibleColors.get(randomColorIndex);
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

    List<Cell> determineTerritory(Cell cell) {
        if (!cells.contains(cell)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(computeContiguousColor(cell));
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
            return new RectangularBoard(new Dimension(width, height), colorGenerator, new ConsolePrinter());
        }
    }
}
