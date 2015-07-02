
public class RuleModifier {
	String modifier = null;
	String comment = null;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (modifier != null) {
			b.append(modifier);
		}
		if (comment != null) {
			if (modifier != null) {
				b.append(" ");
			}
			b.append(comment);
		}
		return b.toString();
	}
}
