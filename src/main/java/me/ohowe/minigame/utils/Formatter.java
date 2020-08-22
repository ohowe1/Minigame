package me.ohowe.minigame.utils;

public class Formatter {

    public static String formatCommand(String string, String commandToPutIn) {
        return string.replace("{COMMAND}", commandToPutIn);
    }

    public static String formatMinigame(String string, String minigameToPutIn) {
        return string.replace("{MINIGAME}", minigameToPutIn);
    }

    public static String formatPlayer(String string, String playerName) {
        return string.replace("{PLAYER}", playerName);
    }
}
