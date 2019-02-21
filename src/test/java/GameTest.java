import game.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameTest {
@Mock
private Board board;
    private Game game;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        game = new Game(board);
    }

    @Test
    public void should_add_starting_cell_to_player_when_add_new_player() {
        Cell startingCell = new Cell(0,0, Color.RED);
        when(board.getStartingCell()).thenReturn(startingCell);
        Player newPlayer = new Player("Toto");

        game.add(newPlayer);

        Assertions.assertThat(game.getStartingCell(newPlayer)).isEqualTo(startingCell);
        verify(board).getStartingCell();
    }
}
