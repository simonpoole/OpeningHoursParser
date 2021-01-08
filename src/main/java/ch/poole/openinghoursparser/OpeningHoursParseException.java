package ch.poole.openinghoursparser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ch.poole.openinghoursparser.I18n.tr;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an exception when parsing the opening hour string
 * 
 * @author JOSM team, Simon Legner
 * @author Simon Poole
 */
public class OpeningHoursParseException extends ParseException {

    private static final long serialVersionUID = 1L;

    private final int                        line;
    private final int                        column;
    private String                           encountered = null; // NOSONAR
    private String                           expected    = null; // NOSONAR
    private List<OpeningHoursParseException> exceptions  = null; // NOSONAR

    private static String EOL = System.getProperty("line.separator", "\n");

    /**
     * Construct a new exception from an optional message
     * 
     * @param message the message or null
     */
    OpeningHoursParseException(@Nullable String message) {
        this(message, -1, -1);
    }

    /**
     * Create a new exception from a message and the token that caused this exception to be thrown
     * 
     * @param message an optional message
     * @param token an optional token
     */
    OpeningHoursParseException(@Nullable String message, @Nullable Token token) {
        this(message, token != null ? token.beginLine : -1, token != null ? token.beginColumn : -1);
    }

    /**
     * Construct a new exception from an optional message and indication of where the error was
     * 
     * @param message an optional message
     * @param line the line where the exceptions was thrown
     * @param column the column where the exception was thrown
     */
    private OpeningHoursParseException(@Nullable String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    /**
     * Construct a new exception from an existing ParseException
     * 
     * @param ex a ParseException
     */
    OpeningHoursParseException(@NotNull ParseException ex) {
        this(null, ex.currentToken);
        this.currentToken = ex.currentToken;
        this.expectedTokenSequences = ex.expectedTokenSequences;
        this.tokenImage = ex.tokenImage;
        setEncounteredExpected();
    }

    /**
     * Construct a new exception from a List of existing ParseExceptions
     * 
     * @param exceptions a List of ParseExceptions
     */
    OpeningHoursParseException(@NotNull List<ParseException> exceptionList) {
        this(!exceptionList.isEmpty() ? exceptionList.get(0).getMessage() : null, null);
        if (exceptionList.isEmpty()) {
            throw new IllegalArgumentException("exceptions is empty");
        }
        exceptions = new ArrayList<>();
        for (ParseException ex : exceptionList) {
            if (ex instanceof OpeningHoursParseException) {
                exceptions.add((OpeningHoursParseException) ex);
            } else {
                exceptions.add(new OpeningHoursParseException(ex));
            }
        }
    }

    /**
     * If we have multiple exceptions return the first one
     * 
     * @return the first exception or null
     */
    @Nullable
    private OpeningHoursParseException getFirstException() {
        if (exceptions != null && !exceptions.isEmpty()) {
            return exceptions.get(0);
        }
        return null;
    }

    /**
     * Returns the line number
     * 
     * @return the line number
     */
    public int getLine() {
        OpeningHoursParseException first = getFirstException();
        if (first != null) {
            return first.getLine();
        }
        return line;
    }

    /**
     * Returns the column number
     * 
     * @return the column number
     */
    public int getColumn() {
        OpeningHoursParseException first = getFirstException();
        if (first != null) {
            return first.getColumn();
        }
        return column;
    }

    @Override
    public String getMessage() {
        OpeningHoursParseException first = getFirstException();
        if (first != null) {
            return first.getMessage();
        }
        final String message = encountered == null || encountered.isEmpty() ? super.getMessage() : tr("exception_encountered", encountered);
        final String string = line >= 0 && column >= 0 ? tr("exception_line_column", message, line, column) : message;
        final String appendix = expected == null || expected.isEmpty() ? "" : EOL + tr("exception_expecting", expected);
        return string + appendix;
    }

    /**
     * Get a list of the exceptions that caused this to be thrown
     * 
     * @return a list of OpeningHoursParseException
     */
    @NotNull
    public List<OpeningHoursParseException> getExceptions() {
        if (exceptions != null) {
            return exceptions;
        }
        List<OpeningHoursParseException> ex = new ArrayList<>();
        ex.add(this);
        return ex;
    }

    /**
     * Create a localized message from the token information
     */
    private void setEncounteredExpected() {
        // localized ch.poole.openinghoursparser.ParseException.initialise
        if (expectedTokenSequences == null) {
            return;
        }
        StringBuilder expectedBuilder = new StringBuilder();
        int maxSize = 0;
        for (int[] expectedTokenSequence : expectedTokenSequences) {
            if (maxSize < expectedTokenSequence.length) {
                maxSize = expectedTokenSequence.length;
            }
            for (int i : expectedTokenSequence) {
                expectedBuilder.append(tokenImage[i]).append(' ');
            }
            if (expectedTokenSequence[expectedTokenSequence.length - 1] != 0) {
                expectedBuilder.append("...");
            }
            expectedBuilder.append(EOL).append("    ");
        }
        expected = expectedBuilder.toString().trim();

        StringBuilder encounteredBuilder = new StringBuilder();
        Token tok = currentToken.next;
        for (int i = 0; i < maxSize; i++) {
            if (i != 0)
                encounteredBuilder.append(" ");
            if (tok.kind == 0) {
                encounteredBuilder.append(tokenImage[0]);
                break;
            }
            encounteredBuilder.append(" ").append(tokenImage[tok.kind]);
            encounteredBuilder.append(" \"");
            encounteredBuilder.append(add_escapes(tok.image));
            encounteredBuilder.append(" \"");
            tok = tok.next;
        }
        encountered = encounteredBuilder.toString();
    }
}
