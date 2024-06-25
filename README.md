# TranslationAPI
Basic TranslationAPI for Nukkit

# How to use
请自行将版本号进行更改！
```xml
<dependency>
    <groupId>glorydark.nukkit</groupId>
    <artifactId>LanguageAPI</artifactId>
    <version>1.0.5</version>
    <scope>provided</scope>
</dependency>
```

# How to add a language
## Step 1
```java
// Category is an identifier, which allows you to load various files while keeping them apart from each other
Language language = new Language("test_category");
```

## Step 2
```java
LanguageData languageData = new LanguageData();

// Then try to add entries to the language data
// Way 1
languageData.addTranslationEntry("test_entry", "test");
// Way 2
languageData = new PropertiesProvider(path+"/zh_CN.properties").parse();

// Specify which language this is for
language.addLanguageData("en_US", languageData);
```

## Last Step
```java
// add your file
LanguageMain.getLanguageMain().addLanguage("test_category", language);
```

# How to get a translation
## Methods
public String getPlayerLanguageData(Player player)

public String getTranslation(Player player, String category, String key)

public String getTranslation(String languageCode, String category, String key)

## Example
```java
LanguageMain.getLanguageMain().getTranslation("zh_CN", "test_category", "test_entry");
```

## Language Code List From Vanilla ResourcePack
**This json file will generate in the plugin data folder automatically.**
```json
[
  [ "en_US", "English (US)" ],
  [ "en_GB", "English (UK)" ],
  [ "de_DE", "Deutsch (Deutschland)" ],
  [ "es_ES", "Español (España)" ],
  [ "es_MX", "Español (México)" ],
  [ "fr_FR", "Français (France)" ],
  [ "fr_CA", "Français (Canada)" ],
  [ "it_IT", "Italiano (Italia)" ],
  [ "ja_JP", "日本語 (日本)" ],
  [ "ko_KR", "한국어 (대한민국)" ],
  [ "pt_BR", "Português (Brasil)" ],
  [ "pt_PT", "Português (Portugal)" ],
  [ "ru_RU", "Русский (Россия)" ],
  [ "zh_CN", "简体中文" ],
  [ "zh_TW", "繁體中文" ],
  [ "nl_NL", "Nederlands (Nederland)" ],
  [ "bg_BG", "Български (BG)" ],
  [ "cs_CZ", "Čeština (Česká republika)" ],
  [ "da_DK", "Dansk (DA)" ],
  [ "el_GR", "Ελληνικά (Ελλάδα)" ],
  [ "fi_FI", "Suomi (Suomi)" ],
  [ "hu_HU", "Magyar (HU)" ],
  [ "id_ID", "Bahasa Indonesia (Indonesia)" ],
  [ "nb_NO", "Norsk bokmål (Norge)" ],
  [ "pl_PL", "Polski (PL)" ],
  [ "sk_SK", "Slovensky (SK)" ],
  [ "sv_SE", "Svenska (Sverige)" ],
  [ "tr_TR", "Türkçe (Türkiye)" ],
  [ "uk_UA", "Українська (Україна)" ]
]
```
