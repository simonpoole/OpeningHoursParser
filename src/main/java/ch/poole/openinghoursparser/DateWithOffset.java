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
 
public class DateWithOffset extends Element {
	boolean openEnded = false;
	int year = 0;
	String month = null;
	int day = -1;
	String weekDay = null;
	int nth = 0;
	boolean weekDayOffsetPositive=true;
	String weekDayOffset = null;
	int dayOffset = 0;
	
	String varDate = null;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (year != 0) {
			b.append(year);
		}
		if (month != null) {
			if (year != 0) {
				b.append(" ");
			}
			b.append(month);
		}
		if (day > 0) {
			if (year != 0 || month != null) {
				b.append(" ");
			}
			b.append(day);
		} else if (weekDay != null) {
			if (year != 0 || month != null) {
				b.append(" ");
			}
			b.append(weekDay);
			b.append("[");
			b.append(nth);
			b.append("]");
		} else if (varDate != null) {
			if (year != 0) {
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
	public String getMonth() {
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
	public String getWeekDay() {
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
	public String getVarDate() {
		return varDate;
	}

	/**
	 * @param openEnded the openEnded to set
	 */
	public void setOpenEnded(boolean openEnded) {
		this.openEnded = openEnded;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
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

	/**
	 * @param varDate the varDate to set
	 */
	public void setVarDate(String varDate) {
		this.varDate = varDate;
	}
}
