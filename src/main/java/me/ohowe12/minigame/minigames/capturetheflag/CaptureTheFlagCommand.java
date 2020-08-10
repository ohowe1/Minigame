package me.ohowe12.minigame.minigames.capturetheflag;

import me.ohowe12.minigame.minigame.MiniGame;
import me.ohowe12.minigame.minigame.MiniGameCommand;
import me.ohowe12.minigame.minigame.MiniGameManager;

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
