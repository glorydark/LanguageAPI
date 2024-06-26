package glorydark.nukkit.utils;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;

import java.io.File;

/**
 * @author glorydark
 * @date {2023/8/24} {18:17}
 */
public abstract class LanguageReader {

    public static void loadLanguageFromDictionary(Plugin plugin, File file) {
        loadLanguageFromDictionary(plugin.getName(), file);
    }

    public static void loadLanguageFromDictionary(String category, File file) {
        Language language = new Language();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                if (listFiles.length == 0) {
                    LanguageMain.getInstance().getLogger().warning("Stopped loading category" + category + " because " + file + " is empty.");
                } else {
                    for (File listFile : listFiles) {
                        language.addLanguageData(listFile.getName().substring(0, listFile.getName().lastIndexOf(".")), loadLanguageDataFromProperties(listFile));
                    }
                    LanguageMain.getInstance().addLanguage(category, language);
                }
            } else {
                LanguageMain.getInstance().getLogger().error("Method file.listFiles() returns a null value");
            }
        } else {
            LanguageMain.getInstance().getLogger().error("File is not a category");
        }
    }

    public static LanguageData loadLanguageDataFromProperties(File file) {
        if (file.getName().endsWith(".properties")) {
            LanguageData data = new LanguageData();
            new Config(file, Config.PROPERTIES).getAll().forEach((s, o) -> data.addTranslationEntry(s, String.valueOf(o)));
            return data;
        } else {
            LanguageMain.getInstance().getLogger().error("Reading wrong-format files. File Name: " + file.getName());
            return null;
        }
    }
}
