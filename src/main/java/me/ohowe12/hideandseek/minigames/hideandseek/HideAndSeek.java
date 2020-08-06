package me.ohowe12.hideandseek.minigames.hideandseek;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.ohowe12.hideandseek.Plugin;
import me.ohowe12.hideandseek.command.PlayerCommand;
import me.ohowe12.hideandseek.minigame.MiniGame;
import me.ohowe12.hideandseek.minigame.MiniGameManager;
import me.ohowe12.hideandseek.utils.MessageSender;
import me.ohowe12.hideandseek.utils.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HideAndSeek extends MiniGame implements PlayerCommand {

  private final PotionEffect INVIS =
      new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 255, false, false, true);
  private Player seeker;
  private Team seekers;
  private int seconds;
  private boolean stillInStartPhase = true;
  private Team hiders;

  public HideAndSeek(
      ArrayList<Player> players, Plugin plugin, World world, MiniGameManager manager) {
    super(players, plugin, world, manager);
  }

  @Override
  public String getName() {
    return "seeker";
  }

  @Override
  public String getUniqueId() {
    return "seekername";
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
  public String getPermission() {
    return "hideseek-seeker";
  }

  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
    plugin.getPluginLogger().debugLog("Tab Completing");
    List<String> done = new ArrayList<>();
    for (Player p : players) {
      done.add(p.getName());
    }
    return done;
  }

  @Override
  public void start() {
    seekers = new Team("Seekers", ChatColor.GOLD + "[SEEKER] ");
    seekers.setColor(ChatColor.RED);
    hiders = new Team("Hiders", ChatColor.AQUA + "[HIDER] ");
    hiders.setInvis(true);
    hiders.setOption(
        org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY,
        org.bukkit.scoreboard.Team.OptionStatus.FOR_OTHER_TEAMS);

    Random random = new Random();
    seeker = players.get(random.nextInt(players.size()));
    seekers.add(seeker);

    players.forEach(
        player -> {
          if (!player.equals(seeker)) {
            plugin.getPluginLogger().debugLog("Adding " + player.getName() + " to hider team");
            hiders.add(player);
          }
        });

    sendMessageToAll(seeker.getName() + " is the seeker!");
    super.start();
    border.setSize(1000);
    hiders.forEach(player -> player.addPotionEffect(INVIS));
    players.forEach(player -> player.teleport(center));
  }

  @Override
  public void end() {
    for (Player player : players) {
      for (PotionEffect potionEffect : player.getActivePotionEffects()) {
        player.removePotionEffect(potionEffect.getType());
      }
    }
    hiders.unregister();
    seekers.unregister();
    running = false;
  }

  @Override
  public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
    if (args.length == 0) {
      MessageSender.sendPlayerMessage(player);
      return;
    }
    if (!running) {
      MessageSender.sendNotRunningMessage(player);
    }
    if (!stillInStartPhase) {
      MessageSender.sendInProgressMessage(player);
      return;
    }
    Player target = Bukkit.getPlayerExact(args[0]);
    if (target == null || !players.contains(target)) {
      MessageSender.sendPlayerMessage(player);
      return;
    }
    seeker.addPotionEffect(INVIS);
    seekers.remove(seeker);
    hiders.add(seeker);
    seeker = target;
    seekers.add(seeker);
    seeker.removePotionEffect(PotionEffectType.INVISIBILITY);
    sendMessageToAll(seeker.getName() + " is the seeker!");
  }

  @EventHandler
  public void onCombat(EntityDamageByEntityEvent e) {
    if (!running) {
      return;
    }
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    Player damager = (Player) e.getDamager();
    Player player = (Player) e.getEntity();
    if (!stillInStartPhase) {
      if ((seekers.contains(damager) || damager.equals(seeker)) && hiders.contains(player)) {
        plugin.getPluginLogger().debugLog("Player found");
        hiders.remove(player);
        seekers.add(player);
        if (hiders.size() >= 1) {
          player.getInventory().addItem(new ItemStack(Material.COMPASS));
          player.setCompassTarget(seeker.getLocation());
          sendToAllPlayers("Player has been found!", player.getDisplayName() + " has been found!");
        } else {
          plugin.getPluginLogger().debugLog("Ending game");
          sendToAllPlayers(ChatColor.RED + "Game Over!");
          manager.stopGame();
        }
        e.setCancelled(false);
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    e.setCancelled(stillInStartPhase && e.getPlayer().equals(seeker) && running);
  }

  private void startGame() {
    stillInStartPhase = false;
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
      seconds++;
      return;
    }
    if (seconds == 45) {
      startGame();
    }
    if (seconds == 120) {
      seeker.getInventory().addItem(new ItemStack(Material.COMPASS));
      sendToAllPlayers("The seeker now has the compass!");
    }
    if (seconds % 300 == 0) {
      sendToAllPlayers(
          ChatColor.RED + "Shrinking world border in half!",
          "The center is " + center.getX() + " " + center.getZ());
      border.setSize(border.getSize() / 2, 60);
      playToALl(Sound.ITEM_TRIDENT_RETURN);
    }
    seconds++;
  }
}
