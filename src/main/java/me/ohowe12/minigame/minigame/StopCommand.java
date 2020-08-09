package me.ohowe12.minigame.minigame;

import me.ohowe12.minigame.Plugin;
import me.ohowe12.minigame.command.PlayerCommand;
import me.ohowe12.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopCommand implements PlayerCommand {

    private final MiniGameManager manager;

    public StopCommand(Plugin plugin) {
        this.manager = plugin.getMiniGameManager();
    }

    public StopCommand(MiniGameManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getUniqueId() {
        return "stop";
    }

    @Override
    public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if (manager.getCurrentMiniGame() == null) {
            MessageSender.sendNotRunningMessage(player);
            return;
        }
        if (!manager.isGameRunning()) {
            MessageSender.sendNotRunningMessage(player);
            return;
        }
        manager.stopGame();
    }
}
