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
public class DateRange extends Element {

    DateWithOffset startDate = null;
    DateWithOffset endDate   = null;
    int            interval  = 0;

    public DateRange() {
    }

    /**
     * Construct a new DateRange with the same content
     * 
     * @param dr original DateRange
     */
    public DateRange(DateRange dr) {
        startDate = dr.startDate != null ? dr.startDate.copy() : null;
        endDate = dr.endDate != null ? dr.endDate.copy() : null;
        interval = dr.interval;
    }

    public String toString() {
        if (startDate == null) {
            throw new IllegalStateException("range must have a start date");
        }
        StringBuilder b = new StringBuilder();
        b.append(startDate.toString());

        if (endDate != null && !endDate.isUndefined()) {
            b.append("-");
            b.append(endDate.toString());
        }
        if (interval > 0) {
            b.append("/");
            b.append(interval);
        }
        return b.toString();
    }

    @Override
    public String toDebugString() {
        if (startDate == null) {
            throw new IllegalStateException("range must have a start date");
        }
        StringBuilder b = new StringBuilder();
        b.append(getClass().getSimpleName() + ":");
        b.append(startDate.toDebugString());

        if (endDate != null && !endDate.isUndefined()) {
            b.append("-");
            b.append(endDate.toDebugString());
        }
        if (interval > 0) {
            b.append("/");
            b.append(interval);
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null && other instanceof DateRange) {
            DateRange o = (DateRange) other;
            if ((startDate == o.startDate || (startDate != null && startDate.equals(o.startDate)))
                    && (endDate == o.endDate || (endDate != null && endDate.equals(o.endDate))) && interval == o.interval) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (startDate == null ? 0 : startDate.hashCode());
        result = 37 * result + (endDate == null ? 0 : endDate.hashCode());
        result = 37 * result + interval;
        return result;
    }

    /**
     * Get the start date of the range
     * 
     * @return the startDate
     */
    public DateWithOffset getStartDate() {
        if (startDate == null) {
            throw new IllegalStateException("range must have a start date");
        }
        return startDate;
    }

    /**
     * Get the end date of the range
     * 
     * @return the endDate, null if not present
     */
    public DateWithOffset getEndDate() {
        return endDate;
    }

    /**
     * Get the interval for the date range
     * 
     * Note: currently the opening hours specification is quiet on what units this is supposed to be in
     * 
     * @return the interval, 0 if no interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Set the start date of the range
     * 
     * @param startDate the startDate to set, can not be null
     */
    public void setStartDate(DateWithOffset startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("start date cannot be null");
        }
        this.startDate = startDate;
    }

    /**
     * Set the end date of the range
     * 
     * @param endDate the end date of the range to set, null if there is no end date
     */
    public void setEndDate(DateWithOffset endDate) {
        this.endDate = endDate;
    }

    /**
     * Set an interval for the date range
     * 
     * Note: currently the opening hours specification is quiet on what units this is supposed to be in
     * 
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public DateRange copy() {
        return new DateRange(this);
    }
}
