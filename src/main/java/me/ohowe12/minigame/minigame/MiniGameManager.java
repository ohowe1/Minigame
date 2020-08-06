package me.ohowe12.minigame.minigame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import me.ohowe12.minigame.Plugin;
import me.ohowe12.minigame.command.GlobalCommand;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MiniGameManager {

  private final Plugin plugin;
  private final World world;
  private MiniGame currentMiniGame = null;
  private Class<? extends MiniGame> currentMiniGameClass = null;

  public MiniGameManager(Plugin plugin, World world) {
    this.plugin = plugin;
    this.world = world;
  }

  public Class<? extends MiniGame> getCurrentMiniGameClass() {
    return currentMiniGameClass;
  }

  public MiniGame getCurrentMiniGame() {
    return currentMiniGame;
  }

  public boolean isGameRunning() {
    if (currentMiniGame == null) {
      return false;
    }
    return currentMiniGame.running;
  }

  public List<Player> getPlayingPlayers() {
    return world.getPlayers();
  }

  public void setMiniGame(Class<? extends MiniGame> miniGame) {
    if (currentMiniGame != null) {
      if (currentMiniGame.running) {
        return;
      }
    }
    plugin.getPluginLogger().debugLog("Set minigame to " + miniGame.getSimpleName());
    currentMiniGameClass = miniGame;
  }

  public void startGame() {
    if (currentMiniGameClass == null) {
      plugin.getPluginLogger().debugLog("minigame class is null");
      return;
    }
    if (currentMiniGame != null) {
      if (currentMiniGame.running) {
        plugin.getPluginLogger().debugLog("Game already running");
        return;
      }
    }
    try {
      Constructor<? extends MiniGame> constructor =
          currentMiniGameClass.getConstructor(
              ArrayList.class, Plugin.class, World.class, MiniGameManager.class);
      currentMiniGame =
          constructor.newInstance((ArrayList<Player>) getPlayingPlayers(), plugin, world, this);
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
      plugin
          .getPluginLogger()
          .log(
              ChatColor.RED
                  + "Could not initialize the "
                  + currentMiniGameClass.getSimpleName()
                  + " minigame. Please report this to that minigame's developer!");
      return;
    }
    if (currentMiniGame instanceof GlobalCommand) {
      plugin.getMainCommandManager().addCommand((GlobalCommand) currentMiniGame);
    }
    currentMiniGame.start();
  }

  public void stopGame() {
    if (currentMiniGame == null) {
      return;
    }
    if (!currentMiniGame.running) {
      return;
    }
    if (currentMiniGame instanceof GlobalCommand) {
      plugin.getMainCommandManager().removeCommand((GlobalCommand) currentMiniGame);
    }
    currentMiniGame.end();
    currentMiniGame = null;
    System.gc();
  }
}
