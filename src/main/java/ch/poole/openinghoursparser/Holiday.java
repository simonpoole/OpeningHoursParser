package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

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
public class Holiday extends Element {
	public enum Type { PH, SH;
		
		public static List<String> nameValues() {
			List<String> result = new ArrayList<String>();
			for (Type t:values()) {
				result.add(t.toString());
			}
			return result;
		}	
	}
	
	Type type = null;
	int offset = 0;
	boolean useAsWeekDay = true;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (type != null) {
			b.append(type.toString());
			if (offset != 0) {
				b.append(offset > 0 ? " +" : " -");
				b.append(String.format("%d",Math.abs(offset)));
				b.append(" day");
				if (Math.abs(offset)>1) {
					b.append("s");
				}
			}
		}
		return b.toString();		
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other != null && other instanceof Holiday) {
			Holiday o = (Holiday)other;
			if ((type == o.type  || (type != null && type.equals(o.type))) 
					&& offset == o.offset){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (type == null ? 0 : type.hashCode());
		result = 37 * result + offset;
		return result;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setUseAsWeekDay(boolean yes) {
		useAsWeekDay = yes;
	}
	
	public boolean getUseAsWeekDay() {
		return useAsWeekDay;
	}
}
