import game.*;
import org.junit.Test;

import static game.Color.BLUE;
import static game.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    @Test
    public void should_() {
        // ARRANGE
        ColorGenerator colorGenerator = new ColorGeneratorFake(
                BLUE, RED, RED,
                RED, RED, RED,
                RED, RED, RED);
        Board board = new Board(3, 3, colorGenerator);
        Game game = new Game(board);
        Player player = new Player("1");
        game.add(player);

        // ACT
        game.currentPlayerChooseColor(RED);

        // ASSERT
        assertThat(player.getScore()).isEqualTo(Score.valueOf(9));
    }
}
