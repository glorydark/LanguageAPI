package glorydark.nukkit.storage;

import java.util.HashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {18:01}
 */
public class Language {

    private HashMap<String, LanguageData> translations;

    public Language() {
        this.translations = new HashMap<>();
    }

    public void addLanguageData(String languageCode, LanguageData data) {
        translations.put(languageCode, data);
    }

    public LanguageData getLanguageData(String languageCode) {
        return translations.get(languageCode);
    }
}
