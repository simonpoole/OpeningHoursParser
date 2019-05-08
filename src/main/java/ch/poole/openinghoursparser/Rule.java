package ch.poole.openinghoursparser;

import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.NotNull;

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
public class Rule extends Element {
    boolean fallBack              = false;
    boolean additive              = false;
    boolean twentyfourseven       = false;
    boolean colonForClarification = false;

    String comment = null;
    // year range list
    List<YearRange> years = null;
    // week list
    List<WeekRange> weeks = null;
    // month day list
    List<DateRange> dates = null;
    // holiday list
    List<Holiday> holidays = null;
    // day list
    List<WeekDayRange> days = null;
    // times
    List<TimeSpan> times = null;
    // modifier
    RuleModifier modifier = null;

    /**
     * Default constructor
     */
    Rule() {
        // empty
    }

    /**
     * Copy constructor
     * 
     * @param r the rule to copy
     */
    public Rule(@NotNull Rule r) {
        fallBack = r.fallBack;
        additive = r.additive;
        twentyfourseven = r.twentyfourseven;
        colonForClarification = r.colonForClarification;
        comment = r.comment;
        years = Util.copyList(r.years);
        weeks = Util.copyList(r.weeks);
        dates = Util.copyList(r.dates);
        holidays = Util.copyList(r.holidays);
        days = Util.copyList(r.days);
        times = Util.copyList(r.times);
        modifier = r.modifier != null ? r.modifier.copy() : null;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (comment != null) {
            b.append("\"" + comment + "\":"); // we only accept comments in quotes so only print them out this way
        }
        if (twentyfourseven) {
            if (b.length() > 0) {
                b.append(" ");
            }
            b.append("24/7");
        } else {
            printList(false, true, b, "", years);
            printList(false, true, b, "week ", weeks);
            printList(false, true, b, "", dates);
            if (colonForClarification) {
                b.append(":");
            }
            printList(false, true, b, "", holidays);
            boolean holidaysAsWeekDays = false;
            if (holidays != null && !holidays.isEmpty() && holidays.get(holidays.size() - 1).getUseAsWeekDay() && days != null && !days.isEmpty()) {
                b.append(",");
                holidaysAsWeekDays = true;
            }
            printList(false, !holidaysAsWeekDays, b, "", days);
            printList(false, true, b, "", times);
        }
        if (modifier != null) {
            if (b.length() > 0) {
                b.append(" ");
            }
            b.append(modifier.toString());
        }
        return b.toString();
    }

    @Override
    public String toDebugString() {
        StringBuilder b = new StringBuilder();
        if (comment != null) {
            b.append("\"" + comment + "\":"); // we only accept comments in quotes so only print them out this way
        }
        if (twentyfourseven) {
            if (b.length() > 0) {
                b.append(" ");
            }
            b.append("24/7");
        } else {
            printList(true, true, b, "", years);
            printList(true, true, b, "week ", weeks);
            printList(true, true, b, "", dates);
            printList(true, true, b, "", holidays);
            boolean holidaysAsWeekDays = false;
            if (holidays != null && !holidays.isEmpty() && holidays.get(holidays.size() - 1).getUseAsWeekDay() && days != null && !days.isEmpty()) {
                b.append(",");
                holidaysAsWeekDays = true;
            }
            printList(true, !holidaysAsWeekDays, b, "", days);
            printList(true, true, b, "", times);
        }
        if (modifier != null) {
            if (b.length() > 0) {
                b.append(" ");
            }
            b.append(modifier.toDebugString());
        }
        return b.toString();
    }

