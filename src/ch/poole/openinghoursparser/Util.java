package ch.poole.openinghoursparser;
/**
 * Utility methods for ops on OH rules
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


public class Util {
	/**
	 * find rules that only differs in the days and times objects and can be merged for display puposes
	 * @param rules
	 * @return
	 */
	public static ArrayList<ArrayList<Rule>> getMergeableRules(ArrayList<Rule> rules) {
		
		ArrayList<ArrayList<Rule>> result = new ArrayList<ArrayList<Rule>>();
		
		ArrayList<Rule> copy = new ArrayList<Rule>(rules); // shallow copy for bookkeeping
		
		while (copy.size() > 0) {
			Rule r = copy.get(0);
			boolean found = false;
			for (ArrayList<Rule> mergeables:result) {
				if (mergeables.size()>0) {
					if (r.isMergeableWith(mergeables.get(0))) {
						mergeables.add(r);
						found = true;
						break;
					}
				}
			}
			if (!found) {
				ArrayList<Rule> m = new ArrayList<Rule>();
				m.add(r);
				result.add(m);
			}
			copy.remove(0);
		}
		
		return result;
	}
}
