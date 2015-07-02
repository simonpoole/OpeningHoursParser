
public class Nth {
	int startNth=-1;
	int endNth=-1;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		if (startNth != -1) {
			b.append(startNth);
		}
		if (endNth != -1) {
			b.append("-");
			b.append(endNth);
		}
		return b.toString();
	}
}
