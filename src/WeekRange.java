
public class WeekRange {
	int startWeek = -1;
	int endWeek = -1;
	int interval = 0;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("%02d",startWeek));
		if (endWeek > -1) {
			b.append("-");
			b.append(String.format("%02d",endWeek));
			if (interval > 0) {
				b.append("/");
				b.append(interval);
			}
		}
		return b.toString();
	}
}
