package game;

import org.junit.Test;

import static game.Color.*;
import static game.Position.PositionBuilder.aPosition;
import static game.RectangularBoard.RectangularBoardBuilder.aRectangularBoard;
import static org.assertj.core.api.Assertions.assertThat;

public class RectangularBoardTest {

    @Test
    public void all_cells_are_contiguous_and_same_color() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(1)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(BLUE, BLUE))
                .build();
        Cell cell1 = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        Cell cell2 = new Cell(aPosition().withColumn(0).withRow(1).build(), BLUE);
        assertThat(board.determineTerritory(cell1)).containsExactlyInAnyOrder(cell1, cell2);
    }

    @Test
    public void both_cells_have_different_colors_therefore_not_contiguous() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(1)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(BLUE, RED))
                .build();
        Cell cell1 = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        assertThat(board.determineTerritory(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void a_cell_with_same_color_is_not_contiguous() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(1)
                .withHeight(3)
                .withColorGenerator(new ColorGeneratorFake(BLUE, RED, BLUE))
                .build();
        Cell cell1 = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        assertThat(board.determineTerritory(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void all_cells_have_same_color_therefore_are_contiguous() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(1)
                .withHeight(3)
                .withColorGenerator(new ColorGeneratorFake(BLUE))
                .build();
        Cell cell1 = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        Cell cell2 = new Cell(aPosition().withColumn(0).withRow(1).build(), BLUE);
        Cell cell3 = new Cell(aPosition().withColumn(0).withRow(2).build(), BLUE);
        assertThat(board.determineTerritory(cell1)).containsExactlyInAnyOrder(cell1, cell2, cell3);
    }

    @Test
    public void given_complex_red_and_blue_board_should_give_adjacent_cells() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(5)
                .withHeight(5)
                .withColorGenerator(new ColorGeneratorFake(
                        BLUE, BLUE, BLUE, RED, BLUE,
                        BLUE, RED, BLUE, BLUE, RED,
                        BLUE, RED, RED, BLUE, RED,
                        BLUE, BLUE, BLUE, RED, RED,
                        RED, RED, BLUE, RED, BLUE))
                .build();
        Cell cell1 = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        Cell cell2 = new Cell(aPosition().withColumn(1).withRow(0).build(), BLUE);
        Cell cell3 = new Cell(aPosition().withColumn(2).withRow(0).build(), BLUE);
        Cell cell4 = new Cell(aPosition().withColumn(0).withRow(1).build(), BLUE);
        Cell cell5 = new Cell(aPosition().withColumn(2).withRow(1).build(), BLUE);
        Cell cell6 = new Cell(aPosition().withColumn(3).withRow(1).build(), BLUE);
        Cell cell7 = new Cell(aPosition().withColumn(0).withRow(2).build(), BLUE);
        Cell cell8 = new Cell(aPosition().withColumn(3).withRow(2).build(), BLUE);
        Cell cell9 = new Cell(aPosition().withColumn(0).withRow(3).build(), BLUE);
        Cell cell10 = new Cell(aPosition().withColumn(1).withRow(3).build(), BLUE);
        Cell cell11 = new Cell(aPosition().withColumn(2).withRow(3).build(), BLUE);
        Cell cell12 = new Cell(aPosition().withColumn(2).withRow(4).build(), BLUE);
        assertThat(board.determineTerritory(cell1)).containsExactlyInAnyOrder(
                cell1, cell2, cell3,
                cell4, cell5, cell6,
                cell7, cell8, cell9,
                cell10, cell11, cell12);
    }

    @Test
    public void player_starts_as_blue_and_choose_red_should_have_all_cells() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(2)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(BLUE, BLUE, BLUE, RED))
                .build();
        Cell topLeftBlue = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);

        board.changeColor(topLeftBlue, RED);

        Cell topLeft = new Cell(aPosition().withColumn(0).withRow(0).build(), RED);
        Cell topRight = new Cell(aPosition().withColumn(1).withRow(0).build(), RED);
        Cell bottomLeft = new Cell(aPosition().withColumn(0).withRow(1).build(), RED);
        Cell bottomRight = new Cell(aPosition().withColumn(1).withRow(1).build(), RED);
        assertThat(board.determineTerritory(topLeft)).containsExactlyInAnyOrder(
                topLeft, topRight,
                bottomLeft, bottomRight);
    }

    @Test
    public void change_all_cell_to_red() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(4)
                .withHeight(4)
                .withColorGenerator(new ColorGeneratorFake(RED, BLUE, GREEN))
                .build();
        Cell topLeft = new Cell(aPosition().withColumn(0).withRow(0).build(), RED);

        board.changeColor(topLeft, BLUE);
        topLeft.changeColor(BLUE);

        board.changeColor(topLeft, GREEN);
        topLeft.changeColor(GREEN);

        board.changeColor(topLeft, RED);
        topLeft.changeColor(RED);

        board.changeColor(topLeft, BLUE);
        topLeft.changeColor(BLUE);

        board.changeColor(topLeft, GREEN);
        topLeft.changeColor(GREEN);

        board.changeColor(topLeft, RED);
        topLeft.changeColor(RED);

        assertThat(board.determineTerritory(topLeft).size()).isEqualTo(16);
    }

    @Test
    public void color_propagates_from_center() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(5)
                .withHeight(5)
                .withColorGenerator(new ColorGeneratorFake(
                        RED, RED, RED, RED, RED,
                        RED, BLUE, BLUE, BLUE, RED,
                        RED, BLUE, RED, BLUE, RED,
                        RED, BLUE, BLUE, BLUE, RED,
                        RED, RED, RED, RED, RED
                ))
                .build();

        Cell center = new Cell(aPosition().withColumn(2).withRow(2).build(), RED);

        board.changeColor(center, BLUE);
        center.changeColor(BLUE);

        board.changeColor(center, RED);
        center.changeColor(RED);

        assertThat(board.determineTerritory(center).size()).isEqualTo(25);
    }

    @Test
    public void first_free_starting_cell_is_0_0() {
        Cell startingCell = new Cell(aPosition().withColumn(0).withRow(0).build(), RED);
        Board board = aRectangularBoard()
                .withWidth(2)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(RED))
                .build();

        Cell cell = board.provideFreeStartingCell();

        assertThat(cell).isEqualTo(startingCell);
    }

    @Test
    public void single_cell_on_checker_pattern_board_should_not_be_dominant() {
        Board board = aRectangularBoard()
                .withWidth(2)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(
                        BLUE, RED,
                        RED, BLUE))
                .build();
        Cell cell = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        boolean isTerritoryDominant = board.isTerritoryDominant(cell);
        assertThat(isTerritoryDominant).isFalse();
    }

    @Test
    public void one_color_board_should_have_dominant_territory() {
        Board board = aRectangularBoard()
                .withWidth(2)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(BLUE))
                .build();
        Cell cell = new Cell(aPosition().withColumn(0).withRow(0).build(), BLUE);
        boolean isTerritoryDominant = board.isTerritoryDominant(cell);
        assertThat(isTerritoryDominant).isTrue();
    }

    @Test
    public void successive_calls_to_provideFreeStartingCell_should_return_different_cells() {
        Board board = aRectangularBoard()
                .withHeight(2)
                .withWidth(2)
                .withColorGenerator(new ColorGeneratorFake(RED))
                .build();

        Cell firstCell = board.provideFreeStartingCell();
        Cell secondCell = board.provideFreeStartingCell();

        assertThat(firstCell).isNotEqualTo(secondCell);
    }

    @Test
    public void second_starting_cell_should_be_opposite_corner() {
        Board board = aRectangularBoard()
                .withHeight(2)
                .withWidth(2)
                .withColorGenerator(new ColorGeneratorFake(RED))
                .build();

        Cell secondCell = new Cell(aPosition().withRow(1).withColumn(1).build(), RED);
        board.provideFreeStartingCell();

        assertThat(board.provideFreeStartingCell()).isEqualTo(secondCell);
    }
}
