package glorydark.nukkit.storage;

import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.utils.LanguageReader;
import glorydark.nukkit.utils.ReplacementContainer;

import java.io.File;
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

    public static LanguageData fromProperties(File file) {
        return LanguageReader.parse(file);
    }

    public void addTranslationEntry(String key, String value) {
        this.translations.put(key, value);
    }

    public String getTranslation(String key, Object... replacements) {
        String text = this.translations.getOrDefault(key, this.translations.getOrDefault(LanguageMain.defaultLanguage, key));
        text = text.replace("\\n", "\n");
        return new ReplacementContainer(text, replacements).getText();
    }

    public HashMap<String, String> getRawData() {
        return translations;
    }
}
