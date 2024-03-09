package ch.poole.openinghoursparser;
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
 *         CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE " OR THE USE OR OTHER
 *         DEALINGS IN THE SOFTWARE.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jetbrains.annotations.NotNull;

public class WeekDayRange extends Element {

    WeekDay   startDay = null;
    WeekDay   endDay   = null;
    List<Nth> nths     = null;
    int       offset   = 0;

    /**
     * Default constructor
     */
    public WeekDayRange() {
        // empty
    }

    /**
     * Construct a new WeekDayRange with the same contents
     * 
     * @param wdr original WeekDayRange
     */
    public WeekDayRange(@NotNull WeekDayRange wdr) {
        startDay = wdr.startDay; // enum
        endDay = wdr.endDay; // wdr
        nths = (wdr.nths != null ? Util.copyList(wdr.nths) : null);
        offset = wdr.offset;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(startDay);
        if (endDay != null) {
            b.append("-");
            b.append(endDay);
        } else if (nths != null && !nths.isEmpty()) {
            b.append("[");
            for (Nth n : nths) {
                b.append(n.toString());
                if (!n.equals(nths.get(nths.size() - 1))) {
                    b.append(",");
                }
            }
            b.append("]");
            if (offset != 0) {
                if (offset > 0) {
                    b.append(" +");
                } else {
                    b.append(" -");
                }
                b.append(String.format(Locale.US, "%d", Math.abs(offset)));
                b.append(" day");
                if (Math.abs(offset) > 1) {
                    b.append("s");
                }
            }
        }
        return b.toString();
    }

    @Override
    public String toDebugString() {
        return getClass().getSimpleName() + ":" + toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof WeekDayRange) {
            WeekDayRange o = (WeekDayRange) other;
            return Util.equals(startDay, o.startDay)
                    && Util.equals(endDay, o.endDay)
                    && Util.equals(nths, o.nths)
                    && offset == o.offset;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (startDay == null ? 0 : startDay.hashCode());
        result = 37 * result + (endDay == null ? 0 : endDay.hashCode());
        result = 37 * result + (nths == null ? 0 : nths.hashCode());
        result = 37 * result + offset;
        return result;
    }

    /**
     * @return the startDay
     */
    public WeekDay getStartDay() {
        return startDay;
    }

    /**
     * @return the endDay
     */
    public WeekDay getEndDay() {
        return endDay;
    }

    /**
     * @return the nths
     */
    public List<Nth> getNths() {
        return nths;
    }

    /**
     * Set the day the range starts on
     * 
     * @param day the day to set
     */
    public void setStartDay(WeekDay day) {
        this.startDay = day;
    }

    /**
     * Set the day the range starts on
     * 
     * @param day the day to set
     */
    public void setStartDay(String day) {
        if (day == null || "".equals(day)) {
            this.startDay = null;
            return;
        }
        this.startDay = WeekDay.getValue(day);
    }

    /**
     * Set the day the range ends on
     * 
     * @param day the day to set
     */
    public void setEndDay(WeekDay day) {
        this.endDay = day;
    }

    /**
     * Set the day the range ends on
     * 
     * @param day the day to set
     */
    public void setEndDay(String day) {
        this.endDay = WeekDay.getValue(day);
    }

    /**
     * @param nths the nths to set
     */
    public void setNths(List<Nth> nths) {
        this.nths = nths;
    }

    /**
     * Add a occurrence in the month for a week day
     * 
     * @param nth the n'th occurrence
     */
    public void add(Nth nth) {
        if (nths == null) {
            nths = new ArrayList<>();
        }
        nths.add(nth);
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public WeekDayRange copy() {
        return new WeekDayRange(this);
    }
}
