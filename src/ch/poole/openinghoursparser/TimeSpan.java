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

public class TimeSpan extends Element {

	boolean twentyfourseven = false;
	int start=-1;
	VariableTime startEvent = null;
	int end=-1;
	VariableTime endEvent = null;
	boolean openEnded=false;
	int interval=0; //minutes

	public String toString() {
		StringBuffer b = new StringBuffer();
		if (twentyfourseven) {
			b.append("24/7");
		} else {
			if (startEvent != null) {
				b.append(startEvent.toString());
			} else {
				b.append(String.format("%02d",(int)start/60));
				b.append(":");
				b.append(String.format("%02d",start%60));
			}
			if (endEvent != null) {
				b.append("-");
				b.append(endEvent.toString());
			} else if (end != -1){
				b.append("-");
				b.append(String.format("%02d",(int)end/60));
				b.append(":");
				b.append(String.format("%02d",end%60));
			}
			if (openEnded) {
				b.append("+");
			}
			if (interval != 0) { // output only the full format
				b.append("/");
				b.append(String.format("%02d",(int)interval/60));
				b.append(":");
				b.append(String.format("%02d",interval%60));
			}
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		TimeSpan o = (TimeSpan)other;
		if (twentyfourseven == o.twentyfourseven && start == o.start && (startEvent == o.startEvent  || (startEvent != null && startEvent.equals(o.startEvent))) 
				&& end == o.end && (endEvent == o.endEvent  || (endEvent != null && endEvent.equals(o.endEvent))) && openEnded == o.openEnded && interval == o.interval) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (twentyfourseven ? 0 : 1);
		result = 37 * result + start;
		result = 37 * result + (startEvent == null ? 0 : startEvent.hashCode());
		result = 37 * result + end;
		result = 37 * result + (endEvent == null ? 0 : endEvent.hashCode());
		result = 37 * result + (openEnded ? 0 : 1);
		result = 37 * result + interval;
		return result;
	}

	/**
	 * @return the twentyfourseven
	 */
	public boolean isTwentyfourseven() {
		return twentyfourseven;
	}

	/**
	 * @return the start time in minutes
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the startEvent
	 */
	public VariableTime getStartEvent() {
		return startEvent;
	}

	/**
	 * @return the end time in minutes
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @return the endEvent
	 */
	public VariableTime getEndEvent() {
		return endEvent;
	}

	/**
	 * @return the openEnded
	 */
	public boolean isOpenEnded() {
		return openEnded;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param twentyfourseven the twentyfourseven to set
	 */
	public void setTwentyfourseven(boolean twentyfourseven) {
		this.twentyfourseven = twentyfourseven;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @param startEvent the startEvent to set
	 */
	public void setStartEvent(VariableTime startEvent) {
		this.startEvent = startEvent;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @param endEvent the endEvent to set
	 */
	public void setEndEvent(VariableTime endEvent) {
		this.endEvent = endEvent;
	}

	/**
	 * @param openEnded the openEnded to set
	 */
	public void setOpenEnded(boolean openEnded) {
		this.openEnded = openEnded;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
}
