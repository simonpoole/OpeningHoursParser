package ch.poole.openinghoursparser;

import org.jetbrains.annotations.NotNull;

/**
 * Container for objects from the opening_hours specification
 * 
 * @author Simon Poole
 *
 *         Copyright (c) 2015 Simon Poole
 *
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without
 *         limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *         Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 *         conditions:
 * 
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions
 *         of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *         TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *         THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *         CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE " OR THE USE OR OTHER
 *         DEALINGS IN THE SOFTWARE.
 */

public class VariableTime extends Element {

    Event event  = null;
    /** offset in minutes */
    int   offset = 0;

    /**
     * Default constructor
     */
    public VariableTime() {
        // empty
    }

    /**
     * Construct a new VariableTime with the same contents
     * 
     * @param vt the original VariableTime
     */
    public VariableTime(@NotNull VariableTime vt) {
        event = vt.event;
        offset = vt.offset;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (event != null) {
            if (offset != 0) {
                b.append("(");
                b.append(event);
                if (offset > 0) {
                    b.append("+");
                }
                b.append(String.format("%02d", offset / 60));
                b.append(":");
                b.append(String.format("%02d", Math.abs(offset) % 60));
                b.append(")");
            } else {
                b.append(event);
            }
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof VariableTime) {
            VariableTime o = (VariableTime) other;
            return Util.equals(event, o.event) && offset == o.offset;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (event == null ? 0 : event.hashCode());
        result = 37 * result + offset;
        return result;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Set the day the range starts on
     * 
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Set the day the range starts on
     * 
     * @param event the event to set
     */
    public void setEvent(String event) {
        Event e = Event.getValue(event);
        if (e == null) {
            throw new IllegalArgumentException(e + " is not a valid Event");
        }
        this.event = e;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public VariableTime copy() {
        return new VariableTime(this);
    }
}
