package ch.poole.openinghoursparser;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Feature adapter allows to override behaviours from a client application.
 * @author JOSM team, Simon Legner
 */
public final class FeatureAdapter {

    private static TranslationAdapter translationAdapter = MessageFormat::format;

    /**
     * Translation support.
     * @author JOSM team, Simon Legner
     */
    public interface TranslationAdapter {
        /**
         * Translates some text for the current locale.
         * <br>
         * For example, <code>tr("JMapViewer''s default value is ''{0}''.", val)</code>.
         * <br>
         * @param text the text to translate. Must be a string literal. (No constants or local vars.)
         * Can be broken over multiple lines. An apostrophe ' must be quoted by another apostrophe.
         * @param objects the parameters for the string.
         * Mark occurrences in {@code text} with <code>{0}</code>, <code>{1}</code>, ...
         * @return the translated string.
         */
        String tr(String text, Object... objects);
    }

    /**
     * Registers translation adapter.
     * @param translationAdapter translation adapter
     */
    public static void registerTranslationAdapter(TranslationAdapter translationAdapter) {
        FeatureAdapter.translationAdapter = Objects.requireNonNull(translationAdapter);
    }

    /**
     * Translates a text using the current {@link TranslationAdapter}.
     * @param text the text to translate. Must be a string literal. (No constants or local vars.)
     * Can be broken over multiple lines. An apostrophe ' must be quoted by another apostrophe.
     * @param objects the parameters for the string.
     * Mark occurrences in {@code text} with <code>{0}</code>, <code>{1}</code>, ...
     * @return the translated string.
     */
    public static String tr(String text, Object... objects) {
        return translationAdapter.tr(text, objects);
    }
}
