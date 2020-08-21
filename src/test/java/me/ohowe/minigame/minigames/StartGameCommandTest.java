package me.ohowe.minigame.minigames;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.minigame.StartGameCommand;
import me.ohowe.minigame.minigames.hideandseek.HideAndSeek;
import me.ohowe.minigame.utils.Language;
import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

public class StartGameCommandTest {

    final MiniGameManager miniGameManager = mock(MiniGameManager.class);
    private StartGameCommand startGameCommand;

    @Test
    public void testWhileNull() {
        callExecute();

        verify(miniGameManager, never()).startGame();

        reset();
    }

    @Test
    public void testWhileRunning() {
        doReturn(HideAndSeek.class).when(miniGameManager).getCurrentMiniGameClass();
        when(miniGameManager.isGameRunning()).thenReturn(true);

        callExecute();

        verify(miniGameManager, never()).startGame();
        reset();
    }

    @Test
    public void testWhileGood() {
        doReturn(HideAndSeek.class).when(miniGameManager).getCurrentMiniGameClass();
        when(miniGameManager.isGameRunning()).thenReturn(false);

        callExecute();
        verify(miniGameManager).startGame();
        reset();
    }

    private void callExecute() {
        startGameCommand.executePlayerCommand(mock(Player.class), new String[0]);
    }

    @Before
    public void setUp() {
        MessageSender.init(mock(Language.class));
        reset();
        startGameCommand = new StartGameCommand(miniGameManager);
    }

    private void reset() {
        when(miniGameManager.isGameRunning()).thenReturn(false);
        when(miniGameManager.getCurrentMiniGameClass()).thenReturn(null);
    }
}
