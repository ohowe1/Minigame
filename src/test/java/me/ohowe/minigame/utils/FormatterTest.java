package me.ohowe.minigame.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FormatterTest {

    @Test
    public void testFormatCommand() {
        assertEquals(
            Formatter.formatCommand(
                "This is the command {COMMAND}. {COMMAND} is a good command", "command"),
            "This is the command command. command is a good command");
    }

    @Test
    public void testWithColor() {
        assertEquals(
            Formatter.formatCommand(
                "§cThis is the command §1{COMMAND}. {COMMAND} is a good command", "command"),
            "§cThis is the command §1command. command is a good command");
    }
}
