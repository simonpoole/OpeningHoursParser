package ch.poole.openinghoursparser;
/**
 * Container for objects from the opening_hours specification
 * @author Simon Poole
 *
 * Copyright (c) 2015 Simon Poole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 " OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
import java.util.ArrayList;

public class Rule extends Element {
	boolean fallBack = false;
	boolean replace = true;

	String comment = null;
	// year range list
    ArrayList<YearRange> years = null;
	// week list
    ArrayList<WeekRange> weeks = null;
	// month day list
	ArrayList<MonthDayRange> monthdays = null;
	// holiday list
	ArrayList<Holiday> holidays = null;
	// day list
	ArrayList<WeekDayRange> days = null;
	// times
	ArrayList<TimeSpan> times = null;
	// modifier
	RuleModifier modifier = null;

	Rule() {

	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		// FIXME might want to write out fallBack and replace flags too
		if (comment != null) {
			b.append(comment + ": ");
		}	
		if (years != null) {
			for (YearRange yr:years) {
				b.append(yr.toString());
				if (years.get(years.size()-1)!=yr) {
					b.append(",");
				} 
			}
		}
		if (weeks != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			b.append("week ");
			for (WeekRange wr:weeks) {
				b.append(wr.toString());
				if (weeks.get(weeks.size()-1)!=wr) {
					b.append(",");
				} 
			}
		}
		if (monthdays != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			for (MonthDayRange mdr:monthdays) {
				b.append(mdr.toString());
				if (monthdays.get(monthdays.size()-1)!=mdr) {
					b.append(",");
				} 
			}
		}
		if (holidays != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			for (Holiday h:holidays) {
				b.append(h.toString());
				if (holidays.get(holidays.size()-1)!=h) {
					b.append(",");
				} 
			}
		}
		if (days != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			for (WeekDayRange d:days) {
				b.append(d.toString());
				if (days.get(days.size()-1)!=d) {
					b.append(",");
				} 
			}
		}
		if (times != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			for (TimeSpan ts:times) {
				b.append(ts.toString());
				if (times.get(times.size()-1)!=ts) {
					b.append(",");
				}
			}
		}
		if (modifier != null) {
			b.append(" ");
			b.append(modifier.toString());
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		Rule o = (Rule)other;
		if (fallBack == o.fallBack  && replace == o.replace 
				&& (comment == o.comment  || (comment != null && comment.equals(o.comment)))
				&& (years == o.years  || (years != null && years.equals(o.years)))
				&& (weeks == o.weeks  || (weeks != null && weeks.equals(o.weeks)))
				&& (monthdays == o.monthdays  || (monthdays != null && monthdays.equals(o.monthdays)))
				&& (holidays == o.holidays  || (holidays != null && holidays.equals(o.holidays)))
				&& (days == o.days  || (days != null && days.equals(o.days)))
				&& (times == o.times  || (times != null && times.equals(o.times)))
				&& (modifier == o.modifier  || (modifier != null && modifier.equals(o.modifier)))){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * (fallBack ? 0 : 1);
		result = 37 * (replace ? 0 : 1);
		result = 37 * result + (comment == null ? 0 : comment.hashCode());
		result = 37 * result + (years == null ? 0 : years.hashCode());
		result = 37 * result + (weeks == null ? 0 : weeks.hashCode());
		result = 37 * result + (monthdays == null ? 0 : monthdays.hashCode());
		result = 37 * result + (holidays == null ? 0 : holidays.hashCode());
		result = 37 * result + (days == null ? 0 : days.hashCode());
		result = 37 * result + (times == null ? 0 : times.hashCode());
		result = 37 * result + (modifier == null ? 0 : modifier.hashCode());
		return result;
	}
	
	/**
	 * Returns true if the input only differs in the days and times objects
	 * Current considers comments significant
	 * @param o
	 * @return
	 */
	public boolean isMergeableWith(Rule o) {
		if (this == o) {
			return true;
		}		
		if ((comment == o.comment  || (comment != null && comment.equals(o.comment)))
			&& (years == o.years  || (years != null && years.equals(o.years)))
			&& (weeks == o.weeks  || (weeks != null && weeks.equals(o.weeks)))
			&& (monthdays == o.monthdays  || (monthdays != null && monthdays.equals(o.monthdays)))
			&& (modifier == o.modifier  || (modifier != null && modifier.equals(o.modifier)))){
			return true;
		}
		return false;
	}
	
	/**
	 * @return the fallBack
	 */
	public boolean isFallBack() {
		return fallBack;
	}

	/**
	 * @return the replace
	 */
	public boolean isReplace() {
		return replace;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the years
	 */
	public ArrayList<YearRange> getYears() {
		return years;
	}

	/**
	 * @return the weeks
	 */
	public ArrayList<WeekRange> getWeeks() {
		return weeks;
	}

	/**
	 * @return the monthdays
	 */
	public ArrayList<MonthDayRange> getMonthdays() {
		return monthdays;
	}

	/**
	 * @return the holidays
	 */
	public ArrayList<Holiday> getHolidays() {
		return holidays;
	}

	/**
	 * @return the days
	 */
	public ArrayList<WeekDayRange> getDays() {
		return days;
	}

	/**
	 * @return the times
	 */
	public ArrayList<TimeSpan> getTimes() {
		return times;
	}

	/**
	 * @return the modifier
	 */
	public RuleModifier getModifier() {
		return modifier;
	}

	/**
	 * @param fallBack the fallBack to set
	 */
	public void setFallBack(boolean fallBack) {
		this.fallBack = fallBack;
	}

	/**
	 * @param replace the replace to set
	 */
	public void setReplace(boolean replace) {
		this.replace = replace;
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
	public void setYears(ArrayList<YearRange> years) {
		this.years = years;
	}

	/**
	 * @param weeks the weeks to set
	 */
	public void setWeeks(ArrayList<WeekRange> weeks) {
		this.weeks = weeks;
	}

	/**
	 * @param monthdays the monthdays to set
	 */
	public void setMonthdays(ArrayList<MonthDayRange> monthdays) {
		this.monthdays = monthdays;
	}

	/**
	 * @param holidays the holidays to set
	 */
	public void setHolidays(ArrayList<Holiday> holidays) {
		this.holidays = holidays;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(ArrayList<WeekDayRange> days) {
		this.days = days;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(ArrayList<TimeSpan> times) {
		this.times = times;
	}

	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(RuleModifier modifier) {
		this.modifier = modifier;
	}
}
