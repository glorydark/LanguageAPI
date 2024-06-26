package glorydark.nukkit.storage;

import glorydark.nukkit.LanguageMain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {18:01}
 */
public class Language {

    private final Map<String, LanguageData> translations;

    public Language() {
        this.translations = new ConcurrentHashMap<>();
    }

    public void addLanguageData(String languageCode, LanguageData data) {
        if (data == null) {
            LanguageMain.getInstance().getLogger().warning("Null translation data is loaded!");
            return;
        }
        this.translations.put(languageCode, data);
    }

    public LanguageData getLanguageData(String languageCode) {
        return this.translations.getOrDefault(languageCode, this.translations.getOrDefault(LanguageMain.defaultLanguage, new LanguageData()));
    }

    public Map<String, LanguageData> getRawData() {
        return translations;
    }
}
