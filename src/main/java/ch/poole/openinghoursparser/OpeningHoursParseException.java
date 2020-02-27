package ch.poole.openinghoursparser;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an exception when parsing the opening hour string
 */
public class OpeningHoursParseException extends ParseException {

    private final int line;
    private final int column;

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
        if (line >= 0 && column >= 0) {
            return super.getMessage() + " at line " + line + ", column " + column;
        } else {
            return super.getMessage();
        }
    }
}
