
public class VariableTime {
	String event = null;
	int offset = 0;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (event != null) {
			b.append(event);
			if (offset != 0) {
				if (offset > 0) {
					b.append("+");
				} 
				b.append(String.format("%02d",(int)offset/60));
				b.append(":");
				b.append(String.format("%02d",Math.abs(offset)%60));
			}
		}
		return b.toString();		
	}
}
