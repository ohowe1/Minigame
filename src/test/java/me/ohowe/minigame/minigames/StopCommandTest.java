package me.ohowe.minigame.minigames;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.minigame.StopCommand;
import me.ohowe.minigame.minigames.hideandseek.HideAndSeek;
import me.ohowe.minigame.utils.Language;
import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

public class StopCommandTest {

    final MiniGameManager miniGameManager = mock(MiniGameManager.class);
    private StopCommand stopGameCommand;

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
