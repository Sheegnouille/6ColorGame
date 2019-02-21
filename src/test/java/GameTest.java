import game.Board;
import game.Cell;
import game.Game;
import game.Player;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static game.Color.BLUE;
import static game.Color.RED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameTest {
@Mock
private Board board;
    private Game game;
    private Cell startingCell;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        game = new Game(board);
        startingCell = new Cell(0, 0, RED);
        when(board.getStartingCell()).thenReturn(startingCell);
    }

    @Test
    public void should_add_starting_cell_to_player_when_add_new_player() {
        game.addPlayer("Toto");

        Assertions.assertThat(game.getCurrentPlayerStartingCell()).isEqualTo(startingCell);
        verify(board).getStartingCell();
    }

    @Test
    public void player_starting_cell_changes_color_when_player_chooses_a_color() {
        game.addPlayer("Tata");

        game.currentPlayerChooseColor(BLUE);

        verify(board).changeColor(startingCell, BLUE);
    }

    @Test
    public void current_player_is_the_only_player() {
        game.addPlayer("Toto");

        Assertions.assertThat(game.getCurrentPlayer().hasName("Toto")).isTrue();
    }

    @Test
    public void current_player_is_first_player_added() {
        game.addPlayer("Toto");
        game.addPlayer("Titi");

        Assertions.assertThat(game.getCurrentPlayer().hasName("Toto")).isTrue();
    }

    @Test
    public void current_player_changes_when_player_chooses_a_color() {
        game.addPlayer("Toto");
        game.addPlayer("Titi");

        game.currentPlayerChooseColor(RED);

        Player currentPlayer = game.getCurrentPlayer();
        Assertions.assertThat(currentPlayer.hasName("Titi")).isTrue();
    }

    @Test
    public void when_all_players_played_current_player_loops() {
        game.addPlayer("Toto");

        game.currentPlayerChooseColor(RED);

        Player currentPlayer = game.getCurrentPlayer();
        Assertions.assertThat(currentPlayer.hasName("Toto")).isTrue();
    }
}
