package me.ohowe.minigame;

import me.ohowe.minigame.maincommand.MainCommandManager;
import me.ohowe.minigame.minigame.MiniGameManager;
import me.ohowe.minigame.minigames.hideandseek.HideAndSeek;
import me.ohowe.minigame.minigames.hideandseek.SeekerCommand;
import me.ohowe.minigame.utils.Language;
import me.ohowe.minigame.utils.Logger;
import me.ohowe.minigame.utils.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    //    private final String NMS_VERSION = Server.class.getPackage().getName().substring(23);
    private Language language;
    private Config configManager;
    private Logger pluginLogger;
    private MainCommandManager mainCommandManager;
    private MiniGameManager miniGameManager;

    public MainCommandManager getMainCommandManager() {
        return mainCommandManager;
    }

    public Language getLanguage() {
        return language;
    }

    public Config getConfigManager() {
        return configManager;
    }

    public Logger getPluginLogger() {
        return pluginLogger;
    }

    public String getLanguageCode() {
        return configManager.getString("language");
    }

    public MiniGameManager getMiniGameManager() {
        return miniGameManager;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        configManager = new Config(this);
        pluginLogger = new Logger(this);
        language = new Language(this);
        MessageSender.init(language);
        miniGameManager = new MiniGameManager(this,
            Bukkit.getWorld(configManager.getString("world")));
        mainCommandManager = new MainCommandManager(this);

        miniGameManager.registerMinigame(HideAndSeek.class, "Hide and Seek");
        miniGameManager.registerSpecialCommand(new SeekerCommand(this, miniGameManager));
    }

    @Override
    public void onDisable() {
        miniGameManager.stopGame();
    }
}
