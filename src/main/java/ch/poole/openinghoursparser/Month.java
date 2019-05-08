package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

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
public enum Month {
    JAN("Jan"), FEB("Feb"), MAR("Mar"), APR("Apr"), MAY("May"), JUN("Jun"), JUL("Jul"), AUG("Aug"), SEP("Sep"), OCT("Oct"), NOV("Nov"), DEC("Dec");

    private static final int[] LASTDAY = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final String name;

    /**
     * Construct a new member of the enum
     * 
     * @param name the name as a String
     */
    Month(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the Month value for a String
     * 
     * @param month the month as a String
     * @return a Month or null
     */
    public static Month getValue(String month) {
        for (Month m : Month.values()) {
            if (m.toString().equals(month)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Get list of all values as Strings
     * 
     * @return a List with all values
     */
    public static List<String> nameValues() {
        List<String> result = new ArrayList<>();
        for (Month m : values()) {
            result.add(m.toString());
        }
        return result;
    }

    /**
     * Return the last day of the month
     * 
     * @param year the year 
     * @param month the month
     * @return the last day
     * @throws ParseException if no valid year is supplied and the month is February
     */
    public static int lastDay(int year, Month month) throws ParseException {
        if (month != FEB) {
            return LASTDAY[month.ordinal() + 1];
        } else {
            if (year == YearRange.UNDEFINED_YEAR) {
                throw new ParseException("Missing month day in date range for February");
            }
            if ((year / 4) * 4 == year && year / 100 * 100 != year) {
                return 29;
            } else {
                return 28;
            }
        }
    }
}
