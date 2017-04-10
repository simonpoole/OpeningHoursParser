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

public class WeekRange extends Element {
	/**
	 * Valid week number are from 1 to 53
	 */
	public static final int UNDEFINED_WEEK = Integer.MIN_VALUE;
	public static final int MIN_WEEK = 1;
	public static final int MAX_WEEK = 53;
	int startWeek = UNDEFINED_WEEK;
	int endWeek = UNDEFINED_WEEK;
	int interval = 0;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("%02d",startWeek));
		if (endWeek != UNDEFINED_WEEK) {
			b.append("-");
			b.append(String.format("%02d",endWeek));
			if (interval > 0) {
				b.append("/");
				b.append(interval);
			}
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other != null && other instanceof WeekRange) {
			WeekRange o = (WeekRange)other;
			if (startWeek == o.startWeek && endWeek == o.endWeek && interval == o.interval) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + startWeek;
		result = 37 * result + endWeek;
		result = 37 * result + interval;
		return result;
	}

	/**
	 * @return the startWeek
	 */
	public int getStartWeek() {
		return startWeek;
	}

	/**
	 * @return the endWeek
	 */
	public int getEndWeek() {
		return endWeek;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param start the startWeek to set
	 */
	public void setStartWeek(int start) {
		if (start != UNDEFINED_WEEK && (start < MIN_WEEK || start > MAX_WEEK)) {
			throw new IllegalArgumentException(start + " is outside of the 1-53 range");
		}
		this.startWeek = start;
	}

	/**
	 * @param end the endWeek to set
	 */
	public void setEndWeek(int end) {
		if (end != UNDEFINED_WEEK && (end < MIN_WEEK || end > MAX_WEEK)) {
			throw new IllegalArgumentException(startWeek + " is outside of the 1-53 range");
		}
		this.endWeek = end;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
}
