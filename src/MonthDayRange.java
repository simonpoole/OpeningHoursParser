
class MonthDayRange {
 
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
		return b.toString();
	}
}
