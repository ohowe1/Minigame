package me.ohowe12.hideandseek;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  private final Configuration defaults;
  private final FileConfiguration config;

  public Config(Plugin plugin) {
    this(plugin.getConfig());
  }

  public Config(FileConfiguration config) {
    this.config = config;
    this.defaults = config.getDefaults();
  }

  public String getString(String path) {
    return (String) getValue(path);
  }

  public boolean getBoolean(String path) {
    return (boolean) getValue(path);
  }

  public int getInt(String path) {
    return (int) getValue(path);
  }

  private Object getValue(String path) {
    Object value = config.get(path);
    if (value == null) {
      value = defaults.get(path);
    }
    return value;
  }
}
