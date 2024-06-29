package glorydark.nukkit.storage;

import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.utils.LanguageReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {17:58}
 */
public class LanguageData {

    private final Map<String, String> translations;

    public LanguageData() {
        this.translations = new ConcurrentHashMap<>();
    }

    public LanguageData(HashMap<String, String> translations) {
        this.translations = translations;
    }

    public static LanguageData fromProperties(File file) {
        return LanguageReader.loadLanguageDataFromProperties(file);
    }

    public void addTranslationEntry(String key, String value) {
        this.translations.put(key, value);
    }

    public String getText(String key) {
        return this.translations.getOrDefault(key, this.translations.getOrDefault(LanguageMain.defaultLanguage, key));
    }

    public Map<String, String> getRawData() {
        return translations;
    }
}
