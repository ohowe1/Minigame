package me.ohowe12.minigame.command;

import me.ohowe12.minigame.utils.Formatter;
import me.ohowe12.minigame.utils.Language;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements GlobalCommand {

    private final CommandManager manager;
    private final Language language;
    private final String header;

    public HelpCommand(CommandManager manager, Language language) {
        this.manager = manager;
        this.language = language;
        this.header = "/" + manager.getFullName();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getUniqueId() {
        return "helpCommand";
    }

    @Override
    public String[] getReplacement() {
        return new String[]{"{COMMAND}", "/" + manager.getFullName()};
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        StringBuilder message = new StringBuilder();
        message
            .append(Formatter.formatCommand(language.getColorizedHeader("help"), header))
            .append("\n");
        message.append(ChatColor.DARK_AQUA).append("- - - - - - - - - - - - - - - - -\n");

        for (GlobalCommand command : manager.getCommands()) {
            if (command.isHidden()) {
                continue;
            }
            message.append(getInformation(command));
        }
        sender.sendMessage(message.toString());
    }

    private String getInformation(GlobalCommand command) {
        return ChatColor.AQUA
            + header
            + " "
            + command.getName()
            + ChatColor.BLUE
            + " - "
            + ChatColor.DARK_AQUA
            + (command.getReplacement() == null
            ? language.getDescription(command.getUniqueId())
            : language
                .getDescription(command.getUniqueId())
                .replace(command.getReplacement()[0], command.getReplacement()[1]))
            + "\n";
    }
}
