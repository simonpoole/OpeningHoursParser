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

class Rule {
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
		if (comment != null) {
			b.append(comment + ": ");
		}	
		if (years != null) {
			for (YearRange yr:years) {
				b.append(yr.toString());
				if (years.get(years.size()-1)!=yr) {
					b.append(",");
				} else {
					b.append(" ");
				}
			}
		}
		if (weeks != null) {
			b.append("week ");
			for (WeekRange wr:weeks) {
				b.append(wr.toString());
				if (weeks.get(weeks.size()-1)!=wr) {
					b.append(",");
				} else {
					b.append(" ");
				}
			}
		}
		if (monthdays != null) {
			for (MonthDayRange mdr:monthdays) {
				b.append(mdr.toString());
				if (monthdays.get(monthdays.size()-1)!=mdr) {
					b.append(",");
				} else {
					b.append(" ");
				}
			}
		}
		if (holidays != null) {
			for (Holiday h:holidays) {
				b.append(h.toString());
				if (holidays.get(holidays.size()-1)!=h) {
					b.append(",");
				} else {
					b.append(" ");
				}
			}
		}
		if (days != null) {
			for (WeekDayRange d:days) {
				b.append(d.toString());
				if (days.get(days.size()-1)!=d) {
					b.append(",");
				} else {
					b.append(" ");
				}
			}
		}
		if (times != null) {
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
}
