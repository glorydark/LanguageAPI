package glorydark.nukkit.utils;

/**
 * @author glorydark
 */
public class ReplacementContainer {

    protected String text;

    public ReplacementContainer(String text, Object... replacements) {
        this.text = text;
        for (int i = 1; i <= replacements.length; i++) {
            this.text = this.text.replace("%" + i + "%", replacements[i - 1].toString());
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
