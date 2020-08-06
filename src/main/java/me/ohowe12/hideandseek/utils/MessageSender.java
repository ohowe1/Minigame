package me.ohowe12.hideandseek.utils;

import org.bukkit.command.CommandSender;

public class MessageSender {

  private static Language language;

  public static void init(Language language) {
    MessageSender.language = language;
  }

  public static void sendPermissionMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("permission"));
  }

  public static void sendConsoleMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("console-message"));
  }

  public static void sendUnknownMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("not-a-command"));
  }

  public static void sendInProgressMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("game-in-progress"));
  }

  public static void sendPlayerMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("invalid-player"));
  }

  public static void sendMinigameSetMessage(CommandSender sender, String minigame) {
    sender.sendMessage(
        Formatter.formatMinigame(language.getColorizedMessage("set-minigame"), minigame));
  }

  public static void sendNotRunningMessage(CommandSender sender) {
    sender.sendMessage(language.getColorizedMessage("not-running"));
  }

  public static void sendOtherMessage(CommandSender sender, String path) {
    sender.sendMessage(language.getColorizedMessage(path));
  }
}
