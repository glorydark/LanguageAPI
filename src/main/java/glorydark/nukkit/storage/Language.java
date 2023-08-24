package glorydark.nukkit.storage;

import java.util.HashMap;

/**
 * @author glorydark
 * @date {2023/8/24} {18:01}
 */
public class Language {

    private final String name;

    private final HashMap<String, LanguageData> translations = new HashMap<>();

    public Language(String name) {
        this.name = name;
    }

    public void addLanguageData(String languageCode, LanguageData data) {
        translations.put(languageCode, data);
    }

    public String getName() {
        return name;
    }

    public LanguageData getLanguageData(String languageCode) {
        return translations.get(languageCode);
    }
}
