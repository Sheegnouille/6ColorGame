import game.*;
import org.junit.Ignore;
import org.junit.Test;

import static game.Color.BLUE;
import static game.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    @Ignore
    @Test
    public void should_() {
        // ARRANGE
        ColorGenerator colorGenerator = new ColorGeneratorFake(
                BLUE, RED, RED,
                RED, RED, RED,
                RED, RED, RED);
        Board board = new Board(3, 3, colorGenerator);
        Game game = new Game(board);
        game.addPlayer("1");

        // ACT
        game.currentPlayerChooseColor(RED);

        // ASSERT
        assertThat(game.getCurrentPlayer().getScore()).isEqualTo(Score.valueOf(9));
    }
}
