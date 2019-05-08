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

public class DateWithOffset extends Element {

    private static final int MAX_NTH             = 5;
    public static final int  UNDEFINED_MONTH_DAY = Integer.MIN_VALUE;
    public static final int  MIN_MONTH_DAY       = 1;
    public static final int  MAX_MONTH_DAY       = 31;

    boolean openEnded             = false;
    int     year                  = YearRange.UNDEFINED_YEAR;
    Month   month                 = null;
    int     day                   = UNDEFINED_MONTH_DAY;
    WeekDay nthWeekDay            = null;
    int     nth                   = 0;
    boolean weekDayOffsetPositive = true;
    WeekDay weekDayOffset         = null;
    int     dayOffset             = 0;

    VarDate varDate = null;

    public DateWithOffset() {
        //empty
    }

    /**
     * Construct a new DateWithOffset with the same content
     * 
     * @param dwo original DateWithOffset
     */
    public DateWithOffset(DateWithOffset dwo) {
        openEnded = dwo.openEnded;
        year = dwo.year;
        month = dwo.month; // enum
        day = dwo.day;
        nthWeekDay = dwo.nthWeekDay; // enum
        nth = dwo.nth;
        weekDayOffsetPositive = dwo.weekDayOffsetPositive;
        weekDayOffset = dwo.weekDayOffset;
        dayOffset = dwo.dayOffset;
        varDate = dwo.varDate; // enum
    }

