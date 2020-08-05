package me.ohowe12.hideandseek.minigames;

import me.ohowe12.hideandseek.minigames.hideandseek.HideAndSeek;
import me.ohowe12.hideandseek.utils.Language;
import me.ohowe12.hideandseek.utils.MessageSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class StartGameCommandTest {
    private StartGameCommand startGameCommand;
    MiniGameManager miniGameManager = mock(MiniGameManager.class);

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