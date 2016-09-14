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
public class MonthDayRange extends Element {
 
	DateWithOffset startDate = null;
	DateWithOffset endDate = null;
	int interval=0;

	public String toString() {
		StringBuilder b = new StringBuilder();
		if (startDate != null) {
			b.append(startDate.toString());
		} 
		if (endDate != null) {
			b.append("-");
			b.append(endDate.toString());
		}
		if (interval> 0) {
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
		MonthDayRange o = (MonthDayRange)other;
		if ((startDate == o.startDate  || (startDate != null && startDate.equals(o.startDate))) 
				&& (endDate == o.endDate  || (endDate != null && endDate.equals(o.endDate)))
				&& interval == o.interval){
			return true;
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
	 * @return the startDate
	 */
	public DateWithOffset getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public DateWithOffset getEndDate() {
		return endDate;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(DateWithOffset startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(DateWithOffset endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
}
