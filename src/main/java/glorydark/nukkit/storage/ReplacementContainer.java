package glorydark.nukkit.storage;

/**
 * @author glorydark
 */
public class ReplacementContainer {

    private final String[] replacements;

    public ReplacementContainer(String... replacements) {
        this.replacements = replacements;
    }

    public String replace(String text) {
        for (int i = 1; i <= replacements.length; i++) {
            text = text.replace("%" + i + "%", replacements[i - 1]);
        }
        return text;
    }
}
