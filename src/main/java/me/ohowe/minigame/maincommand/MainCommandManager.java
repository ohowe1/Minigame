package me.ohowe.minigame.maincommand;

import me.ohowe.minigame.Plugin;
import me.ohowe.minigame.command.CommandCommandManager;
import me.ohowe.minigame.command.GlobalCommand;
import me.ohowe.minigame.command.HelpCommand;
import me.ohowe.minigame.minigame.StartGameCommand;
import me.ohowe.minigame.minigame.StopCommand;
import me.ohowe.minigame.utils.Language;

public class MainCommandManager extends CommandCommandManager {

    private HelpCommand helpCommand;

    public MainCommandManager(Plugin plugin, Language language) {
        super(plugin, language);
    }

    public MainCommandManager(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "minigame";
    }

    @Override
    protected GlobalCommand getHelpCommand() {
        return helpCommand;
    }


    @Override
    protected void createCommands() {
        helpCommand = new HelpCommand(this, language);
        addCommand(helpCommand);
        addCommand(new StartGameCommand(plugin));
        addCommand(new StopCommand(plugin));
    }
}
