package glorydark.nukkit.provider;

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
public class PropertiesProvider {

    protected File file;

    public PropertiesProvider(File file) {
        this.file = file;
    }

    public LanguageData parse() throws LoadDataException {
        if(file.getName().endsWith(".properties")) {
            LanguageData data = new LanguageData();
            new Config(file, Config.PROPERTIES).getAll().forEach((s, o) -> data.addTranslationEntry(s, String.valueOf(o)));
            return data;
        } else {
            throw new LoadDataException("Reading wrong-format files. File Name: "+ file.getName());
        }
    }

    /**
     *
     * @param category It divides different language translation collections into different categories. E.g. skywars, lobby, ...
     * @param file The dictionary that are needed to load, which contains various properties files. E.g. zh_cn.properties.
     */
    public static void loadLanguageFromDictionary(String category, File file) throws LoadDataException {
        Language language = new Language(category);
        if(file.isDirectory()){
            File[] listFiles = file.listFiles();
            if(listFiles != null) {
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
}
