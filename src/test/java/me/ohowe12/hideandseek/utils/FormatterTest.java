package me.ohowe12.hideandseek.utils;

import junit.framework.TestCase;

public class FormatterTest extends TestCase {
    public void testFormatCommand() {
        assertEquals(Formatter.formatCommand("This is the command {COMMAND}. {COMMAND} is a good command", "command"), "This is the command command. command is a good command");
    }
    public void testWithColor() {
        assertEquals(Formatter.formatCommand("§cThis is the command §1{COMMAND}. {COMMAND} is a good command", "command"), "§cThis is the command §1command. command is a good command");
    }
}