package domain.game;

import domain.board.Cell;
import domain.board.RectangularBoard;
import domain.color.Color;
import domain.color.ColorGeneratorFake;
import org.junit.Test;

import java.util.Arrays;

import static domain.board.Position.PositionBuilder.aPosition;
import static domain.board.RectangularBoard.RectangularBoardBuilder.aRectangularBoard;
import static domain.color.Color.*;
import static domain.color.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;

public class AITest {
    @Test
    public void computer_determines_best_color_to_play() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(2)
                .withHeight(2)
                .withColorGenerator(new ColorGeneratorFake(
                        RED, BLUE,
                        BLUE, BLUE
                ))
                .build();

        Cell topLeft = new Cell(aPosition().withColumn(0).withRow(0).build(), RED);
        Color colorChoseByTheComputer = AI.determineColorToPlayGreedy(board, topLeft, Arrays.asList(Color.values()));

        assertThat(colorChoseByTheComputer).isEqualTo(BLUE);
    }

    @Test
    public void computer_has_a_territory_of_5_after_playing() {
        RectangularBoard board = aRectangularBoard()
                .withWidth(3)
                .withHeight(3)
                .withColorGenerator(new ColorGeneratorFake(
                        BLUE, BLUE, BLUE,
                        GREEN, RED, GREEN,
                        GREEN, GREEN, RED
                ))
                .build();

        Cell centerCell = new Cell(aPosition().withColumn(1).withRow(1).build(), RED);
        Color colorChoseByTheComputer = AI.determineColorToPlayGreedy(board, centerCell, Arrays.asList(Color.values()));
        board.changeColor(centerCell, colorChoseByTheComputer);
        Cell newCenterCell = new Cell(aPosition().withColumn(1).withRow(1).build(), GREEN);

        assertThat(board.determineTerritorySizeFromCell(newCenterCell)).isEqualTo(5);
    }
}
