package glorydark.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;

import java.util.HashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {17:49}
 */
public class LanguageMain extends PluginBase implements Listener {

    public static final String KEY_NOT_FOUND = "KEY_NOT_FOUND";

    private final HashMap<String, Language> languageBase = new HashMap<>();

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

    @EventHandler
    public void onJoin(PlayerLocallyInitializedEvent event){
        event.getPlayer().sendMessage(event.getPlayer().getLoginChainData().getLanguageCode());
        event.getPlayer().sendMessage(LanguageMain.getLanguageMain().getTranslation(event.getPlayer(), "test_category", "test_entry"));
    }

    public String getPlayerLanguageData(Player player) {
        return player.getLoginChainData().getLanguageCode();
    }

    public String getTranslation(Player player, String category, String key) {
        return this.getTranslation(player, category, key, KEY_NOT_FOUND);
    }

    public String getTranslation(Player player, String category, String key, String defaultValue) {
        return this.getTranslation(getPlayerLanguageData(player), category, key, defaultValue);
    }

    public String getTranslation(String languageCode, String category, String key) {
        return this.getTranslation(languageCode, category, key, KEY_NOT_FOUND);
    }

    public String getTranslation(String languageCode, String category, String key, String defaultValue) {
        if(languageBase.containsKey(category)){
            return languageBase.get(category).getLanguageData(languageCode).getTranslation(key, defaultValue);
        }else{
            return defaultValue;
        }
    }

    public void addLanguage(String category, Language language) {
        this.languageBase.put(category, language);
    }

    public static LanguageMain getLanguageMain() {
        return languageMain;
    }
}