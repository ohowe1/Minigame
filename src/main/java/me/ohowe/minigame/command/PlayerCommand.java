package me.ohowe.minigame.command;

import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerCommand extends GlobalCommand {

    @Override
    default void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            MessageSender.sendConsoleMessage(sender);
            return;
        }
        executePlayerCommand((Player) sender, args);
    }

    void executePlayerCommand(@NotNull Player player, @NotNull String[] args);
}
