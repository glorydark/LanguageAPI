package glorydark.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.plugin.PluginDisableEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import glorydark.nukkit.command.LanguageCommand;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;
import it.unimi.dsi.fastutil.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author glorydark
 * @date {2023/8/24} {17:49}
 */
public class LanguageMain extends PluginBase implements Listener {

    public static String defaultLanguage;
    public static boolean forceDefaultLanguage;
    private static LanguageMain instance;
    private final ConcurrentHashMap<String, Language> languages = new ConcurrentHashMap<>();
    private Cache<String, String> translationCache; // 对常用的键添加缓存

    public static LanguageMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.saveResource("language_code_list.json", false);
        Config config = new Config(this.getDataFolder().getPath() + "/config.yml", Config.YAML);
        defaultLanguage = config.getString("default_language", "en_US");
        forceDefaultLanguage = config.getBoolean("force_default_language", false);
        translationCache = Caffeine.newBuilder()
                .maximumSize(config.getInt("max_cache_size", 1000)) // 设置缓存的最大容量
                .expireAfterWrite(config.getInt("cache_expired_rate", 10), TimeUnit.MINUTES) // 设置缓存项的过期时间
                .recordStats() // 启用统计信息
                .build();
        this.getLogger().info("LanguageAPI Enabled!");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getCommandMap().register("", new LanguageCommand("lang"));
    }

    protected String getPlayerLanguageData(Player player) {
        if (LanguageMain.forceDefaultLanguage) {
            return defaultLanguage;
        }
        return player.getLoginChainData().getLanguageCode();
    }

    public String getTranslation(Plugin plugin, Player player, String key, Object... replacements) {
        return this.getTranslation(plugin, this.getPlayerLanguageData(player), key, replacements);
    }

    public String getTranslation(Plugin plugin, String languageCode, String key, Object... replacements) {
        return getTranslation(plugin.getName(), languageCode, key, replacements);
    }

    public String getTranslation(String categoryName, Player player, String key, Object... replacements) {
        return getTranslation(categoryName, this.getPlayerLanguageData(player), key, replacements);
    }

    public String getTranslation(String categoryName, String languageCode, String key, Object... replacements) {
        String cacheKey = categoryName + ":" + languageCode + ":" + key;
        return translationCache.get(cacheKey, (Function<String, String>) o -> {
            if (languages.containsKey(categoryName)) {
                return languages.get(categoryName).getLanguageData(languageCode).getTranslation(key, replacements);
            } else {
                return key;
            }
        });
    }

    public void addLanguage(Plugin plugin, Language language) {
        this.addLanguage(plugin.getName(), language);
    }

    public void addLanguage(String categoryName, Language language) {
        if (this.languages.containsKey(categoryName)) {
            this.getLogger().warning("Found a duplicate category: " + categoryName + ". Trying to replace it and refresh all caches.");
        }
        this.languages.put(categoryName, language);
    }

    public void clearLanguage(Plugin plugin) {
        this.clearLanguage(plugin.getName());
    }

    public void clearLanguage(String categoryName) {
        List<String> removeItems = new ArrayList<>();
        for (String languageName : this.languages.keySet()) {
            for (Map.Entry<String, LanguageData> languageCodeEntry : this.languages.getOrDefault(languageName, new Language()).getRawData().entrySet()) {
                for (String key : languageCodeEntry.getValue().getRawData().keySet()) {
                    removeItems.add(categoryName + ":" + languageCodeEntry.getKey() + ":" + key);
                }
            }
        }
        this.translationCache.invalidateAll(removeItems);
        this.languages.remove(categoryName);
    }

    public ConcurrentHashMap<String, Language> getLanguages() {
        return languages;
    }

    @EventHandler
    public void PluginDisableEvent(PluginDisableEvent event) {
        this.clearLanguage(event.getPlugin()); // 将无用的缓存快速清理掉
    }

    public Cache<String, String> getTranslationCache() {
        return translationCache;
    }
}