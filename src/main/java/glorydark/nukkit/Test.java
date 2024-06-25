package glorydark.nukkit;

import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;

/**
 * @author glorydark
 * @date {2023/8/24} {19:23}
 */
public class Test {

    public static void main(String[] args){

    }

    public void test(){
        Language language = new Language();
        LanguageData languageData = new LanguageData();
        languageData.addTranslationEntry("test_entry", "测试");
        language.addLanguageData("zh_CN", languageData);
        LanguageMain.getLanguageMain().addLanguage("test_category", language);

        System.out.println(LanguageMain.getLanguageMain().getTranslation("zh_CN", "test_category", "test_entry"));
    }

}
