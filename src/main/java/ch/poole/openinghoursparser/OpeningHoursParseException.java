package ch.poole.openinghoursparser;

import org.jetbrains.annotations.Nullable;

import static ch.poole.openinghoursparser.I18n.tr;

/**
 * Represents an exception when parsing the opening hour string
 * @author JOSM team, Simon Legner
 */
public class OpeningHoursParseException extends ParseException {

    private final int line;
    private final int column;
    private String encountered = null;
    private String expected = null;

    OpeningHoursParseException(String message) {
        this(message, -1, -1);
    }

    OpeningHoursParseException(String message, @Nullable Token token) {
        this(message, token != null ? token.beginLine : -1, token != null ? token.beginColumn : -1);
    }

    private OpeningHoursParseException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    OpeningHoursParseException(ParseException ex) {
        this(null, ex.currentToken);
        this.currentToken = ex.currentToken;
        this.expectedTokenSequences = ex.expectedTokenSequences;
        this.tokenImage = ex.tokenImage;
        setEncounteredExpected();
    }

    /**
     * Returns the line number
     * @return the line number
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the column number
     * @return the column number
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String getMessage() {
        final String message = encountered == null || encountered.isEmpty()
                ? super.getMessage()
                : tr("exception_encountered", encountered);
        final String string = line >= 0 && column >= 0
                ? tr("exception_line_column", message, line, column)
                : message;
        final String appendix = expected == null || expected.isEmpty()
                ? ""
                : EOL + tr("exception_expecting", expected);
        return string + appendix;
    }

    private void setEncounteredExpected() {
        // localized ch.poole.openinghoursparser.ParseException.initialise
        StringBuilder expected = new StringBuilder();
        int maxSize = 0;
        for (int[] expectedTokenSequence : expectedTokenSequences) {
            if (maxSize < expectedTokenSequence.length) {
                maxSize = expectedTokenSequence.length;
            }
            for (int i : expectedTokenSequence) {
                expected.append(tokenImage[i]).append(' ');
            }
            if (expectedTokenSequence[expectedTokenSequence.length - 1] != 0) {
                expected.append("...");
            }
            expected.append(EOL).append("    ");
        }
        this.expected = expected.toString().trim();

        StringBuilder encountered = new StringBuilder();
        Token tok = currentToken.next;
        for (int i = 0; i < maxSize; i++) {
            if (i != 0) encountered.append(" ");
            if (tok.kind == 0) {
                encountered.append(tokenImage[0]);
                break;
            }
            encountered.append(" ").append(tokenImage[tok.kind]);
            encountered.append(" \"");
            encountered.append(add_escapes(tok.image));
            encountered.append(" \"");
            tok = tok.next;
        }
        this.encountered = encountered.toString();
    }
}
