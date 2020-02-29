package ch.poole.openinghoursparser;

/**
 * Utility methods for ops on OH rules
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
import org.jetbrains.annotations.Nullable;

public final class Util {

    /**
     * Default constructor
     */
    private Util() {
        // dummy private constructor
    }

    /**
     * find rules that only differs in the days and times objects and can be merged for display puposes
     * 
     * @param rules rules to check
     * @return list of list of rules that can be merged
     */
    public static List<List<Rule>> getMergeableRules(List<Rule> rules) {

        List<List<Rule>> result = new ArrayList<>();

        List<Rule> copy = new ArrayList<>(rules); // shallow copy for bookkeeping

        while (!copy.isEmpty()) {
            Rule r = copy.get(0);
            boolean found = false;
            for (List<Rule> mergeables : result) {
                if (!mergeables.isEmpty() && r.isMergeableWith(mergeables.get(0))) {
                    mergeables.add(r);
                    found = true;
                    break;
                }
            }
            if (!found) {
                List<Rule> m = new ArrayList<>();
                m.add(r);
                result.add(m);
            }
            copy.remove(0);
        }
        return result;
    }

    /**
     * Generate an OH string from rules
     * 
     * @param rules rules to convert to an opening_hours string
     * @return specification conformant opening_hours string
     */
    public static String rulesToOpeningHoursString(@NotNull List<Rule> rules) {
        return rulesToOpeningHoursString(rules, false);
    }

    /**
     * Generate an OH string from rules
     * 
     * @param rules rules to convert to an opening_hours string
     * @param debug if true add debug output
     * @return opening_hours string
     */
    private static String rulesToOpeningHoursString(@NotNull List<Rule> rules, boolean debug) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Rule r : rules) {
            if (!r.isEmpty()) {
                if (!first) {
                    if (r.isAdditive()) {
                        result.append(", ");
                    } else if (r.isFallBack()) {
                        result.append(" || ");
                    } else {
                        result.append("; ");
                    }
                } else {
                    first = false;
                }
                result.append(debug ? r.toDebugString() : r.toString());
            }
        }
        return result.toString();
    }

    /**
     * Generate a debugging output string from rules
     * 
     * @param rules rules to convert to an opening_hours string
     * @return specification debugging opening_hours string
     */
    public static String rulesToOpeningHoursDebugString(@NotNull List<Rule> rules) {
        return rulesToOpeningHoursString(rules, true);
    }

    /**
     * Capitalize a string
     * 
     * @param s string to capitalize
     * @return capitalized string
     */
    public static String capitalize(String s) {
        char[] c = s.toLowerCase(Locale.US).toCharArray();
        if (c.length > 0) {
            c[0] = Character.toUpperCase(c[0]);
            return new String(c);
        }
        return s;
    }

    /**
     * Convert two letter German weekdays to our standard
     * 
     * @param s the String with the German weekday
     * @return the standard weekday string
     */
    @Nullable
    public static String deWeekDays2En(String s) {
        switch (s.toLowerCase(Locale.US)) {
        case "mo":
            return "Mo";
        case "di":
            return "Tu";
        case "mi":
            return "We";
        case "do":
            return "Th";
        case "fr":
            return "Fr";
        case "sa":
            return "Sa";
        case "so":
            return "Su";
        default:
            return null;
        }
    }

    /**
     * Copy a list, creating copies of its contents
     * 
     * @param <T> object class
     * @param l List to copy
     * @return deep copy of the List or null if it is null
     */
    @SuppressWarnings("unchecked")
    static <T extends Copy<?>> List<T> copyList(List<T> l) {
        if (l == null) {
            return null;
        }
        List<T> r = new ArrayList<>(l.size());
        for (T o : l) {
            r.add((T) o.copy());
        }
        return r;
    }

    /**
     * Make a safe comparison catching parse errors
     * 
     * @param token the current token
     * @param lower lower bounds
     * @return true if greater or equals to limit and is an int
     */
    static boolean equalsOrMore(@NotNull String token, int lower) {
        try {
            int temp = Integer.parseInt(token);
            return temp >= lower;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Make a safe comparison catching parse errors
     * 
     * @param token the current token
     * @param lower lower bounds
     * @param upper upper bounds
     * @return true if in bounds and is an int
     */
    static boolean between(@NotNull String token, int lower, int upper) {
        try {
            int temp = Integer.parseInt(token);
            return temp >= lower && temp <= upper;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     * @param a the first object
     * @param b the second object
     * @return true if the arguments are equal
     * @see java.util.Objects#equals
     */
    public static boolean equals(Object a, Object b) {
        // Objects.equals might not be available on Android
        return (a == b) || (a != null && a.equals(b));
    }

}
