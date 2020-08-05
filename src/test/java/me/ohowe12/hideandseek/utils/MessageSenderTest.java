package me.ohowe12.hideandseek.utils;

import junit.framework.TestCase;
import org.bukkit.command.CommandSender;

import static org.mockito.Mockito.*;

public class MessageSenderTest extends TestCase {

    public void testSendPermissionMessage() {
        CommandSender sender = mock(CommandSender.class);

        MessageSender.sendPermissionMessage(sender);
        verify(sender).sendMessage("§cYou do not have permission to do that");
    }

    public void testSendOtherMessage() {
        CommandSender sender = mock(CommandSender.class);

        MessageSender.sendOtherMessage(sender, "permission");
        verify(sender).sendMessage("§cYou do not have permission to do that");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Language language = mock(Language.class);
        when(language.getColorizedMessage("permission")).thenReturn("§cYou do not have permission to do that");
        MessageSender.init(language);
    }
}