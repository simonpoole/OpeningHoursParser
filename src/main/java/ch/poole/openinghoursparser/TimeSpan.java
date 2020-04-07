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
 *         CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE " OR THE USE OR OTHER
 *         DEALINGS IN THE SOFTWARE.
 */

public class TimeSpan extends Element {
    private static final int HOURS_24          = 1440;
    public static final int  UNDEFINED_TIME    = Integer.MIN_VALUE;
    public static final int  MIN_TIME          = 0;
    public static final int  MAX_TIME          = HOURS_24;
    public static final int  MAX_EXTENDED_TIME = 2880;

    int          start      = UNDEFINED_TIME;
    VariableTime startEvent = null;
    int          end        = UNDEFINED_TIME;
    VariableTime endEvent   = null;
    boolean      openEnded  = false;
    int          interval   = 0;             // minutes

    /**
     * Default constructor
     */
    public TimeSpan() {
        // empty
    }

    /**
     * Construct a new TimeSpan with the same contents
     * 
     * @param ts the original TimeSpan
     */
    public TimeSpan(@NotNull TimeSpan ts) {
        start = ts.start;
        startEvent = ts.startEvent != null ? ts.startEvent.copy() : null;
        end = ts.end;
        endEvent = ts.endEvent != null ? ts.endEvent.copy() : null;
        openEnded = ts.openEnded;
        interval = ts.interval;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (startEvent != null) {
            b.append(startEvent.toString());
        } else {
            b.append(String.format(Locale.US, "%02d", start / 60));
            b.append(":");
            b.append(String.format(Locale.US, "%02d", start % 60));
        }
        if (endEvent != null) {
            b.append("-");
            b.append(endEvent.toString());
        } else if (end != UNDEFINED_TIME) {
            b.append("-");
            // output as normal time if time span is less than 24 hours
            int tempEnd = start != UNDEFINED_TIME && (end - start) < HOURS_24 && end > HOURS_24 ? end - HOURS_24 : end;
            b.append(String.format(Locale.US, "%02d", tempEnd / 60));
            b.append(":");
            b.append(String.format(Locale.US, "%02d", end % 60));
        }
        if (openEnded) {
            b.append("+");
        }
        if (interval != 0) { // output only the full format
            b.append("/");
            b.append(String.format(Locale.US, "%02d", interval / 60));
            b.append(":");
            b.append(String.format(Locale.US, "%02d", interval % 60));
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof TimeSpan) {
            TimeSpan o = (TimeSpan) other;
            return start == o.start && Util.equals(startEvent, o.startEvent) && end == o.end && Util.equals(endEvent, o.endEvent) && openEnded == o.openEnded
                    && interval == o.interval;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + start;
        result = 37 * result + (startEvent == null ? 0 : startEvent.hashCode());
        result = 37 * result + end;
        result = 37 * result + (endEvent == null ? 0 : endEvent.hashCode());
        result = 37 * result + (openEnded ? 0 : 1);
        result = 37 * result + interval;
        return result;
    }

    /**
     * @return the start time in minutes
     */
    public int getStart() {
        return start;
    }

    /**
     * @return the startEvent
     */
    public VariableTime getStartEvent() {
        return startEvent;
    }

    /**
     * @return the end time in minutes
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return the endEvent
     */
    public VariableTime getEndEvent() {
        return endEvent;
    }

    /**
     * @return the openEnded
     */
    public boolean isOpenEnded() {
        return openEnded;
    }

    /**
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Set the start of range value
     * 
     * @param s the start value to set
     */
    public void setStart(int s) {
        if (s != UNDEFINED_TIME && (s < MIN_TIME || s > MAX_EXTENDED_TIME)) {
            throw new IllegalArgumentException(tr("invalid_time", s));
        }
        this.start = s;
    }

    /**
     * @param startEvent the startEvent to set
     */
    public void setStartEvent(VariableTime startEvent) {
        this.startEvent = startEvent;
    }

    /**
     * Set the end of range value
     * 
     * @param e the end value to set
     */
    public void setEnd(int e) {
        if (e != UNDEFINED_TIME && (e < MIN_TIME || e > MAX_EXTENDED_TIME)) {
            throw new IllegalArgumentException(tr("invalid_time", e));
        }
        this.end = e;
    }

    /**
     * @param endEvent the endEvent to set
     */
    public void setEndEvent(VariableTime endEvent) {
        this.endEvent = endEvent;
    }

    /**
     * @param openEnded the openEnded to set
     */
    public void setOpenEnded(boolean openEnded) {
        this.openEnded = openEnded;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public TimeSpan copy() {
        return new TimeSpan(this);
    }
}
