package glorydark.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.nukkit.command.LanguageCommand;
import glorydark.nukkit.storage.Language;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {17:49}
 */
public class LanguageMain extends PluginBase implements Listener {

    public static String defaultLanguage;
    private static LanguageMain instance;
    private final ConcurrentHashMap<String, Language> languages = new ConcurrentHashMap<>();

    public static LanguageMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.saveResource("language_code_list.json", false);
        defaultLanguage = new Config(this.getDataFolder().getPath() + "/config.yml", Config.YAML).getString("default_language", "en_US");
        this.getLogger().info("LanguageAPI Enabled!");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getCommandMap().register("", new LanguageCommand("lang"));
    }

    protected String getPlayerLanguageData(Player player) {
        return player.getLoginChainData().getLanguageCode();
    }

    public String getTranslation(Plugin plugin, Player player, String key, Object... replacements) {
        return this.getTranslation(plugin, this.getPlayerLanguageData(player), key, replacements);
    }

    public String getTranslation(Plugin plugin, String languageCode, String key, Object... replacements) {
        return getTranslation(plugin.getName(), languageCode, key, replacements);
    }

    public String getTranslation(String categoryName, String languageCode, String key, Object... replacements) {
        if (this.languages.containsKey(categoryName)) {
            return this.languages.get(categoryName).getLanguageData(languageCode).getTranslation(key, replacements);
        } else {
            return key;
        }
    }

    public void addLanguage(Plugin plugin, Language language) {
        this.addLanguage(plugin.getName(), language);
    }

    public void addLanguage(String categoryName, Language language) {
        if (this.languages.containsKey(categoryName)) {
            this.getLogger().warning("Found a duplicate category: " + categoryName + ". Trying to replace it.");
        }
        this.languages.put(categoryName, language);
    }

    public void clearLanguage(Plugin plugin) {
        this.languages.remove(plugin.getName());
    }

    public void clearLanguage(String categoryName) {
        this.languages.remove(categoryName);
    }

    public ConcurrentHashMap<String, Language> getLanguages() {
        return languages;
    }
}