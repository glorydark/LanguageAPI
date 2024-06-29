package glorydark.nukkit.utils;

/**
 * @author glorydark
 */
public class ReplacementContainer {

    protected String text;

    public ReplacementContainer(String text, Object... replacements) {
        this.text = text;
        for (int i = 1; i <= replacements.length; i++) {
            Object object = replacements[i - 1];
            if (object == null) {
                continue;
            }
            this.text = this.text.replace("%" + i + "%", object.toString());
        }
        this.text = this.text.replace("\\n", "\n");
    }

    public String processText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
