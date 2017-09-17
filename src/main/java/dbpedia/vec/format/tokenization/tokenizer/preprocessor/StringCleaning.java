package dbpedia.vec.format.tokenization.tokenizer.preprocessor;

import java.util.regex.Pattern;


public class StringCleaning {

    private static final Pattern punctPattern = Pattern.compile("[\\d\\.:,\"\'\\(\\)\\[\\]|/?!;]+");

    private StringCleaning() {}

    /**
     * Strip punctuation
     * @param base the base string
     * @return the cleaned string
     */
    public static String stripPunct(String base) {
        return punctPattern.matcher(base).replaceAll("");
    }
}
