package ch.poole.openinghoursparser;

import org.jetbrains.annotations.NotNull;

import static ch.poole.openinghoursparser.I18n.tr;

import java.util.Locale;

/**
 * Container for objects from the opening_hours specification
 * 
 * @author Simon Poole
 *
 *         Copyright (c) 2015 Simon Poole
 *
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without
 *         limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *         Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 *         conditions:
 * 
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions
 *         of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *         TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *         THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *         CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *         DEALINGS IN THE SOFTWARE.
 */

public class YearRange extends Element {

    public static final int FIRST_VALID_YEAR = 1900;
    /**
     * years are defined as positive integers greater than 1900 (in the spec non-inclusive) however that might change
     * one day
     */
    public static final int UNDEFINED_YEAR   = Integer.MIN_VALUE;
    int                     startYear        = UNDEFINED_YEAR;
    int                     endYear          = UNDEFINED_YEAR;
    int                     interval         = 0;
    boolean                 openEnded        = false;

    /**
     * Default constructor
     */
    public YearRange() {
        // empty
    }

    /**
     * Construct a new YearRange with the same contents
     * 
     * @param yr original YearRange
     */
    public YearRange(@NotNull YearRange yr) {
        startYear = yr.startYear;
        endYear = yr.endYear;
        interval = yr.interval;
        openEnded = yr.openEnded;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(String.format(Locale.US, "%04d", startYear));
        if (endYear != UNDEFINED_YEAR) {
            b.append("-");
            b.append(String.format(Locale.US, "%04d", endYear));
            if (interval > 0) {
                b.append("/");
                b.append(interval);
            }
        } else if (openEnded) {
            b.append("+");
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof YearRange) {
            YearRange o = (YearRange) other;
            if (startYear == o.startYear && endYear == o.endYear && interval == o.interval && openEnded == o.openEnded) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + startYear;
        result = 37 * result + endYear;
        result = 37 * result + interval;
        result = 37 * result + (openEnded ? 0 : 1);
        return result;
    }

    /**
     * @return the startYear
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * @return the endYear
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Set the start of the year range
     * 
     * @param start the year to set
     */
    public void setStartYear(int start) {
        if (start != UNDEFINED_YEAR && start < FIRST_VALID_YEAR) {
            throw new IllegalArgumentException(tr("invalid_year_number", Integer.toString(start)));
        }
        this.startYear = start;
    }

    /**
     * Set the end of the year range
     * 
     * @param end the year to set
     */
    public void setEndYear(int end) {
        if (end != UNDEFINED_YEAR && end < FIRST_VALID_YEAR) {
            throw new IllegalArgumentException(tr("invalid_year_number", Integer.toString(end)));
        }
        this.endYear = end;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Check if this is open ended
     * 
     * @return true if open ended
     */
    public boolean isOpenEnded() {
        return openEnded;
    }

    @Override
    public YearRange copy() {
        return new YearRange(this);
    }

}
