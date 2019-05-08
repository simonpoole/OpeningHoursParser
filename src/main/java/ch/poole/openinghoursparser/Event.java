package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

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
public enum Event {
    DAWN("dawn"), SUNRISE("sunrise"), DUSK("dusk"), SUNSET("sunset");

    private final String name;

    /**
     * Construct a new member of the enum
     * 
     * @param name the name as a String
     */
    Event(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the Event value for a String
     * 
     * @param event the event as a String
     * @return an Event or null
     */
    public static Event getValue(String event) {
        for (Event e : Event.values()) {
            if (e.toString().equals(event)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Get list of all values as Strings
     * 
     * @return a List with all values
     */
    public static List<String> nameValues() {
        List<String> result = new ArrayList<>();
        for (Event e : values()) {
            result.add(e.toString());
        }
        return result;
    }
}
