package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

public enum Month {
	JAN("Jan"),
	FEB("Feb"),
	MAR("Mar"),
	APR("Apr"),
	MAY("May"),
	JUN("Jun"),
	JUL("Jul"),
	AUG("Aug"),
	SEP("Sep"),
	OCT("Oct"),
	NOV("Nov"),
	DEC("Dec");
	
	private final String name;
	
	Month(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static Month getValue(String month) {
		for (Month m:Month.values()) {
			if (m.toString().equals(month)) {
				return m;
			}
		}
		return null;
	}
	
	public static List<String> nameValues() {
		List<String> result = new ArrayList<String>();
		for (Month m:values()) {
			result.add(m.toString());
		}
		return result;
	}
}
