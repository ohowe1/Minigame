package me.ohowe.minigame.minigames.capturetheflag;

import java.util.ArrayList;
import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.minigame.MiniGame;
import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.utils.Team;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CaptureTheFlag extends MiniGame {
    private Team redTeam;
    private Team blueTeam;

    public CaptureTheFlag(ArrayList<Player> players,
        Plugin plugin, World world,
        MiniGameManager manager) {
        super(players, plugin, world, manager);
    }

    @Override
    public void start() {
        redTeam = new Team("Red", ChatColor.GOLD + "[RED]", ChatColor.RED);
        blueTeam = new Team("Blue", ChatColor.AQUA + "[BLUE]", ChatColor.BLUE);
        Team.splitPlayers(players, redTeam, blueTeam);
        super.start();
    }

    @Override
    protected void second() {
        if (seconds < 10) {
            // TODO make countdown and game
        }
    }

    @Override
    protected void endGame() {

    }
}
