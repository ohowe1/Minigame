package me.ohowe12.hideandseek.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormatterTest  {
    @Test
    public void testFormatCommand() {
        assertEquals(Formatter.formatCommand("This is the command {COMMAND}. {COMMAND} is a good command", "command"), "This is the command command. command is a good command");
    }
    @Test
    public void testWithColor() {
        assertEquals(Formatter.formatCommand("§cThis is the command §1{COMMAND}. {COMMAND} is a good command", "command"), "§cThis is the command §1command. command is a good command");
    }
}