package me.ohowe.minigame.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import me.ohowe.minigame.Plugin;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

public class Language {

    private final Plugin plugin;
    private final List<String> languages = Arrays.asList("en-US", "de-DE", "es-ES");
    private JsonObject languageJson;

    public Language(Plugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public String getMessage(String title) {
        return getTranslation("messages", title);
    }

    public String getColorizedMessage(String title) {
        return ChatColor.translateAlternateColorCodes('&', getMessage(title));
    }

    public String getDescription(String uniqueId) {
        return getTranslation("command-descriptions", uniqueId);
    }

    public String getHeader(String name) {
        return getTranslation("headers", name);
    }

    public String getColorizedHeader(String name) {
        return ChatColor.translateAlternateColorCodes(
            '&', Objects.requireNonNull(getTranslation("headers", name)));
    }

    public void reload() {
        String language = plugin.getLanguageCode();
        languageJson = getJsonObject(language);
    }

    private JsonObject getJsonObject(@Nullable String language) {
        if (language == null) {
            language = "en-US";
        }
        if (!languages.contains(language)) {
            plugin.getPluginLogger()
                .log(Logger.ANSI_RED + "That is an invalid language! Using en-US.");
            language = "en-US";
        }
        InputStream is = plugin.getResource("Languages/" + language + ".json");

        if (is == null) {
            badLanguage();
            return null;
        }
        JsonElement element = new JsonParser().parse(new InputStreamReader(is));
        return element.getAsJsonObject();
    }

    private String getTranslation(String category, String name) {

        JsonObject objectCategory = getCategory(category);
        if (objectCategory == null) {
            return null;
        }

        JsonElement jsonName = objectCategory.get(name);
        if (jsonName == null) {
            jsonName = getCategory(category, "en-US").get(name);
            if (jsonName == null) {
                badLanguage();
                return null;
            }
        }

        if (!jsonName.isJsonPrimitive()) {
            badLanguage();
            return null;
        }
        return jsonName.getAsString();
    }

    private JsonObject getCategory(String category, JsonObject languageJsonToUse) {
        if (languageJsonToUse == null) {
            return null;
        }
        JsonElement jsonCategory = languageJsonToUse.get(category);

        if (jsonCategory == null) {
            jsonCategory = Objects.requireNonNull(getJsonObject("en-US")).get(category);
            if (jsonCategory == null) {
                badLanguage();
                return null;
            }
        }
        if (!jsonCategory.isJsonObject()) {
            badLanguage();
            return null;
        }
        return jsonCategory.getAsJsonObject();
    }

    private JsonObject getCategory(String category, String language) {
        return getCategory(category, getJsonObject(language));
    }

    private JsonObject getCategory(String category) {
        return getCategory(category, languageJson);
    }

    private void badLanguage() {
        plugin.getPluginLogger().log(Logger.ANSI_RED + "Cannot get language! This is bad.");
        plugin
            .getPluginLogger()
            .log(
                Logger.ANSI_RED
                    + "If you did not mess with the .jar at all please report it to the developer!");
    }
}
