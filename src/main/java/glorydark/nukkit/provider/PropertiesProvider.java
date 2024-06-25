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
}
