package ch.poole.openinghoursparser;

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
public class Nth extends Element {
    public static final int INVALID_NTH = Integer.MIN_VALUE;
    int                     startNth    = INVALID_NTH;
    int                     endNth      = INVALID_NTH;

    public Nth() {
    }

    /**
     * Construct a new Nth with the same content
     * 
     * @param n original Nth
     */
    public Nth(Nth n) {
        startNth = n.startNth;
        endNth = n.endNth;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();

        if (startNth != INVALID_NTH) {
            b.append(startNth);
        }
        if (endNth != INVALID_NTH) {
            b.append("-");
            b.append(endNth);
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null && other instanceof Nth) {
            Nth o = (Nth) other;
            if (startNth == o.startNth && endNth == o.endNth) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + startNth;
        result = 37 * result + endNth;
        return result;
    }

    /**
     * @return the startNth
     */
    public int getStartNth() {
        return startNth;
    }

    /**
     * @return the endNth of Nth.INVALID_NTH if not set/not a range
     */
    public int getEndNth() {
        return endNth;
    }

    /**
     * @param startNth the startNth to set
     */
    public void setStartNth(int startNth) {
        this.startNth = startNth;
    }

    /**
     * @param endNth the endNth to set
     */
    public void setEndNth(int endNth) {
        this.endNth = endNth;
    }

    @Override
    public Nth copy() {
        return new Nth(this);
    }
}