    /**
     * Output a human readable list of objects
     * 
     * @param <T> the object class
     * @param debug if true produce debug output
     * @param addSpace add a space
     * @param b the StringBuilder to append to
     * @param prefix a prefix
     * @param list the list of objects
     */
    <T extends Element> void printList(boolean debug, boolean addSpace, @NotNull StringBuilder b, @NotNull String prefix, @NotNull List<T> list) {
        if (list != null) {
            if (addSpace && b.length() > 0) {
                b.append(" ");
            }
            b.append(prefix);
            Iterator<T> iter = list.iterator();
            while (iter.hasNext()) {
                b.append(debug ? iter.next().toDebugString() : iter.next().toString());
                if (iter.hasNext()) {
                    b.append(",");
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Rule) {
            Rule o = (Rule) other;
            return fallBack == o.fallBack && additive == o.additive && (comment == o.comment || (comment != null && comment.equals(o.comment)))
                    && twentyfourseven == o.twentyfourseven && (years == o.years || (years != null && years.equals(o.years)))
                    && (weeks == o.weeks || (weeks != null && weeks.equals(o.weeks))) && (dates == o.dates || (dates != null && dates.equals(o.dates)))
                    && (holidays == o.holidays || (holidays != null && holidays.equals(o.holidays)))
                    && (days == o.days || (days != null && days.equals(o.days))) && (times == o.times || (times != null && times.equals(o.times)))
                    && (modifier == o.modifier || (modifier != null && modifier.equals(o.modifier)));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (fallBack ? 0 : 1);
        result = 37 * result + (additive ? 0 : 1);
        result = 37 * result + (comment == null ? 0 : comment.hashCode());
        result = 37 * result + (twentyfourseven ? 0 : 1);
        result = 37 * result + (years == null ? 0 : years.hashCode());
        result = 37 * result + (weeks == null ? 0 : weeks.hashCode());
        result = 37 * result + (dates == null ? 0 : dates.hashCode());
        result = 37 * result + (holidays == null ? 0 : holidays.hashCode());
        result = 37 * result + (days == null ? 0 : days.hashCode());
        result = 37 * result + (times == null ? 0 : times.hashCode());
        result = 37 * result + (modifier == null ? 0 : modifier.hashCode());
        return result;
    }

    /**
     * Returns true if the input only differs in the days and times objects Current considers comments significant
     * 
     * @param r rule to test against
     * @return true if r can be merged with this rule
     */
    public boolean isMergeableWith(Rule r) {
        return this.equals(r) || (!twentyfourseven && ((comment == null && r.comment == null) || (comment != null && comment.equals(r.comment)))
                && ((years == null && r.years == null) || (years != null && years.equals(r.years)))
                && ((weeks == null && r.weeks == null) || (weeks != null && weeks.equals(r.weeks)))
                && ((dates == null && r.dates == null) || (dates != null && dates.equals(r.dates)))
                && ((modifier == null && r.modifier == null) || (modifier != null && modifier.equals(r.modifier))));
    }

    /**
     * @return the fallBack
     */
    public boolean isFallBack() {
        return fallBack;
    }

    /**
     * @return true if this rule does not overwrite rules for days with previous rules
     */
    public boolean isAdditive() {
        return additive;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return the twentyfourseven
     */
    public boolean isTwentyfourseven() {
        return twentyfourseven;
    }

    /**
     * @param twentyfourseven the twentyfourseven to set
     */
    public void setTwentyfourseven(boolean twentyfourseven) {
        this.twentyfourseven = twentyfourseven;
    }

    /**
     * @return the years
     */
    public List<YearRange> getYears() {
        return years;
    }

    /**
     * @return a List of WeekRange objects or null if none exist
     */
    public List<WeekRange> getWeeks() {
        return weeks;
    }

    /**
     * @return a List of DateRange objects or null if none exist
     */
    public List<DateRange> getDates() {
        return dates;
    }

    /**
     * @return the holidays
     */
    public List<Holiday> getHolidays() {
        return holidays;
    }

    /**
     * @return the days
     */
    public List<WeekDayRange> getDays() {
        return days;
    }

    /**
     * @return the times
     */
    public List<TimeSpan> getTimes() {
        return times;
    }

    /**
     * @return the modifier
     */
    public RuleModifier getModifier() {
        return modifier;
    }

    /**
     * @param fallBack set the fallback flag for this rule
     */
    public void setFallBack(boolean fallBack) {
        this.fallBack = fallBack;
    }

    /**
     * @param additive set the additive flag for this rule
     */
    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @param years the years to set
     */
    public void setYears(List<YearRange> years) {
        this.years = years;
    }

    /**
     * @param weeks the weeks to set
     */
    public void setWeeks(List<WeekRange> weeks) {
        this.weeks = weeks;
    }

    /**
     * @param monthdays the monthdays to set
     */
    public void setMonthdays(List<DateRange> monthdays) {
        this.dates = monthdays;
    }

    /**
     * @param holidays the holidays to set
     */
    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    /**
     * @param days the days to set
     */
    public void setDays(List<WeekDayRange> days) {
        this.days = days;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(List<TimeSpan> times) {
        this.times = times;
    }

    /**
     * @param modifier the modifier to set
     */
    public void setModifier(RuleModifier modifier) {
        this.modifier = modifier;
    }

    /**
     * Check if this rule actually contains something
     * 
     * @return true if empty
     */
    public boolean isEmpty() {
        // not done in one expression so that it remains legible
        if (twentyfourseven) {
            return false;
        }
        if (comment != null && !"".equals(comment)) {
            return false;
        }
        if (years != null && !years.isEmpty()) {
            return false;
        }
        if (weeks != null && !weeks.isEmpty()) {
            return false;
        }
        if (dates != null && !dates.isEmpty()) {
            return false;
        }
        if (holidays != null && !holidays.isEmpty()) {
            return false;
        }
        if (days != null && !days.isEmpty()) {
            return false;
        }
        if (times != null && !times.isEmpty()) {
            return false;
        }
        return modifier == null;
    }

    @Override
    public Rule copy() {
        return new Rule(this);
    }
}
