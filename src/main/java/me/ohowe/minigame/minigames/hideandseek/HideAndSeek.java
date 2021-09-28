package me.ohowe.minigame.minigames.hideandseek;

import java.util.ArrayList;
import java.util.Random;
import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.minigame.MiniGame;
import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.utils.Team;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HideAndSeek extends MiniGame {

    private final PotionEffect INVIS =
        new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 255, false, false, true);
    private final SeekerCommand seekerCommand;
    private Player seeker;
    private Team seekers;
    private boolean stillInStartPhase = true;
    private Team hiders;

    public HideAndSeek(
        ArrayList<Player> players, Plugin plugin, World world, MiniGameManager manager) {
        super(players, plugin, world, manager);
        seekerCommand = (SeekerCommand) manager.getSpecialCommand("seeker-set");
    }


    private Location getClosestPlayer() {
        Location closet = new Location(world, 100000, 1000000, 100000);
        for (Player p : hiders) {
            if (p.getLocation().distanceSquared(seeker.getLocation())
                < closet.distanceSquared(seeker.getLocation())) {
                closet = p.getLocation();
            }
        }
        return closet;
    }

    @Override
    public void start() {
        seekers = new Team("Seekers", ChatColor.GOLD + "[SEEKER]", ChatColor.RED);
        hiders = new Team("Hiders", ChatColor.AQUA + "[HIDER]", ChatColor.BLUE);
        hiders.setInvis(true);
        hiders.setOption(
            org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY,
            org.bukkit.scoreboard.Team.OptionStatus.FOR_OTHER_TEAMS);

        seeker = seekerCommand.getSeeker();
        if (seeker == null) {
            Random random = new Random();
            seeker = players.get(random.nextInt(players.size()));
        }
        seekers.add(seeker);

        players.forEach(
            player -> {
                if (!player.equals(seeker)) {
                    plugin.getPluginLogger()
                        .debugLog("Adding " + player.getName() + " to hider team");
                    hiders.add(player);
                }
            });

        sendMessageToAll(seeker.getName() + " is the seeker!");
        super.start();
        border.setSize(plugin.getConfigManager().getInt("world-border-width"));
        hiders.forEach(player -> player.addPotionEffect(INVIS));
        hiders.forEach(
            player -> player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 2)));
        players.forEach(player -> player.teleport(center));
    }

    public void setSeeker(@NotNull Player target) {
        if (!running) {
            return;
        }
        if (!stillInStartPhase) {
            return;
        }
        if (players.contains(target)) {
            return;
        }
        seeker.addPotionEffect(INVIS);
        seeker.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 2));
        seekers.remove(seeker);
        hiders.add(seeker);
        seeker = target;
        seekers.add(seeker);
        seeker.getInventory().remove(Material.ENDER_PEARL);
        seeker.removePotionEffect(PotionEffectType.INVISIBILITY);
        sendMessageToAll(seeker.getName() + " is the seeker!");
    }

    public boolean canChangeSeeker() {
        return stillInStartPhase;
    }

    @Override
    protected boolean playerPVPEvent(Player damager, Player damaged) {
        if (!stillInStartPhase) {
            if ((seekers.contains(damager) || damager.equals(seeker)) && hiders.contains(damaged)) {
                hiders.remove(damaged);
                seekers.add(damaged);
                if (hiders.size() >= 1) {
                    damaged.getInventory().addItem(new ItemStack(Material.COMPASS));
                    damaged.setCompassTarget(seeker.getLocation());
                    sendToAllPlayers("Player has been found!",
                        damaged.getDisplayName() + " has been found!");
                } else {
                    plugin.getPluginLogger().debugLog("Ending game");
                    sendToAllPlayers(ChatColor.RED + "Game Over!");
                    manager.stopGame();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    protected void endGame() {
        for (Player player : players) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
        hiders.unregister();
        seekers.unregister();
    }

    @Override
    protected boolean playerMoveEvent(Player player) {
        return (stillInStartPhase && player.equals(seeker));
    }

    private void startGame() {
        stillInStartPhase = false;
        seeker.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
        hiders.remove(seeker);
        hiders.forEach(player -> player.removePotionEffect(PotionEffectType.INVISIBILITY));
        playToALl(Sound.ENTITY_ENDER_DRAGON_AMBIENT);
        sendToAllPlayers(ChatColor.RED + "Run!", "The seeker has been released");
    }

    private void setCompasses() {
        for (Player player : seekers) {
            player.setCompassTarget(seeker.getLocation());
        }
        seeker.setCompassTarget(getClosestPlayer());
    }

    @Override
    protected void second() {
        setCompasses();

        if (seconds < 45) {
            int newSec = 45 - seconds;
            String secondsString = String.valueOf(newSec);
            if (45 - seconds < 10) {
                if (newSec % 2 == 0) {
                    secondsString = ChatColor.RED + secondsString;
                } else {
                    secondsString = ChatColor.GOLD + secondsString;
                }
                playToALl(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            sendToAllPlayers(secondsString, "Seeker released in " + newSec, 20);
            return;
        }
        if (seconds == 45) {
            startGame();
        }
        if (seconds == 120) {
            seeker.getInventory().addItem(new ItemStack(Material.COMPASS));
            sendToAllPlayers(ChatColor.GOLD + "Beware!", "The seeker now has a compass");
        }
        if (seconds % 300 == 0) {
            sendToAllPlayers(
                ChatColor.RED + "Stay safe!",
                "The border is shrinking in half!");
            border.setSize(border.getSize() / 2, 60);
            playToALl(Sound.ITEM_TRIDENT_RETURN);
        }
    }
}
