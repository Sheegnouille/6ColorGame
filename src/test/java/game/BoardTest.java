package game;

import org.junit.Test;

import static game.Color.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Test
    public void all_cells_are_contiguous_and_same_color() {
        RectangularBoard board = new RectangularBoard(1, 2, new ColorGeneratorFake(BLUE, BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        Cell cell2 = new Cell(0, 1, BLUE);
        assertThat(board.determineContiguousColor(cell1)).containsExactlyInAnyOrder(cell1, cell2);
    }

    @Test
    public void both_cells_have_different_colors_therefore_not_contiguous() {
        RectangularBoard board = new RectangularBoard(1, 2, new ColorGeneratorFake(BLUE, RED));
        Cell cell1 = new Cell(0, 0, BLUE);
        assertThat(board.determineContiguousColor(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void a_cell_with_same_color_is_not_contiguous() {
        RectangularBoard board = new RectangularBoard(1, 3, new ColorGeneratorFake(BLUE, RED, BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        assertThat(board.determineContiguousColor(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void all_cells_have_same_color_therefore_are_contiguous() {
        RectangularBoard board = new RectangularBoard(1, 3, new ColorGeneratorFake(BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        Cell cell2 = new Cell(0, 1, BLUE);
        Cell cell3 = new Cell(0, 2, BLUE);
        assertThat(board.determineContiguousColor(cell1)).containsExactlyInAnyOrder(cell1, cell2, cell3);
    }

    @Test
    public void given_complex_red_and_blue_board_should_give_adjacent_cells() {
        RectangularBoard board = new RectangularBoard(5, 5, new ColorGeneratorFake(
                BLUE, BLUE, BLUE, RED, BLUE,
                BLUE, RED, BLUE, BLUE, RED,
                BLUE, RED, RED, BLUE, RED,
                BLUE, BLUE, BLUE, RED, RED,
                RED, RED, BLUE, RED, BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        Cell cell2 = new Cell(1, 0, BLUE);
        Cell cell3 = new Cell(2, 0, BLUE);
        Cell cell4 = new Cell(0, 1, BLUE);
        Cell cell5 = new Cell(2, 1, BLUE);
        Cell cell6 = new Cell(3, 1, BLUE);
        Cell cell7 = new Cell(0, 2, BLUE);
        Cell cell8 = new Cell(3, 2, BLUE);
        Cell cell9 = new Cell(0, 3, BLUE);
        Cell cell10 = new Cell(1, 3, BLUE);
        Cell cell11 = new Cell(2, 3, BLUE);
        Cell cell12 = new Cell(2, 4, BLUE);
        assertThat(board.determineContiguousColor(cell1)).containsExactlyInAnyOrder(
                cell1, cell2, cell3,
                cell4, cell5, cell6,
                cell7, cell8, cell9,
                cell10, cell11, cell12);
    }

    @Test
    public void player_starts_as_blue_and_choose_red_should_have_all_cells() {
        RectangularBoard board = new RectangularBoard(2, 2, new ColorGeneratorFake(BLUE, BLUE, BLUE, RED));
        Cell topLeftBlue = new Cell(0, 0, BLUE);

        board.changeColor(topLeftBlue, RED);

        Cell topLeft = new Cell(0,0, RED);
        Cell topRight= new Cell(1,0, RED);
        Cell bottomLeft = new Cell(0,1, RED);
        Cell bottomRight = new Cell(1,1, RED);
        assertThat(board.determineContiguousColor(topLeft)).containsExactlyInAnyOrder(
                topLeft, topRight,
                bottomLeft, bottomRight);
    }

    @Test
    public void change_all_cell_to_red() {
        RectangularBoard board = new RectangularBoard(4, 4, new ColorGeneratorFake(RED, BLUE, GREEN));
        Cell topLeft = new Cell(0, 0, RED);

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

        assertThat(board.determineContiguousColor(topLeft).size()).isEqualTo(16);
    }

    @Test
    public void color_propagates_from_center() {
        RectangularBoard board = new RectangularBoard(5, 5, new ColorGeneratorFake(
                RED, RED, RED, RED, RED,
                RED, BLUE, BLUE, BLUE, RED,
                RED, BLUE, RED, BLUE, RED,
                RED, BLUE, BLUE, BLUE, RED,
                RED, RED, RED, RED, RED
                ));

        Cell center = new Cell(2,2, RED);

        board.changeColor(center, BLUE);
        center.changeColor(BLUE);

        board.changeColor(center, RED);
        center.changeColor(RED);

        assertThat(board.determineContiguousColor(center).size()).isEqualTo(25);
    }
}
