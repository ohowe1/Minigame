package me.ohowe.minigame.command;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface GlobalCommand {

    String getName();

    String getUniqueId();

    default String getPermission() {
        return null;
    }

    default String[] getReplacement() {
        return null;
    }

    default boolean isHidden() {
        return false;
    }

    void onCommand(@NotNull CommandSender sender, @NotNull String[] args);

    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

    default boolean canExecute(CommandSender sender) {
        if (getPermission() == null) {
            return true;
        }
        return sender.hasPermission(getPermission());
    }
}
