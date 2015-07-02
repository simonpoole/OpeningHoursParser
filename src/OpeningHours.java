

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Container for objects from the opening_hours specification 
 * @author simon
 *
 */
public class OpeningHours {
	enum RuleModifier {
		OPEN,
		CLOSED,
		OFF,
		UNKNOWN
	}
		
			
	LinkedList<RuleSequence> timeDomain = new LinkedList<RuleSequence>();
	LinkedList<RuleSequence> fallbackGroup = new LinkedList<RuleSequence>();
	
	public OpeningHours() {
		
	}
	
	public OpeningHours(String openingHours) {

	}
	
	public List<RuleSequence> getRuleSequences() {
		return timeDomain;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (RuleSequence rs:timeDomain) {
			b.append(rs.toString());
			if (timeDomain.getLast()!=rs) {
				b.append(";");
			}
		}
		if (fallbackGroup.size() > 0) {
			b.append("||");
			for (RuleSequence rs:fallbackGroup) {
				b.append(rs.toString());
				if (fallbackGroup.getLast()!=rs) {
					b.append(";");
				}
			}
		}
		return b.toString();
	}
	
	public class RuleSequence {
		
		CalendarRange range = null;
		Rule rule = null;
		RuleModifier modifier = RuleModifier.OPEN;
		String comment = null;
		
		RuleSequence() {
			
		}
		
		RuleSequence(String ruleSequence) {

		}


		
		public String toString() {
			StringBuffer b = new StringBuffer();
			if (range != null) {
				
			}
			if (rule != null) {
				b.append(rule.toString());
			}
			if (modifier != RuleModifier.OPEN) {
				b.append(" ");
				b.append(modifier.toString().toLowerCase());
			}
			if (comment != null && comment.length() > 0) {
				b.append(" \"");
				b.append(comment);
				b.append("\"");
			}
			return b.toString();
		}
	}
	
	class CalendarRange {
		int startYear = -1;
		String startMonth = null;
		int startDay = 0;
		boolean startOffsetMinus = true;
		String startOffsetWeekday = null;
		int startOffsetDays = 0;
		
		int endYear = -1;
		String endMonth = null;
		int endDay = 0;
		boolean endOffsetMinus = true;
		String endOffsetWeekday = null;
		int endOffsetDays = 0;
		
		boolean openEnded = false;
		
		CalendarRange() {
			
		}
	}
	


}
