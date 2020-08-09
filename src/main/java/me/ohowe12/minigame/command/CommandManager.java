package me.ohowe12.minigame.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import me.ohowe12.minigame.Plugin;
import me.ohowe12.minigame.utils.Language;
import me.ohowe12.minigame.utils.Logger;
import me.ohowe12.minigame.utils.MessageSender;
import org.bukkit.command.CommandSender;

public abstract class CommandManager {

    protected final Plugin plugin;
    protected final Logger logger;
    protected final Language language;
    private final ArrayList<GlobalCommand> commands = new ArrayList<>();

    public CommandManager(Plugin plugin, Language language) {
        this.plugin = plugin;
        this.language = language;
        logger = plugin.getPluginLogger();
        logger.debugLog("Command manager created");
        createCommands();
    }

    public CommandManager(Plugin plugin) {
        this(plugin, plugin.getLanguage());
    }

    public abstract String getName();

    public List<GlobalCommand> getCommands() {
        return new ArrayList<>(commands);
    }

    protected abstract GlobalCommand getHelpCommand();

    public abstract String getFullName();

    protected List<String> processTabComplete(CommandSender sender, String[] args) {
        List<String> possibleCommandNames =
            commands.stream()
                .filter(command -> shouldTabComplete(command, sender))
                .map(GlobalCommand::getName)
                .collect(Collectors.toList());
        List<String> results = new ArrayList<>();

        if (args.length == 1) {
            for (String a : possibleCommandNames) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    results.add(a);
                }
            }
            return results;
        } else if (args.length > 1) {
            for (GlobalCommand globalCommand : commands) {
                if (globalCommand.getName() == null) {
                    continue;
                }
                if (!globalCommand.getName().equalsIgnoreCase(args[0])) {
                    continue;
                }
                if (!sender.hasPermission(globalCommand.getPermission())
                    && !globalCommand.getName().isEmpty()) {
                    continue;
                }
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                return globalCommand.onTabComplete(sender, newArgs);
            }
        }

        return null;
    }

    private boolean shouldTabComplete(GlobalCommand command, CommandSender sender) {
        return command.canExecute(sender) && !(command.getName() == null) && !command.isHidden();
    }

    protected void processCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            getHelpCommand().onCommand(sender, args);
            return;
        }
        for (GlobalCommand globalCommand : commands) {
            globalCommand.getName();
            if (!globalCommand.getName().equalsIgnoreCase(args[0])) {
                continue;
            }
            if (globalCommand.getPermission() != null) {
                if (!sender.hasPermission(globalCommand.getPermission())
                    && !globalCommand.getName().isEmpty()
                    && !(globalCommand.getName() == null)) {
                    MessageSender.sendPermissionMessage(sender);
                    return;
                }
            }
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
            globalCommand.onCommand(sender, newArgs);
            return;
        }
        MessageSender.sendUnknownMessage(sender);
    }

    protected abstract void createCommands();

    public final void addCommand(GlobalCommand command) {
        this.commands.add(command);
    }

    public final void removeCommand(GlobalCommand command) {
        this.commands.remove(command);
    }
}
