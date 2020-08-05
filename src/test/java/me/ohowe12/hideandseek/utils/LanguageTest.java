package me.ohowe12.hideandseek.utils;

import junit.framework.TestCase;
import me.ohowe12.hideandseek.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static org.mockito.Mockito.*;

public class LanguageTest extends TestCase {
    Language language;
    Plugin plugin;

    public void testGetMessage() {
        assertEquals("&cYou do not have permission to do that", language.getMessage("permission"));
    }

    public void testGetColorizedMessage() {
        assertEquals("§cYou do not have permission to do that", language.getColorizedMessage("permission"));
    }

    public void testGerman() {
        when(plugin.getLanguageCode()).thenReturn("de-DE");
        Language german = new Language(plugin);
        assertEquals("&cDu hast keine Erlaubnis das zu tun", german.getMessage("permission"));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        plugin = mock(Plugin.class);
        when(plugin.getLanguageCode()).thenReturn("en-US");
        when(plugin.getResource("Languages/en-US.json")).thenReturn(getResource("Languages/en-US.json"));
        when(plugin.getResource("Languages/de-DE.json")).thenReturn(getResource("Languages/de-DE.json"));

        language = new Language(plugin);
    }

    private InputStream getResource(String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }
}