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

public class RuleModifier extends Element {
	String modifier = null;
	String comment = null;
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (modifier != null) {
			b.append(modifier);
		}
		if (comment != null) {
			if (modifier != null && modifier.length() > 0) {
				b.append(" ");
			}
			b.append(comment);
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		RuleModifier o = (RuleModifier)other;
		if ((modifier == o.modifier  || (modifier != null && modifier.equals(o.modifier))) 
				&& (comment == o.comment  || (comment != null && comment.equals(o.comment)))){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (modifier == null ? 0 : modifier.hashCode());
		result = 37 * result + (comment == null ? 0 : comment.hashCode());
		return result;
	}
}
