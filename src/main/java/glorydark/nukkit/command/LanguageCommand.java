package glorydark.nukkit.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * @author glorydark
 */
public class LanguageCommand extends Command {
    public LanguageCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.isOp()) {
            return false;
        }
        if (strings.length == 1) {
            switch (strings[0]) {
                case "stats":
                    StringBuilder builder = new StringBuilder("Loaded language data: ");
                    for (Map.Entry<String, Language> entry : LanguageMain.getInstance().getLanguages().entrySet()) {
                        Map<String, LanguageData> rawData = entry.getValue().getRawData();
                        builder.append("\n").append(entry.getKey()).append("(").append(rawData.size()).append(" items)");
                        for (Map.Entry<String, LanguageData> languageDataEntry : rawData.entrySet()) {
                            builder.append("\n").append(" - ").append(languageDataEntry.getKey())
                                    .append(" (").append(languageDataEntry.getValue().getRawData().size()).append(" items)");
                        }
                    }
                    @NonNull CacheStats stats = LanguageMain.getInstance().getTranslationCache().stats();
                    builder.append("\n").append("Cache stats: ")
                            .append("\n").append(stats);
                    commandSender.sendMessage(builder.toString());
                    break;
                case "reload":
                    Config config = new Config(LanguageMain.getInstance().getDataFolder().getPath() + "/config.yml", Config.YAML);
                    LanguageMain.defaultLanguage = config.getString("default_language", "en_US");
                    LanguageMain.forceDefaultLanguage = config.getBoolean("force_default_language", false);
                    LanguageMain.getInstance().getLogger().info("Reload successfully!");
                    break;
            }
        }
        return false;
    }
}
