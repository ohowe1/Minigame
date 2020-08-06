package me.ohowe12.hideandseek.minigames.hideandseek;

import me.ohowe12.hideandseek.minigame.MiniGame;
import me.ohowe12.hideandseek.minigame.MiniGameCommand;
import me.ohowe12.hideandseek.minigame.MiniGameManager;

public class HideAndSeekCommand extends MiniGameCommand {

  public HideAndSeekCommand(MiniGameManager manager) {
    super(manager);
  }

  @Override
  protected Class<? extends MiniGame> getMiniGameClass() {
    return HideAndSeek.class;
  }

  @Override
  protected String getFriendlyName() {
    return "Hide and Seek";
  }

  @Override
  public String getUniqueId() {
    return "hide-and-seek";
  }
}
