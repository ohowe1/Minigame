package me.ohowe12.hideandseek.minigames;

import me.ohowe12.hideandseek.Plugin;
import me.ohowe12.hideandseek.command.PlayerCommand;
import me.ohowe12.hideandseek.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartGameCommand implements PlayerCommand {
    private final MiniGameManager manager;

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getUniqueId() {
        return "start";
    }

    @Override
    public String getPermission() {
        return "minigame-start";
    }

    public StartGameCommand(Plugin plugin) {
        this.manager = plugin.getMiniGameManager();
    }

    public StartGameCommand(MiniGameManager manager) {
        this.manager = manager;
    }

    @Override
    public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if (manager.getCurrentMiniGameClass() == null) {
            MessageSender.sendOtherMessage(player, "not-selected");
            return;
        }
        if (manager.isGameRunning()) {
            MessageSender.sendInProgressMessage(player);
            return;
        }
        manager.startGame();
    }
}
