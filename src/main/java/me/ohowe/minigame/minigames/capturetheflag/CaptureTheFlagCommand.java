package me.ohowe.minigame.minigames.capturetheflag;

import me.ohowe.minigame.minigame.MiniGame;
import me.ohowe.minigame.minigame.MiniGameCommand;
import me.ohowe.minigame.minigame.MiniGameManager;

public class CaptureTheFlagCommand extends MiniGameCommand {

    protected CaptureTheFlagCommand(MiniGameManager manager) {
        super(manager);
    }

    @Override
    protected Class<? extends MiniGame> getMiniGameClass() {
        return CaptureTheFlag.class;
    }

    @Override
    protected String getFriendlyName() {
        return "Capture the Flag";
    }

}
