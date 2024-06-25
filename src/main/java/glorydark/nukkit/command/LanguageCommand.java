package glorydark.nukkit.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import glorydark.nukkit.LanguageMain;
import glorydark.nukkit.storage.Language;
import glorydark.nukkit.storage.LanguageData;

import java.util.HashMap;
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
            if (strings[0].equals("list")) {
                StringBuilder builder = new StringBuilder("Loaded language data: ");
                for (Map.Entry<String, Language> entry : LanguageMain.getInstance().getLanguages().entrySet()) {
                    HashMap<String, LanguageData> rawData = entry.getValue().getRawData();
                    builder.append("\n").append(entry.getKey()).append("(").append(rawData.size()).append(" items)");
                    for (Map.Entry<String, LanguageData> languageDataEntry : rawData.entrySet()) {
                        builder.append("\n").append(" - ").append(languageDataEntry.getKey())
                                .append(" (").append(languageDataEntry.getValue().getRawData().size()).append(" items)");
                    }
                }
                commandSender.sendMessage(builder.toString());
            }
        }
        return false;
    }
}
