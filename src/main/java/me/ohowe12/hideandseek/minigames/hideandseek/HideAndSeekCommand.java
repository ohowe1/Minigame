package me.ohowe12.hideandseek.minigames.hideandseek;

import me.ohowe12.hideandseek.Plugin;
import me.ohowe12.hideandseek.command.PlayerCommand;
import me.ohowe12.hideandseek.minigames.MiniGameManager;
import me.ohowe12.hideandseek.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class HideAndSeekCommand implements PlayerCommand {

    private final MiniGameManager manager;
    private final Plugin plugin;

    public HideAndSeekCommand(MiniGameManager manager, Plugin plugin) {
        this.manager = manager;
        this.plugin = plugin;
    }

    public HideAndSeekCommand(Plugin plugin) {
        this.plugin = plugin;
        this.manager = plugin.getMiniGameManager();
    }

    @Override
    public String getName() {
        return "hideandseek";
    }

    @Override
    public String getUniqueId() {
        return "hide-and-seek";
    }

    @Override
    public String getPermission() {
        return "minigame-start";
    }

    @Override
    public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
        manager.setMiniGame(HideAndSeek.class);
        MessageSender.sendMinigameSetMessage(player, "Hide and Seek");
    }
}
