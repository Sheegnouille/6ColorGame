package game;

import game.board.Board;
import game.board.Cell;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static game.board.Position.PositionBuilder.aPosition;
import static game.color.Color.BLUE;
import static game.color.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GameTest {
    @Mock
    private Board board;
    private Game game;
    private Cell startingCell;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        game = new Game(board);
        startingCell = new Cell(aPosition().withColumn(0).withRow(0).build(), RED);
        when(board.provideFreeStartingCell()).thenReturn(startingCell);
        when(board.determineBoardSize()).thenReturn(100);
        when(board.determineTerritorySizeFromCell(startingCell)).thenReturn(0);
    }

    @Test
    public void should_add_starting_cell_to_player_when_add_new_player() {
        game.addPlayer("Toto");

        assertThat(game.getCurrentPlayerStartingCell()).isEqualTo(startingCell);
        verify(board).provideFreeStartingCell();
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

        assertThat(game.getCurrentPlayer().hasName("Toto")).isTrue();
    }

    @Test
    public void current_player_is_first_player_added() {
        game.addPlayer("Toto");
        game.addPlayer("Titi");

        assertThat(game.getCurrentPlayer().hasName("Toto")).isTrue();
    }

    @Test
    public void current_player_changes_when_player_chooses_a_color() {
        game.addPlayer("Toto");
        game.addPlayer("Titi");

        game.currentPlayerChooseColor(BLUE);

        Player currentPlayer = game.getCurrentPlayer();
        assertThat(currentPlayer.hasName("Titi")).isTrue();
    }

    @Test
    public void when_all_players_played_current_player_loops() {
        game.addPlayer("Toto");

        game.currentPlayerChooseColor(RED);

        Player currentPlayer = game.getCurrentPlayer();
        assertThat(currentPlayer.hasName("Toto")).isTrue();
    }

    @Test
    public void is_finished_when_one_player_has_dominant_territory() {
        game.addPlayer("toto");
        when(board.determineTerritorySizeFromCell(startingCell)).thenReturn(100);

        boolean finished = game.isFinished();

        assertThat(finished).isTrue();
        verify(board).determineBoardSize();
        verify(board).determineTerritorySizeFromCell(startingCell);
    }

    @Test
    public void territory_not_dominant_should_not_finish_game() {
        game.addPlayer("toto");

        boolean finished = game.isFinished();

        assertThat(finished).isFalse();
        verify(board).determineBoardSize();
        verify(board).determineTerritorySizeFromCell(startingCell);
    }

    @Test
    public void two_players_should_have_different_starting_cells() {
        game.addPlayer("Toto");
        when(board.provideFreeStartingCell()).thenReturn(new Cell(aPosition().withColumn(1).withRow(0).build(), RED));
        game.addPlayer("Tata");

        Cell startingCellPlayer1 = game.getCurrentPlayerStartingCell();
        game.currentPlayerChooseColor(BLUE);
        Cell startingCellPlayer2 = game.getCurrentPlayerStartingCell();

        assertThat(startingCellPlayer1).isNotEqualTo(startingCellPlayer2);
        verify(board, times(2)).provideFreeStartingCell();
    }

    @Test
    public void a_player_cannot_choose_the_color_of_the_other_player() {
        game.addPlayer("Toto");
        game.addPlayer("Tata");
        game.currentPlayerChooseColor(RED);
        boolean canPlayerChooseColor = game.currentPlayerChooseColor(RED);
        assertThat(canPlayerChooseColor).isFalse();
    }

    @Test
    public void name() {
        when(board.determineBoardSize()).thenReturn(64);
        when(board.determineTerritorySizeFromCell(Mockito.any())).thenReturn(16);
        game.addPlayer("Toto");
        game.addPlayer("Tata");
        game.addPlayer("Titi");
        game.addPlayer("Tutu");

        boolean finished = game.isFinished();

        assertThat(finished).isTrue();
        verify(board).determineBoardSize();
        verify(board).determineTerritorySizeFromCell(Mockito.any());
    }
}
