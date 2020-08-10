package me.ohowe12.minigame.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Team implements Iterable<Player> {

    private final List<Player> players = new ArrayList<>();
    private org.bukkit.scoreboard.Team bukkitTeam;


    public Team(String name) {
        bukkitTeam =
            Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeam(name);
        if (bukkitTeam == null) {
            bukkitTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
        }
    }

    public Team(String name, String prefix) {
        this(name);
        setPrefix(prefix);
    }

    public Team(String name, String prefix, ChatColor color) {
        this(name, prefix);
        setColor(color);
    }

    /**
     * @param players The list of players to be split into the teams.
     * @param team1   The first team
     * @param team2   The second team
     */
    public static void splitPlayers(ArrayList<Player> players, Team team1, Team team2) {
        ArrayList<Player> copy = (ArrayList<Player>) players.clone();
        Collections.shuffle(copy);
        team1.addAll(copy.subList(0, copy.size() / 2));
        team2.addAll(copy.subList(copy.size() / 2 + 1, copy.size()));
    }

    private static @Nullable LeatherArmorMeta getLeatherArmorMeta(ItemStack leatherArmor) {
        if (leatherArmor.getItemMeta() instanceof LeatherArmorMeta) {
            return (LeatherArmorMeta) leatherArmor.getItemMeta();
        }
        return null;
    }

    public void setPrefix(String prefix) {
        bukkitTeam.setPrefix(prefix + " ");
    }

    public void setInvis(boolean canSee) {
        bukkitTeam.setCanSeeFriendlyInvisibles(canSee);
    }

    public void add(Player player) {
        bukkitTeam.addEntry(player.getName());
        players.add(player);
    }

    public void addAll(List<Player> players) {
        for (Player p : players) {
            add(p);
        }
    }

    public void sendAllTo(Location location) {
        for (Player p : players) {
            p.teleport(location);
        }
    }

    public void setSpawnLocation(Location location) {
        for (Player p : players) {
            p.setBedSpawnLocation(location, true);
        }
    }

    public void giveToAll(ItemStack itemStack) {
        for (Player p : players) {
            p.getInventory().addItem(itemStack);
        }
    }

    public void equipArmor(ItemStack helmet, ItemStack chestPlate, ItemStack leggings,
        ItemStack boots) {
        for (Player p : players) {
            p.getInventory().setHelmet(helmet);
            p.getInventory().setChestplate(chestPlate);
            p.getInventory().setLeggings(leggings);
            p.getInventory().setBoots(boots);
        }
    }

    public void equipArmor() {
        equipArmor(leatherify(new ItemStack(Material.LEATHER_HELMET)),
            leatherify(new ItemStack(Material.LEATHER_CHESTPLATE)),
            leatherify(new ItemStack(Material.LEATHER_LEGGINGS)),
            leatherify(new ItemStack(Material.LEATHER_BOOTS)));
    }

    private ItemStack leatherify(ItemStack leather) {
        LeatherArmorMeta leatherArmorMeta = getLeatherArmorMeta(leather);
        assert leatherArmorMeta != null;
        leatherArmorMeta.setColor(getColor());
        leather.setItemMeta(leatherArmorMeta);
        return leather;
    }

    private Color getColor() {
        switch (bukkitTeam.getColor()) {
            case RED:
            case DARK_RED:
                return Color.RED;
            case DARK_AQUA:
            case AQUA:
                return Color.AQUA;
            case GOLD:
                return Color.ORANGE;

            case BLUE:
            case DARK_BLUE:
                return Color.BLUE;
            case GRAY:
            case DARK_GRAY:
                return Color.GRAY;
            case BLACK:
                return Color.BLACK;
            case GREEN:
            case DARK_GREEN:
                return Color.GREEN;

            case YELLOW:
                return Color.YELLOW;
            case DARK_PURPLE:
            case LIGHT_PURPLE:
                return Color.PURPLE;
            default:
                return Color.WHITE;

        }
    }

    public void setColor(ChatColor color) {
        if (color != null) {
            bukkitTeam.setColor(color);
        }
    }

    public void setOption(
        org.bukkit.scoreboard.Team.Option option,
        org.bukkit.scoreboard.Team.OptionStatus optionStatus) {
        bukkitTeam.setOption(option, optionStatus);
    }

    public void unregister() {
        players.clear();
        bukkitTeam.unregister();
    }

    public boolean contains(Player player) {
        return players.contains(player);
    }

    public void remove(Player player) {
        players.remove(player);
        bukkitTeam.removeEntry(player.getName());
    }

    public int size() {
        return players.size();
    }

    @NotNull
    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        players.forEach(action);
    }
}
