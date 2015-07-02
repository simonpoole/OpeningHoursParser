import java.util.ArrayList;


class Rule {
	boolean fallBack = false;
	boolean replace = true;
	
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
				b.append(d.startDay.charAt(0));
				b.append(d.startDay.substring(1).toLowerCase());
				if (d.endDay != null) {
					b.append("-");
					b.append(d.endDay.charAt(0));
					b.append(d.endDay.substring(1).toLowerCase());
				}
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
