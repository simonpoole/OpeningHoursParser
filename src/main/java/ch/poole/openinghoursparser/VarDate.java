package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

public enum VarDate {
	EASTER("easter");
	
	private final String name;
	
	VarDate(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static VarDate getValue(String varDate) {
		for (VarDate v:VarDate.values()) {
			if (v.toString().equals(varDate)) {
				return v;
			}
		}
		return null;
	}
	
	public static List<String> nameValues() {
		List<String> result = new ArrayList<String>();
		for (VarDate v:values()) {
			result.add(v.toString());
		}
		return result;
	}
}

