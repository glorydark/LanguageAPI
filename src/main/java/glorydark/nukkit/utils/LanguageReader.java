package glorydark.nukkit.utils;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.exception.LoadDataException;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;

import java.io.File;

/**
 * @author glorydark
 * @date {2023/8/24} {18:17}
 */
public abstract class LanguageReader {

    public static void loadLanguageFromDictionary(Plugin plugin, File file) throws LoadDataException {
        Language language = new Language();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File listFile : listFiles) {
                    language.addLanguageData(listFile.getName().substring(0, listFile.getName().lastIndexOf(".")), parse(listFile));
                }
                LanguageMain.getInstance().addLanguage(plugin, language);
            } else {
                throw new LoadDataException("Method file.listFiles() returns a null value");
            }
        } else {
            throw new LoadDataException("File is not a category");
        }
    }

    public static LanguageData parse(File file) {
        try {
            if (file.getName().endsWith(".properties")) {
                LanguageData data = new LanguageData();
                new Config(file, Config.PROPERTIES).getAll().forEach((s, o) -> data.addTranslationEntry(s, String.valueOf(o)));
                return data;
            } else {
                throw new LoadDataException("Reading wrong-format files. File Name: " + file.getName());
            }
        } catch (Exception ignored) {
            return null;
        }
    }
}
