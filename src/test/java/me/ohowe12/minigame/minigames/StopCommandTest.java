package me.ohowe12.minigame.minigames;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.ohowe12.minigame.minigame.MiniGameManager;
import me.ohowe12.minigame.minigame.StopCommand;
import me.ohowe12.minigame.minigames.hideandseek.HideAndSeek;
import me.ohowe12.minigame.utils.Language;
import me.ohowe12.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

public class StopCommandTest {

    private StopCommand stopGameCommand;
    final MiniGameManager miniGameManager = mock(MiniGameManager.class);

    @Test
    public void testWhileNull() {
        callExecute();

        verify(miniGameManager, never()).stopGame();

        reset();
    }

    @Test
    public void testWhileNotRunning() {
        doReturn(mock(HideAndSeek.class)).when(miniGameManager).getCurrentMiniGame();
        when(miniGameManager.isGameRunning()).thenReturn(false);

        callExecute();

        verify(miniGameManager, never()).stopGame();
        reset();
    }

    @Test
    public void testWhileGood() {
        doReturn(mock(HideAndSeek.class)).when(miniGameManager).getCurrentMiniGame();
        when(miniGameManager.isGameRunning()).thenReturn(true);

        callExecute();
        verify(miniGameManager).stopGame();
        reset();
    }

    private void callExecute() {
        stopGameCommand.executePlayerCommand(mock(Player.class), new String[0]);
    }


    @Before
    public void setUp() {
        MessageSender.init(mock(Language.class));
        reset();
        stopGameCommand = new StopCommand(miniGameManager);
    }

    private void reset() {
        when(miniGameManager.isGameRunning()).thenReturn(false);
        when(miniGameManager.getCurrentMiniGameClass()).thenReturn(null);
    }
}