package ch.poole.openinghoursparser;
/**
 * Container for objects from the opening_hours specification 
 * @author Simon Poole
 *
 * Copyright (c) 2015 Simon Poole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 " OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
