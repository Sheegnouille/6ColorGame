import game.Board;
import game.ColorGenerator;
import game.ColorGeneratorFake;
import game.Game;
import org.junit.Test;

import static game.Color.*;
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

    @Test
    public void game_with_two_players_should_finish_when_one_player_territory_is_dominant() {
        // ARRANGE
        ColorGenerator colorGenerator = new ColorGeneratorFake(
                BLUE, BLUE, BLUE,
                RED, RED, RED,
                GREEN, GREEN, GREEN);
        Board board = aRectangularBoard()
                .withWidth(10)
                .withHeight(10)
                .withColorGenerator(colorGenerator)
                .build();
        Game game = new Game(board);
        game.addPlayer("1");
        game.addPlayer("2");

        // ACT
        game.currentPlayerChooseColor(BLUE);
        game.currentPlayerChooseColor(GREEN);
        game.currentPlayerChooseColor(RED);
        game.currentPlayerChooseColor(BLUE);
        game.currentPlayerChooseColor(GREEN);
        game.currentPlayerChooseColor(RED);

        // ASSERT
        assertThat(game.isFinished()).isTrue();
    }
}
