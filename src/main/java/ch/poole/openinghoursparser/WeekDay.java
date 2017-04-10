package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

public enum WeekDay {
	MO("Mo"),
	TU("Tu"),
	WE("We"),
	TH("Th"),
	FR("Fr"),
	SA("Sa"),
	SU("Su");
	
	private final String name;
	
	WeekDay(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static WeekDay getValue(String day) {
		for (WeekDay w:WeekDay.values()) {
			if (w.toString().equals(day)) {
				return w;
			}
		}
		return null;
	}
	
	public static List<String> nameValues() {
		List<String> result = new ArrayList<String>();
		for (WeekDay w:values()) {
			result.add(w.toString());
		}
		return result;
	}
}
