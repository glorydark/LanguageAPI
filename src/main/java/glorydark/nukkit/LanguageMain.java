package glorydark.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.nukkit.storage.Language;

import java.util.HashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {17:49}
 */
public class LanguageMain extends PluginBase implements Listener {

    private final HashMap<String, Language> languages = new HashMap<>();

    public static String defaultLanguage;

    private static LanguageMain languageMain;

    @Override
    public void onEnable() {
        languageMain = this;
        this.saveDefaultConfig();
        this.saveResource("language_code_list.json", false);
        defaultLanguage = new Config(this.getDataFolder().getPath() + "/config.yml", Config.YAML).getString("default_language", "zh_CN");
        this.getLogger().info("LanguageAPI Enabled!");
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    protected String getPlayerLanguageData(Player player) {
        return player.getLoginChainData().getLanguageCode();
    }

    public String getTranslation(Player player, String category, String key) {
        return this.getTranslation(player, category, key, key);
    }

    public String getTranslation(Player player, String category, String key, String defaultValue) {
        return this.getTranslation(this.getPlayerLanguageData(player), category, key, defaultValue);
    }

    public String getTranslation(String languageCode, String category, String key) {
        return this.getTranslation(languageCode, category, key, key);
    }

    public String getTranslation(String languageCode, String category, String key, String defaultValue) {
        if(this.languages.containsKey(category)){
            return this.languages.get(category).getLanguageData(languageCode).getTranslation(key, defaultValue);
        }else{
            return defaultValue;
        }
    }

    public void addLanguage(String category, Language language) {
        this.languages.put(category, language);
    }

    public static LanguageMain getLanguageMain() {
        return languageMain;
    }
}