package me.ohowe12.minigame.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.command.CommandSender;
import org.junit.Before;
import org.junit.Test;

public class MessageSenderTest {

    @Test
    public void testSendPermissionMessage() {
        CommandSender sender = mock(CommandSender.class);

        MessageSender.sendPermissionMessage(sender);
        verify(sender).sendMessage("§cYou do not have permission to do that");
    }

    @Test
    public void testSendOtherMessage() {
        CommandSender sender = mock(CommandSender.class);

        MessageSender.sendOtherMessage(sender, "permission");
        verify(sender).sendMessage("§cYou do not have permission to do that");
    }

    @Before
    public void setUp() {
        Language language = mock(Language.class);
        when(language.getColorizedMessage("permission"))
            .thenReturn("§cYou do not have permission to do that");
        MessageSender.init(language);
    }
}