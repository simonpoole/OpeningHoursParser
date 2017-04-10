package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

public enum Event {
	DAWN("dawn"),
	SUNRISE("sunrise"),
	DUSK("dusk"),
	SUNSET("sunset");
	
	private final String name;
	
	Event(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static Event getValue(String event) {
		for (Event e:Event.values()) {
			if (e.toString().equals(event)) {
				return e;
			}
		}
		return null;
	}
	
	public static List<String> nameValues() {
		List<String> result = new ArrayList<String>();
		for (Event e:values()) {
			result.add(e.toString());
		}
		return result;
	}
}
