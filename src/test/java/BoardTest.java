import game.Board;
import game.Cell;
import game.ColorGeneratorFake;
import org.junit.Ignore;
import org.junit.Test;

import static game.Color.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Test
    public void all_cells_are_contiguous_and_same_color() {
        Board board = new Board(1, 2, new ColorGeneratorFake(BLUE, BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        Cell cell2 = new Cell(0, 1, BLUE);
        assertThat(board.getContiguousColor(cell1)).containsExactlyInAnyOrder(cell1, cell2);
    }

    @Test
    public void both_cells_have_different_colors_therefore_not_contiguous() {
        Board board = new Board(1, 2, new ColorGeneratorFake(BLUE, RED));
        Cell cell1 = new Cell(0, 0, BLUE);
        assertThat(board.getContiguousColor(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void a_cell_with_same_color_is_not_contiguous() {
        Board board = new Board(1, 3, new ColorGeneratorFake(BLUE, RED, BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        assertThat(board.getContiguousColor(cell1)).containsExactlyInAnyOrder(cell1);
    }

    @Test
    public void all_cells_have_same_color_therefore_are_contiguous() {
        Board board = new Board(1, 3, new ColorGeneratorFake(BLUE));
        Cell cell1 = new Cell(0, 0, BLUE);
        Cell cell2 = new Cell(0, 1, BLUE);
        Cell cell3 = new Cell(0, 2, BLUE);
        assertThat(board.getContiguousColor(cell1)).containsExactlyInAnyOrder(cell1, cell2, cell3);
    }

    @Test
    public void given_complex_red_and_blue_board_should_give_adjacent_cells() {
        Board board = new Board(5, 5, new ColorGeneratorFake(
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
        assertThat(board.getContiguousColor(cell1)).containsExactlyInAnyOrder(
                cell1, cell2, cell3,
                cell4, cell5, cell6,
                cell7, cell8,
                cell9, cell10, cell11,
                cell12);
    }
}
