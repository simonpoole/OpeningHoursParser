
public class Holiday {
	enum Type { PH, SH };
	Type type = null;
	int offset = 0;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (type != null) {
			b.append(type.toString());
			if (offset != 0) {
				if (offset > 0) {
					b.append("+");
				} else {
					b.append("-");
				}
				b.append(String.format("%d",Math.abs(offset)));
			}
		}
		return b.toString();		
	}
}
