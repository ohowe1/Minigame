package me.ohowe.minigame.minigames.hideandseek;

import java.util.List;
import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.command.PlayerCommand;
import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SeekerCommand implements PlayerCommand {

    private final MiniGameManager manager;
    private final Plugin plugin;
    private Player seeker;

    public SeekerCommand(Plugin plugin, MiniGameManager manager) {
        this.manager = manager;
        this.plugin = plugin;
    }

    @Override
    public void executePlayerCommand(@NotNull Player player,
        @NotNull String[] args) {
        if (args.length < 1) {
            MessageSender.sendPlayerMessage(player);
            return;
        }
        if (args[0].equalsIgnoreCase("-")) {
            seeker = null;
            MessageSender.sendNextRoundSeeker(player, "random");
            return;
        }
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            MessageSender.sendPlayerMessage(player);
            return;
        }
        seeker = target;
        if (manager.getCurrentMiniGame() instanceof HideAndSeek) {
            HideAndSeek hideAndSeek = (HideAndSeek) manager.getCurrentMiniGame();
            if (hideAndSeek.canChangeSeeker()) {
                hideAndSeek.setSeeker(seeker);
                return;
            }
        }
        MessageSender.sendNextRoundSeeker(player, seeker.getName());
    }

    public Player getSeeker() {
        return seeker;
    }

    @Override
    public String getName() {
        return "seeker";
    }

    @Override
    public String getUniqueId() {
        return "seeker-set";
    }

    @Override
    public String getPermission() {
        return "hideseek-seeker";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
