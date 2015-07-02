import java.util.ArrayList;


class WeekDayRange {
	String startDay = null;
	String endDay = null;
	ArrayList<Nth> nths = new ArrayList<Nth>();

	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(startDay);
		if (endDay != null) {
			b.append("-");
			b.append(endDay);
		} else if (nths != null) {
			b.append("["); 
			for (Nth n:nths) {
				b.append(n.toString());
				if (!n.equals(nths.get(nths.size()-1))) {
					b.append(",");
				}
			}
			
			b.append("]");
		}
		return b.toString();
	}
}
