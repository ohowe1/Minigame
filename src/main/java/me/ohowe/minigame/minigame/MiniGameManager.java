package me.ohowe.minigame.minigame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.command.GlobalCommand;
import me.ohowe.minigame.command.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MiniGameManager {

    private final Plugin plugin;
    private final World world;
    private final ArrayList<PlayerCommand> specialCommands = new ArrayList<>();
    private MiniGame currentMiniGame = null;
    private Class<? extends MiniGame> currentMiniGameClass = null;

    // TODO make /minigame seeker sync with hide and seek
    // TODO make minigames use translations

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

    public void registerMinigame(Class<? extends MiniGame> minigameClass, String friendlyName) {
        plugin.getMainCommandManager()
            .addCommand(new MiniGameCommand(this, minigameClass, friendlyName));
    }

    public void registerSpecialCommand(PlayerCommand command) {
        specialCommands.add(command);
        plugin.getMainCommandManager().addCommand(command);
    }

    public PlayerCommand getSpecialCommand(String commandId) {
        Optional<PlayerCommand> optionalPlayerCommand = specialCommands.stream().filter(command -> command.getUniqueId().equalsIgnoreCase(commandId)).findFirst();
        return optionalPlayerCommand.orElse(null);
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
                constructor
                    .newInstance(getPlayingPlayers(), plugin, world, this);
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
