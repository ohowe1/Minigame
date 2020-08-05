package me.ohowe12.hideandseek.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Team implements Iterable<Player> {
    private final List<Player> players = new ArrayList<>();
    private final String name;
    private org.bukkit.scoreboard.Team bukkitTeam;

    public void setPrefix(String prefix) {
        bukkitTeam.setPrefix(prefix);
    }

    public void setColor(ChatColor color) {
        if (color != null) {
            bukkitTeam.setColor(color);
        }
    }

    public void setInvis(boolean canSee) {
        bukkitTeam.setCanSeeFriendlyInvisibles(canSee);
    }

    public Team(String name) {
        this.name = name;
        bukkitTeam = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeam(name);
        if (bukkitTeam == null) {
            bukkitTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
        }
    }

    public Team(String name, String prefix) {
        this(name);
        setPrefix(prefix);
    }

    public void add(Player player) {
        bukkitTeam.addEntry(player.getName());
        players.add(player);
    }

    public void setOption(org.bukkit.scoreboard.Team.Option option, org.bukkit.scoreboard.Team.OptionStatus optionStatus) {
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
