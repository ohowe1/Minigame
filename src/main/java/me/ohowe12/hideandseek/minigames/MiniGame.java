package me.ohowe12.hideandseek.minigames;

import me.ohowe12.hideandseek.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public abstract class MiniGame implements Listener {
    protected boolean running;
    protected ArrayList<Player> players;
    protected final Plugin plugin;
    protected final World world;
    protected final MiniGameManager manager;
    protected final Location center;

    public MiniGame(ArrayList<Player> players, Plugin plugin, World world, MiniGameManager manager) {
        this.manager = manager;
        this.players = players;
        this.plugin = plugin;
        this.world = world;
        this.center = world.getSpawnLocation();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
            p.sendTitle(title, subtitle, 5, time-10, 5);
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
