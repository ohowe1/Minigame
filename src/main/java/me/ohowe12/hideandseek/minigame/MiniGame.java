package me.ohowe12.hideandseek.minigame;

import java.util.ArrayList;
import me.ohowe12.hideandseek.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MiniGame implements Listener {

  protected final Plugin plugin;
  protected final World world;
  protected final WorldBorder border;
  protected final MiniGameManager manager;
  protected final Location center;
  protected boolean running;
  protected final ArrayList<Player> players;

  public MiniGame(
      ArrayList<Player> players,
      Plugin plugin,
      World world,
      MiniGameManager manager,
      int x,
      int y,
      int z) {
    this.manager = manager;
    this.players = players;
    this.plugin = plugin;
    this.world = world;
    this.border = world.getWorldBorder();
    border.setCenter(x, z);
    this.center = new Location(world, x, y, z);
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public MiniGame(ArrayList<Player> players, Plugin plugin, World world, MiniGameManager manager) {
    this(
        players,
        plugin,
        world,
        manager,
        plugin.getConfigManager().getInt("world-border-x"),
        plugin.getConfigManager().getInt("world-border-z"),
        plugin.getConfigManager().getInt("center-y"));
  }

  public void start() {
    running = true;
    new BukkitRunnable() {
      @Override
      public void run() {
        if (!running) {
          plugin.getPluginLogger().debugLog("Stopped Task!");
          this.cancel();
        }
        second();
      }
    }.runTaskTimer(plugin, 0L, 20L);
  }

  protected final void sendToAllPlayers(String title, String subtitle, int time) {
    for (Player p : players) {
      p.sendTitle(title, subtitle, 5, time - 10, 5);
    }
  }

  protected final void sendToAllPlayers(String title, String subtitle) {
    sendToAllPlayers(title, subtitle, 100);
  }

  protected final void sendToAllPlayers(String title) {
    sendToAllPlayers(title, "");
  }

  protected final void sendMessageToAll(String message) {
    for (Player p : players) {
      p.sendMessage(message);
    }
  }

  protected final void playToALl(Sound sound) {
    for (Player p : players) {
      p.playSound(p.getLocation(), sound, 1, 1);
    }
  }

  public void end() {
    running = false;
  }

  protected abstract void second();

  @EventHandler
  public void breakEvent(BlockBreakEvent e) {
    Player player = e.getPlayer();
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    e.setCancelled(true);
  }
}
