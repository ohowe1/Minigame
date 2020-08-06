package me.ohowe12.minigame.utils;

import me.ohowe12.minigame.Config;
import me.ohowe12.minigame.Plugin;

public class Logger {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  private final Plugin plugin;
  private final Config config;
  private final String PREFIX;
  private final String FOOTER;

  public Logger(Plugin plugin) {
    this.plugin = plugin;
    config = plugin.getConfigManager();
    PREFIX = ANSI_CYAN;
    FOOTER = ANSI_RESET;
  }

  public void debugLog(String message) {
    if (config.getBoolean("debug")) {
      plugin.getLogger().info(ANSI_GREEN + "[Debug] " + PREFIX + message + FOOTER);
    }
  }

  public void log(String message) {
    plugin.getLogger().info(PREFIX + message + FOOTER);
  }
}
