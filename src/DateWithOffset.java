
public class DateWithOffset {
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
}
