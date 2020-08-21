package me.ohowe.minigame.minigames.hideandseek;

import me.ohowe.minigame.minigame.MiniGame;
import me.ohowe.minigame.minigame.MiniGameCommand;
import me.ohowe.minigame.minigame.MiniGameManager;

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

}
