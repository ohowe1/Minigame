package me.ohowe12.hideandseek.utils;

public class Formatter {
    public static String formatCommand(String string, String commandToPutIn) {
        return string.replace("{COMMAND}", commandToPutIn);
    }
    public static String formatMinigame(String string, String minigameToPutIn) {
        return string.replace("{MINIGAME}", minigameToPutIn);
    }
}
