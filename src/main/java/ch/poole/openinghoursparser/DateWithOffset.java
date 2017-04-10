package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

import ch.poole.openinghoursparser.WeekDayRange.WeekDay;

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
 
public class DateWithOffset extends Element {
	public enum Month {
		JAN("Jan"),
		FEB("Feb"),
		MAR("Mar"),
		APR("Apr"),
		MAY("May"),
		JUN("Jun"),
		JUL("Jul"),
		AUG("Aug"),
		SEP("Sep"),
		OCT("Oct"),
		NOV("Nov"),
		DEC("Dec");
		
		private final String name;
		
		Month(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}

		public static Month getValue(String month) {
			for (Month m:Month.values()) {
				if (m.toString().equals(month)) {
					return m;
				}
			}
			return null;
		}
		
		public static List<String> nameValues() {
			List<String> result = new ArrayList<String>();
			for (Month m:values()) {
				result.add(m.toString());
			}
			return result;
		}
	}
	
	public enum VarDate {
		EASTER("easter");
		
		private final String name;
		
		VarDate(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}

		public static VarDate getValue(String varDate) {
			for (VarDate v:VarDate.values()) {
				if (v.toString().equals(varDate)) {
					return v;
				}
			}
			return null;
		}
		
		public static List<String> nameValues() {
			List<String> result = new ArrayList<String>();
			for (VarDate v:values()) {
				result.add(v.toString());
			}
			return result;
		}
	}
	
	public static final int UNDEFINED_MONTH_DAY = Integer.MIN_VALUE;
	public static final int MIN_MONTH_DAY = 1;
	public static final int MAX_MONTH_DAY = 31;
	
	boolean openEnded = false;
	int year = YearRange.UNDEFINED_YEAR;
	Month month = null;
	int day = UNDEFINED_MONTH_DAY;
	WeekDay weekDay = null;
	int nth = 0;
	boolean weekDayOffsetPositive=true;
	String weekDayOffset = null;
	int dayOffset = 0;
	
	VarDate varDate = null;
	
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
		if (day != UNDEFINED_MONTH_DAY) {
			if (year != YearRange.UNDEFINED_YEAR || month != null) {
				b.append(" ");
			}
			b.append(day);
		} else if (weekDay != null) {
			if (year != YearRange.UNDEFINED_YEAR || month != null) {
				b.append(" ");
			}
			b.append(weekDay);
			b.append("[");
			b.append(nth);
			b.append("]");
		} else if (varDate != null) {
			if (year != YearRange.UNDEFINED_YEAR ) {
				b.append(" ");
			}
			b.append(varDate);
		}
		// offsets
		if (weekDayOffset!= null) {
			if (weekDayOffsetPositive) {
				b.append("+");
			} else {
				b.append("-");
			}
			b.append(weekDayOffset);
		}
		if (dayOffset != 0) {
			b.append(dayOffset > 0 ? "+" : "-");
			b.append(Math.abs(dayOffset));
		}
		if (openEnded) {
			b.append("+");
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other != null && other instanceof DateWithOffset) {
			DateWithOffset o = (DateWithOffset)other;
			if (openEnded == o.openEnded && year == o.year 
					&& (month == o.month  || (month != null && month.equals(o.month))) 
					&& day == o.day 
					&& (weekDay == o.weekDay  || (weekDay != null && weekDay.equals(o.weekDay)))
					&& nth == o.nth
					&& weekDayOffsetPositive == o.weekDayOffsetPositive
					&& (weekDayOffset == o.weekDayOffset  || (weekDayOffset != null && weekDayOffset.equals(o.weekDayOffset)))
					&& dayOffset == o.dayOffset 
					&& (varDate == o.varDate  || (varDate != null && varDate.equals(o.varDate)))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (openEnded ? 0 : 1);
		result = 37 * result + year;
		result = 37 * result + (month == null ? 0 : month.hashCode());
		result = 37 * result + day;
		result = 37 * result + (weekDay == null ? 0 : weekDay.hashCode());
		result = 37 * result + nth;
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
	 * @return the weekDay
	 */
	public WeekDay getWeekDay() {
		return weekDay;
	}

	/**
	 * @return the nth
	 */
	public int getNth() {
		return nth;
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
	public String getWeekDayOffset() {
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
	 * @param the var date
	 */
	public void setVarDate(String date) {
		VarDate v = VarDate.getValue(date);
		if (v==null) {
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
		if (year < YearRange.FIRST_VALID_YEAR) {
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
	 * @param the month
	 */
	public void setMonth(String month) {
		Month m = Month.getValue(month);
		if (m==null) {
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
		if (day < MIN_MONTH_DAY || day > MAX_MONTH_DAY) {
			throw new IllegalArgumentException(day + " is not a valid month day number");
		}
		this.day = day;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}
	
	/**
	 * Set a week day
	 * 
	 * @param the day
	 */
	public void setWeekDay(String day) {
		WeekDay w = WeekDay.getValue(day);
		if (w==null) {
			throw new IllegalArgumentException(day + " is not a valid WeekDay");
		}
		this.weekDay = w;
	}

	/**
	 * @param nth the nth to set
	 */
	public void setNth(int nth) {
		this.nth = nth;
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
	public void setWeekDayOffset(String weekDayOffset) {
		this.weekDayOffset = weekDayOffset;
	}

	/**
	 * @param dayOffset the dayOffset to set
	 */
	public void setDayOffset(int dayOffset) {
		this.dayOffset = dayOffset;
	}
}
