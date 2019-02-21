import game.Board;
import game.ColorGenerator;
import game.ColorGeneratorFake;
import game.Game;
import org.junit.Test;

import static game.Color.BLUE;
import static game.Color.RED;
import static game.RectangularBoard.RectangularBoardBuilder.aRectangularBoard;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    @Test
    public void game_with_one_player_should_finish_after_one_change_of_color() {
        // ARRANGE
        ColorGenerator colorGenerator = new ColorGeneratorFake(
                BLUE, RED, RED,
                RED, RED, RED,
                RED, RED, RED);
        Board board = aRectangularBoard()
                .withWidth(3)
                .withHeight(3)
                .withColorGenerator(colorGenerator)
                .build();
        Game game = new Game(board);
        game.addPlayer("1");

        // ACT
        game.currentPlayerChooseColor(RED);

        // ASSERT
        assertThat(game.isFinished()).isTrue();
    }
}
