package me.ohowe.minigame.minigame;

import java.util.ArrayList;
import me.ohowe.minigame.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MiniGame implements Listener {

    protected final Plugin plugin;
    protected final World world;
    protected final WorldBorder border;
    protected final MiniGameManager manager;
    protected final Location center;
    protected final ArrayList<Player> players;
    protected boolean running;
    protected int seconds = 0;

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

    public MiniGame(ArrayList<Player> players, Plugin plugin, World world,
        MiniGameManager manager) {
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
                seconds++;
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

    public final void end() {
        running = false;
        endGame();
    }

    protected abstract void second();

    @EventHandler
    public final void placeEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        e.setCancelled(blockPlace(player));
    }

    protected boolean blockPlace(Player player) {
        return false;
    }

    @EventHandler
    public final void breakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        e.setCancelled(blockBreak(player));
    }

    protected boolean blockBreak(Player player) {
        return player.getGameMode() != GameMode.CREATIVE;
    }


    @EventHandler
    public final void onMove(PlayerMoveEvent e) {
        if (!running) {
            return;
        }
        e.setCancelled(playerMoveEvent(e.getPlayer()));
    }

    protected boolean playerMoveEvent(Player player) {
        return false;
    }


    @EventHandler
    public final void damageEvent(EntityDamageByEntityEvent e) {
        if (!running) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        e.setCancelled(playerPVPEvent(((Player) e.getDamager()), ((Player) e.getEntity())));
    }

    protected boolean playerPVPEvent(Player damager, Player damaged) {
        return true;
    }

    protected abstract void endGame();

}
