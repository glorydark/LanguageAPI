package glorydark.nukkit.storage;

import glorydark.nukkit.LanguageMain;

import java.util.HashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {17:58}
 */
public class LanguageData {

    private final HashMap<String, String> translations;

    public LanguageData() {
        this.translations = new HashMap<>();
    }

    public LanguageData(HashMap<String, String> translations) {
        this.translations = translations;
    }

    public void addTranslationEntry(String key, String value) {
        translations.put(key, value);
    }

    public String getTranslation(String key) {
        return translations.getOrDefault(key, translations.getOrDefault(LanguageMain.defaultLanguage, key));
    }

    public String getTranslation(String key, String defaultValue) {
        return translations.getOrDefault(key, translations.getOrDefault(LanguageMain.defaultLanguage, defaultValue));
    }
}
