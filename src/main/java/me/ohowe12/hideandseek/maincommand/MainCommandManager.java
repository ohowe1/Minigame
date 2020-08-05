package me.ohowe12.hideandseek.maincommand;

import me.ohowe12.hideandseek.Plugin;
import me.ohowe12.hideandseek.command.CommandCommandManager;
import me.ohowe12.hideandseek.command.GlobalCommand;
import me.ohowe12.hideandseek.command.HelpCommand;
import me.ohowe12.hideandseek.minigames.StartGameCommand;
import me.ohowe12.hideandseek.minigames.StopCommand;
import me.ohowe12.hideandseek.minigames.hideandseek.HideAndSeekCommand;
import me.ohowe12.hideandseek.utils.Language;

public class MainCommandManager extends CommandCommandManager {
    private HelpCommand helpCommand;

    @Override
    public String getName() {
        return "minigame";
    }

    @Override
    protected GlobalCommand getHelpCommand() {
        return helpCommand;
    }

    public MainCommandManager(Plugin plugin, Language language) {
        super(plugin, language);
    }

    public MainCommandManager(Plugin plugin) {
        super(plugin);
    }


    @Override
    protected void createCommands() {
        helpCommand = new HelpCommand(this, language);
        addCommand(helpCommand);
        addCommand(new HideAndSeekCommand(plugin));
        addCommand(new StartGameCommand(plugin));
        addCommand(new StopCommand(plugin));
    }
}