    /**
     * Check if we don't have a month day number or equivalent
     * 
     * @return true if the day is undefined
     */
    public boolean undefinedDay() {
        return day == UNDEFINED_MONTH_DAY && nthWeekDay == null && weekDayOffset == null && varDate == null;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (year != YearRange.UNDEFINED_YEAR) {
            b.append(year);
        }
        if (month != null) {
            if (year != YearRange.UNDEFINED_YEAR) {
                b.append(" ");
            }
            b.append(month);
        }
        if (nthWeekDay != null) {
            if (month != null) {
                b.append(" ");
            }
            b.append(nthWeekDay);
            b.append('[');
            b.append(nth);
            b.append(']');
        }
        if (day != UNDEFINED_MONTH_DAY) {
            if (year != YearRange.UNDEFINED_YEAR || month != null) {
                b.append(" ");
            }
            b.append(day);
        } else if (varDate != null) {
            if (year != YearRange.UNDEFINED_YEAR) {
                b.append(" ");
            }
            b.append(varDate);
        }
        // offsets
        if (weekDayOffset != null) {
            if (weekDayOffsetPositive) {
                b.append("+");
            } else {
                b.append("-");
            }
            b.append(weekDayOffset);
        }
        if (dayOffset != 0) {
            b.append(dayOffset > 0 ? " +" : " -");
            b.append(String.format("%d", Math.abs(dayOffset)));
            b.append(" day");
            if (Math.abs(dayOffset) > 1) {
                b.append("s");
            }
        }
        if (openEnded) {
            b.append("+");
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof DateWithOffset) {
            DateWithOffset o = (DateWithOffset) other;
            return openEnded == o.openEnded && year == o.year && (month == o.month || (month != null && month.equals(o.month)))
                    && (nthWeekDay == o.nthWeekDay || (nthWeekDay != null && nthWeekDay.equals(o.nthWeekDay))) && nth == o.nth && day == o.day
                    && weekDayOffsetPositive == o.weekDayOffsetPositive
                    && (weekDayOffset == o.weekDayOffset || (weekDayOffset != null && weekDayOffset.equals(o.weekDayOffset))) && dayOffset == o.dayOffset
                    && (varDate == o.varDate || (varDate != null && varDate.equals(o.varDate)));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (openEnded ? 0 : 1);
        result = 37 * result + year;
        result = 37 * result + (month == null ? 0 : month.hashCode());
        result = 37 * result + (nthWeekDay == null ? 0 : nthWeekDay.hashCode());
        result = 37 * result + nth;
        result = 37 * result + day;
        result = 37 * result + (weekDayOffsetPositive ? 0 : 1);
        result = 37 * result + (weekDayOffset == null ? 0 : weekDayOffset.hashCode());
        result = 37 * result + dayOffset;
        result = 37 * result + (varDate == null ? 0 : varDate.hashCode());
        return result;
    }

    /**
     * @return the openEnded
     */
    public boolean isOpenEnded() {
        return openEnded;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return the weekDayOffsetPositive
     */
    public boolean isWeekDayOffsetPositive() {
        return weekDayOffsetPositive;
    }

    /**
     * @return the weekDayOffset
     */
    public WeekDay getWeekDayOffset() {
        return weekDayOffset;
    }

    /**
     * @return the dayOffset
     */
    public int getDayOffset() {
        return dayOffset;
    }

    /**
     * @return the varDate
     */
    public VarDate getVarDate() {
        return varDate;
    }

    /**
     * @param varDate the varDate to set
     */
    public void setVarDate(VarDate varDate) {
        this.varDate = varDate;
    }

    /**
     * Set a variable date
     * 
     * @param date the var date
     */
    public void setVarDate(String date) {
        VarDate v = VarDate.getValue(date);
        if (v == null) {
            throw new IllegalArgumentException(v + " is not a valid VarDate");
        }
        this.varDate = v;
    }

    /**
     * @param openEnded the openEnded to set
     */
    public void setOpenEnded(boolean openEnded) {
        this.openEnded = openEnded;
    }

    /**
     * Set the year value
     * 
     * @param year the year to set
     */
    public void setYear(int year) {
        if (year != YearRange.UNDEFINED_YEAR && year < YearRange.FIRST_VALID_YEAR) {
            throw new IllegalArgumentException(year + " is earlier than " + YearRange.FIRST_VALID_YEAR);
        }
        this.year = year;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Set a month
     * 
     * @param month the month
     */
    public void setMonth(String month) {
        if (month == null || "".equals(month)) {
            this.month = null;
            return;
        }
        Month m = Month.getValue(month);
        if (m == null) {
            throw new IllegalArgumentException(m + " is not a valid Month");
        }
        this.month = m;
    }

    /**
     * Set the month day number
     * 
     * @param day the day to set
     */
    public void setDay(int day) {
        if (day != UNDEFINED_MONTH_DAY && (day < MIN_MONTH_DAY || day > MAX_MONTH_DAY)) {
            throw new IllegalArgumentException(day + " is not a valid month day number");
        }
        this.day = day;
    }

    /**
     * Set date specification via occurrence in month
     * 
     * @param day week day to use
     * @param nth the occurrence in the month positive number between -5 and 5, negative numbers are last occurrence
     *            (-1), before last (-2) and so on
     */
    public void setNth(String day, int nth) {
        setNth(WeekDay.getValue(day), nth);
    }

    /**
     * Set date specification via occurrence in month
     * 
     * @param day WeekDay to use
     * @param nth the occurrence in the month positive number between -5 and 5, negative numbers are last occurrence
     *            (-1), before last (-2) and so on
     */
    public void setNth(WeekDay day, int nth) {
        if (nth < -MAX_NTH || nth > MAX_NTH) {
            throw new IllegalArgumentException(nth + " is not a valid occurrence number");
        }
        nthWeekDay = day;
        this.nth = nth;
    }

    /**
     * Get the WeekDay for date specification via occurrence in month
     * 
     * @return the WeekDay or null if not set
     */
    public WeekDay getNthWeekDay() {
        return nthWeekDay;
    }

    /**
     * Get the occurrence value for date specification via occurrence in month
     * 
     * @return a integer between -5 and 5, valid only if the WeekDay has been set
     */
    public int getNth() {
        return nth;
    }

    /**
     * @param weekDayOffsetPositive the weekDayOffsetPositive to set
     */
    public void setWeekDayOffsetPositive(boolean weekDayOffsetPositive) {
        this.weekDayOffsetPositive = weekDayOffsetPositive;
    }

    /**
     * @param weekDayOffset the weekDayOffset to set
     */
    public void setWeekDayOffset(WeekDay weekDayOffset) {
        this.weekDayOffset = weekDayOffset;
    }

    /**
     * @param weekDayOffset the weekDayOffset to set
     */
    public void setWeekDayOffset(String weekDayOffset) {
        WeekDay w = WeekDay.getValue(weekDayOffset);
        if (w == null) {
            throw new IllegalArgumentException(day + " is not a valid WeekDay");
        }
        this.weekDayOffset = w;
    }

    /**
     * @param dayOffset the dayOffset to set
     */
    public void setDayOffset(int dayOffset) {
        this.dayOffset = dayOffset;
    }

    boolean isUndefined() {
        return year == YearRange.UNDEFINED_YEAR && month == null && day == DateWithOffset.UNDEFINED_MONTH_DAY && varDate == null;
    }

    @Override
    public DateWithOffset copy() {
        return new DateWithOffset(this);
    }
}
