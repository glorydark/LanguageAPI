package glorydark.nukkit.storage;

import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.exception.LoadDataException;
import glorydark.nukkit.provider.PropertiesProvider;
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

    /**
     * @param category It divides different language translation collections into different categories. E.g. skywars, lobby, ...
     * @param file     The dictionary that are needed to load, which contains various properties files. E.g. zh_cn.properties.
     */
    public static void loadLanguageFromDictionary(String category, File file) throws LoadDataException {
        Language language = new Language();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File listFile : listFiles) {
                    language.addLanguageData(listFile.getName().replace(".properties", ""), new PropertiesProvider(listFile).parse());
                }
                LanguageMain.getLanguageMain().addLanguage(category, language);
            } else {
                throw new LoadDataException("Method file.listFiles() returns a null value");
            }
        } else {
            throw new LoadDataException("File is not a category");
        }
    }

    public void addTranslationEntry(String key, String value) {
        this.translations.put(key, value);
    }

    public String getTranslation(String key, Object... replacements) {
        String text = this.translations.getOrDefault(key, this.translations.getOrDefault(LanguageMain.defaultLanguage, key));
        text = text.replace("\\n", "\n");
        return new ReplacementContainer(text, replacements).getText();
    }
}
