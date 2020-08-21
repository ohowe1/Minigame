package me.ohowe.minigame.command;

import java.util.List;
import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.utils.Language;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommandManager extends CommandManager implements GlobalCommand {

    private final CommandManager manager;

    public SubCommandManager(Plugin plugin, Language language, CommandManager manager) {
        super(plugin, language);
        this.manager = manager;
    }

    public SubCommandManager(Plugin plugin, CommandManager manager) {
        this(plugin, plugin.getLanguage(), manager);
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        processCommand(sender, args);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return processTabComplete(sender, args);
    }

    @Override
    public String getFullName() {
        return manager.getFullName() + " " + getName();
    }
}
