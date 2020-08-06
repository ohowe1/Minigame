package me.ohowe12.hideandseek.minigame;

import me.ohowe12.hideandseek.command.PlayerCommand;
import me.ohowe12.hideandseek.utils.MessageSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class MiniGameCommand implements PlayerCommand {

  protected final MiniGameManager manager;

  protected MiniGameCommand(MiniGameManager manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return getFriendlyName().replace(" ", "");
  }

  protected abstract Class<? extends MiniGame> getMiniGameClass();

  protected abstract String getFriendlyName();

  @Override
  public String getPermission() {
    return "minigame-set";
  }

  @Override
  public void executePlayerCommand(@NotNull Player player, @NotNull String[] args) {
    manager.setMiniGame(getMiniGameClass());
    MessageSender.sendMinigameSetMessage(player, getFriendlyName());
  }
}
