package me.ohowe.minigame.minigame;

import me.ohowe.minigame.command.PlayerCommand;
import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MiniGameCommand implements PlayerCommand {

    protected final MiniGameManager manager;
    protected final String friendlyName;
    protected final Class<? extends MiniGame> minigameClass;

    protected MiniGameCommand(MiniGameManager manager, Class<? extends MiniGame> minigameClass,
        String friendlyName) {
        this.manager = manager;
        this.minigameClass = minigameClass;
        this.friendlyName = friendlyName;
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

    protected Class<? extends MiniGame> getMiniGameClass() {
        return minigameClass;
    }

    protected String getFriendlyName() {
        return friendlyName;
    }

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
