package me.ohowe12.minigame.maincommand;

import me.ohowe12.minigame.Plugin;
import me.ohowe12.minigame.command.CommandCommandManager;
import me.ohowe12.minigame.command.GlobalCommand;
import me.ohowe12.minigame.command.HelpCommand;
import me.ohowe12.minigame.minigame.StartGameCommand;
import me.ohowe12.minigame.minigame.StopCommand;
import me.ohowe12.minigame.minigames.hideandseek.HideAndSeekCommand;
import me.ohowe12.minigame.utils.Language;

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
    addCommand(new HideAndSeekCommand(plugin.getMiniGameManager()));
    addCommand(new StartGameCommand(plugin));
    addCommand(new StopCommand(plugin));
  }
}
