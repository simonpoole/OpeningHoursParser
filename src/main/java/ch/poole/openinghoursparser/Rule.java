package ch.poole.openinghoursparser;
import java.util.Iterator;
import java.util.List;

public class Rule extends Element {
	boolean fallBack = false;
	boolean replace = true;
	boolean twentyfourseven = false;

	String comment = null;
	// year range list
    List<YearRange> years = null;
	// week list
    List<WeekRange> weeks = null;
	// month day list
	List<MonthDayRange> monthdays = null;
	// holiday list
	List<Holiday> holidays = null;
	// day list
	List<WeekDayRange> days = null;
	// times
	List<TimeSpan> times = null;
	// modifier
	RuleModifier modifier = null;

	Rule() {
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
			printList(b, "", years);
			printList(b, "week ", weeks);
			printList(b, "", monthdays);
			printList(b, "", holidays);
			printList(b, "", days);
			printList(b, "", times);
		}
		if (modifier != null) {
			b.append(" ");
			b.append(modifier.toString());
		}
		return b.toString();
	}
	
	<T> void printList(StringBuilder b, String prefix, List<T>list) {
		if (list != null) {
			if (b.length() > 0) {
				b.append(" ");
			}
			b.append(prefix);
			Iterator<T> iter = list.iterator();
			while (iter.hasNext()) {
				b.append(iter.next().toString());
				if (iter.hasNext()) {
					b.append(",");
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Rule) {
			Rule o = (Rule)other;
			return fallBack == o.fallBack  
					&& replace == o.replace 
					&& (comment == o.comment  || (comment != null && comment.equals(o.comment)))
					&& twentyfourseven == o.twentyfourseven 
					&& (years == o.years  || (years != null && years.equals(o.years)))
					&& (weeks == o.weeks  || (weeks != null && weeks.equals(o.weeks)))
					&& (monthdays == o.monthdays  || (monthdays != null && monthdays.equals(o.monthdays)))
					&& (holidays == o.holidays  || (holidays != null && holidays.equals(o.holidays)))
					&& (days == o.days  || (days != null && days.equals(o.days)))
					&& (times == o.times  || (times != null && times.equals(o.times)))
					&& (modifier == o.modifier  || (modifier != null && modifier.equals(o.modifier)));
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (fallBack ? 0 : 1);
		result = 37 * result + (replace ? 0 : 1);
		result = 37 * result + (comment == null ? 0 : comment.hashCode());
		result = 37 * result + (twentyfourseven ? 0 : 1);
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
	 * @param r rule to test against
	 * @return true if r can be merged with this rule
	 */
	public boolean isMergeableWith(Rule r) {
		return this.equals(r) 
				|| (!twentyfourseven
				&& ((comment == null && r.comment == null) || (comment != null && comment.equals(r.comment)))
				&& ((years == null && r.years == null) || (years != null && years.equals(r.years)))
				&& ((weeks == null && r.weeks == null) || (weeks != null && weeks.equals(r.weeks)))
				&& ((monthdays == null && r.monthdays == null) || (monthdays != null && monthdays.equals(r.monthdays)))
				&& ((modifier == null && r.modifier == null) || (modifier != null && modifier.equals(r.modifier))));
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
	 * @return the weeks
	 */
	public List<WeekRange> getWeeks() {
		return weeks;
	}

	/**
	 * @return the monthdays
	 */
	public List<MonthDayRange> getMonthdays() {
		return monthdays;
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
	public void setMonthdays(List<MonthDayRange> monthdays) {
		this.monthdays = monthdays;
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
		if (monthdays != null && !monthdays.isEmpty()) {
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
}
