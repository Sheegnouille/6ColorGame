package domain;

import domain.game.Game;
import domain.board.Board;
import domain.color.ColorGenerator;
import domain.color.ColorGeneratorFake;
import org.junit.Test;

import static domain.board.RectangularBoard.RectangularBoardBuilder.aRectangularBoard;
import static domain.color.Color.*;
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
                RED, BLUE, GREEN, RED, BLUE,
                BLUE, GREEN, RED, BLUE, GREEN,
                GREEN, RED, BLUE, GREEN, RED,
                RED, BLUE, GREEN, RED, BLUE,
                BLUE, GREEN, RED, BLUE, GREEN);
        Board board = aRectangularBoard()
                .withWidth(5)
                .withHeight(5)
                .withColorGenerator(colorGenerator)
                .build();
        Game game = new Game(board);
        game.addPlayer("1");
        game.addPlayer("2");

        // ACT
        game.currentPlayerChooseColor(BLUE);
        game.currentPlayerChooseColor(RED);
        game.currentPlayerChooseColor(GREEN);
        game.currentPlayerChooseColor(BLUE);
        game.currentPlayerChooseColor(RED);
        game.currentPlayerChooseColor(GREEN);
        game.currentPlayerChooseColor(BLUE);

        // ASSERT
        assertThat(game.isFinished()).isTrue();
    }
}
