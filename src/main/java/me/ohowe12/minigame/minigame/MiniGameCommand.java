package me.ohowe12.minigame.minigame;

import me.ohowe12.minigame.command.PlayerCommand;
import me.ohowe12.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class MiniGameCommand implements PlayerCommand {

    protected final MiniGameManager manager;

    protected MiniGameCommand(MiniGameManager manager) {
        this.manager = manager;
    }

    @Override
    public final String getUniqueId() {
        return "minigame";
    }

    @Override
    public String[] getReplacement() {
        return new String[]{"{MINIGAME}", getFriendlyName()};
    }

    @Override
    public String getName() {
        return getFriendlyName().replace(" ", "");
    }

    protected abstract Class<? extends MiniGame> getMiniGameClass();

    protected abstract String getFriendlyName();

    @Override
    public String getPermission() {
        return "minigame-set";
    }

    @Override
    public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
        manager.setMiniGame(getMiniGameClass());
        MessageSender.sendMinigameSetMessage(player, getFriendlyName());
    }
}
