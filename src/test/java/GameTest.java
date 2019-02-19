import game.*;
import org.junit.Test;

import static game.Color.BLUE;
import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    public void given_a_game_with_one_player_should_return_one() {
        Game game = new Game(new Board(1, 1, new ColorGeneratorFake(BLUE)));
        game.add(new Player("Toto"));
        assertThat(game.getNumberOfPlayer()).isEqualTo(1);
    }

    @Test
    public void given_a_game_with_two_players_should_return_two() {
        Game game = new Game(new Board(1, 1, new ColorGeneratorFake(BLUE)));
        game.add(new Player("Toto"));
        game.add(new Player("Tata"));
        assertThat(game.getNumberOfPlayer()).isEqualTo(2);
    }
}
