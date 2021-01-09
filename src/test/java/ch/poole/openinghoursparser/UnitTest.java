/**
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

package ch.poole.openinghoursparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the OpeningHoursParser
 * 
 * @author Simon Poole
 *
 */
public class UnitTest {

    @Before
    public void setUp() {
        I18n.setLocale(Locale.ROOT);
    }

    @Test
    public void holidaysVsWeekdays() {
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("PH,Su 10:00-12:00; PH Su 11:00-13:00".getBytes()));
            List<Rule> rules = parser.rules(false);
            assertEquals(2, rules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH 10:00-12:00".getBytes()));
            List<Rule> rules = parser.rules(true);
            assertEquals(1, rules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH,Mo 10:00-12:00".getBytes()));
            List<Rule> rules = parser.rules(true);
            fail("this should have thrown an exception");
        } catch (ParseException pex) {
            assertEquals("Holiday in weekday range at line 1, column 10", pex.getMessage());
        }
    }

    @Test
    public void holidays() {
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("PH Su 10:00-12:00".getBytes()));
            List<Rule> rules = parser.rules(false);
            assertEquals(1, rules.size());
            assertEquals("PH Su 10:00-12:00", rules.get(0).toString());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void equalsTests() {
        DateWithOffset dwo1 = new DateWithOffset();
        dwo1.day = 1;
        dwo1.dayOffset = 1;
        try {
            dwo1.setMonth("bla");
            fail("This should have caused an exception");
        } catch (IllegalArgumentException ex) {
            assertEquals("bla is not a valid month", ex.getMessage());
        }
        dwo1.setMonth("Jan");
        // dwo1.nth = 1;
        dwo1.openEnded = true;
        try {
            dwo1.setVarDate("bla");
            fail("This should have caused an exception");
        } catch (IllegalArgumentException ex) {
            assertEquals("bla is not a valid variable date", ex.getMessage());
        }
        dwo1.setVarDate("easter");
        // try {
        // dwo1.setWeekDay("bla");
        // fail("This should have caused an exception");
        // } catch (IllegalArgumentException ex) {
        // }
        // dwo1.setWeekDay("Mo");
        dwo1.setWeekDayOffset("Mo");
        dwo1.weekDayOffsetPositive = true;
        try {
            dwo1.setYear(1899);
            fail("This should have caused an exception");
        } catch (IllegalArgumentException ex) {
            assertEquals("1899 is earlier than 1900", ex.getMessage());
        }
        dwo1.setYear(1999);

        assertEquals(dwo1, dwo1);

        DateWithOffset dwo2 = new DateWithOffset();
        dwo2.day = 1;
        dwo2.dayOffset = 1;
        dwo2.setMonth("Jan");
        // dwo2.nth = 1;
        dwo2.openEnded = true;
        dwo2.setVarDate("easter");
        // dwo2.setWeekDay("Mo");
        dwo2.setWeekDayOffset("Mo");
        dwo2.weekDayOffsetPositive = true;
        dwo2.setYear(1999);

        assertEquals(dwo1, dwo2);
        assertEquals(dwo1.hashCode(), dwo2.hashCode());

        dwo2.day = 2;
        assertFalse(dwo1.equals(dwo2));
        dwo2.day = 1;
        assertEquals(dwo1, dwo2);
        dwo2.dayOffset = 2;
        assertFalse(dwo1.equals(dwo2));
        dwo2.dayOffset = 1;
        assertEquals(dwo1, dwo2);
        dwo2.setMonth("Feb");
        assertFalse(dwo1.equals(dwo2));
        dwo2.setMonth("Jan");
        assertEquals(dwo1, dwo2);
        // dwo2.nth=2;
        // assertFalse(dwo1.equals(dwo2));
        // dwo2.nth=1;
        assertEquals(dwo1, dwo2);
        dwo2.openEnded = false;
        assertFalse(dwo1.equals(dwo2));
        dwo2.openEnded = true;
        assertEquals(dwo1, dwo2);
        dwo2.varDate = null;
        assertFalse(dwo1.equals(dwo2));
        dwo2.setVarDate("easter");
        assertEquals(dwo1, dwo2);
        // dwo2.setWeekDay("Tu");
        // assertFalse(dwo1.equals(dwo2));
        // dwo2.setWeekDay("Mo");
        assertEquals(dwo1, dwo2);
        dwo2.setWeekDayOffset("Tu");
        assertFalse(dwo1.equals(dwo2));
        dwo2.setWeekDayOffset("Mo");
        assertEquals(dwo1, dwo2);
        dwo2.weekDayOffsetPositive = false;
        assertFalse(dwo1.equals(dwo2));
        dwo2.weekDayOffsetPositive = true;
        assertEquals(dwo1, dwo2);
        dwo2.setYear(2000);
        assertFalse(dwo1.equals(dwo2));

        TimeSpan ts1 = new TimeSpan();
        ts1.start = 1;

        VariableTime vt1 = new VariableTime();
        vt1.setEvent("sunrise");
        vt1.offset = 0;
        assertEquals(vt1, vt1);

        ts1.startEvent = vt1;
        ts1.end = 3;
        ts1.endEvent = null;
        ts1.openEnded = true;
        ts1.interval = 0;

        assertEquals(ts1, ts1);

        TimeSpan ts2 = new TimeSpan();
        ts2.start = 1;

        VariableTime vt2 = new VariableTime();
        vt2.setEvent("sunrise");
        vt2.offset = 0;

        ts2.startEvent = vt2;
        ts2.end = 3;
        ts1.endEvent = null;
        ts2.openEnded = true;
        ts2.interval = 0;

        assertEquals(vt1, vt2);
        assertEquals(vt1.hashCode(), vt2.hashCode());

        assertEquals(ts1, ts2);
        assertEquals(ts1.hashCode(), ts2.hashCode());

        assertEquals(ts1, ts2);
        ts2.start = 2;
        assertFalse(ts1.equals(ts2));
        ts2.start = 1;
        assertEquals(ts1, ts2);
        vt2.setEvent("sunset");
        assertFalse(ts1.equals(ts2));
        vt2.setEvent("sunrise");
        assertEquals(ts1, ts2);
        ts2.end = 4;
        assertFalse(ts1.equals(ts2));
        ts2.end = 3;
        assertEquals(ts1, ts2);
        ts2.openEnded = false;
        assertFalse(ts1.equals(ts2));
        ts2.openEnded = true;
        assertEquals(ts1, ts2);
        ts2.interval = 1;
        assertFalse(ts1.equals(ts2));

        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 PH Mo,Tu 10:00-11:00".getBytes()));
            List<Rule> rules1 = parser.rules(false);
            parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 PH Mo,Tu 10:00-11:00".getBytes()));
            List<Rule> rules2 = parser.rules(false);
            assertEquals(1, rules1.size());
            assertEquals(1, rules2.size());
            assertEquals(rules1.get(0), rules1.get(0));
            assertEquals(rules1.get(0), rules2.get(0));
            assertEquals(rules1.get(0).hashCode(), rules2.get(0).hashCode());
            parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 SH Mo,Tu 10:00-11:00".getBytes()));
            List<Rule> rules3 = parser.rules(false);
            assertFalse(rules1.get(0).equals(rules3.get(0)));
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void mergeRulesTest() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 Mo,Tu 10:00-11:00;2010-2011 Th,Fr 13:00-14:00".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            assertEquals(2, rules.size());
            List<List<Rule>> mergeableRules = Util.getMergeableRules(rules);
            assertEquals(1, mergeableRules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void twentyFourSeven() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 Mo,Tu 10:00-11:00, 24/7".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            assertEquals(2, rules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void dateWithOffset() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Dec 25 off".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            assertEquals(1, rules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }

        // parser = new OpeningHoursParser(new ByteArrayInputStream("Dec Mo[1]-Jan Tu[1],Mar Fr[2]".getBytes()));
        // try {
        // List<Rule>rules = parser.rules(false);
        // assertEquals(1,rules.size());
        // assertEquals(2,rules.get(0).getDates().size());
        // } catch (ParseException pex) {
        // fail(pex.getMessage());
        // }

    }

    @Test
    /**
     * This doesn't seem to turn up in our test data
     */
    public void dateRangeWithInterval() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Jan-Mar/8".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            assertEquals(1, rules.size());
            ;
            Rule r = rules.get(0);
            List<DateRange> list = r.getDates();
            assertEquals(1, list.size());
            DateRange range = list.get(0);
            assertEquals(8, range.getInterval());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        parser = new OpeningHoursParser(new ByteArrayInputStream("Jan-Mar 7/8".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            fail("should throw a ParseException");
        } catch (ParseException pex) {
            assertEquals("Interval not allowed here at line 1, column 11", pex.getMessage());
        }
    }

    @Test
    public void ampm() {
        // 12:01pm to 12:59pm is 12:01 to 12:59
        // 13:00pm and later is considered to be mistyped and in the 24:00 system
        // 12:00 pm is 12:00
        // 12:01am to 12:59am is 00:01 to 00:59
        // 12:00am is 00:00
        // 13:00am and later is considered to be mistyped and in the 24:00 system
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("00:01 am".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            Rule r = rules.get(0);
            List<TimeSpan> times = r.getTimes();
            TimeSpan span = times.get(0);
            assertEquals(1, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("12:01 pm".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(12 * 60 + 1, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("12:00 pm".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(12 * 60, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("13:00 pm".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(13 * 60, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("12:00 am".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(0, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("12:01 am".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(1, span.getStart());

            parser = new OpeningHoursParser(new ByteArrayInputStream("13:00 am".getBytes()));
            rules = parser.rules(false);
            r = rules.get(0);
            times = r.getTimes();
            span = times.get(0);
            assertEquals(13 * 60, span.getStart());

        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    static String LONG_TEST = "Mo[2] -2 days 10:00-12:00;24/7;week 4-40 PH+2days dawn-09:00;dawn-25:00/25;2010-2100/4 12:01-13:02, 14:00, 10:00-(sunset+02:00), 13:00+, 11:01-45:00/46, dawn-dusk, sunrise+; 12-16 closed \"ein test\"; Mo, We 12:01-13:02; Apr-Sep 10:01-13:03, Dec 13:03-21:01";

    @Test
    public void copyTest() {

        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(LONG_TEST.getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            for (Rule r : rules) {
                Rule copied = r.copy();
                assertEquals(r.toDebugString(), copied.toDebugString());
            }
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void collectionTimes() {
        // common values from https://taginfo.openstreetmap.org/keys/collection_times#values
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Mo-Fr 09:00; Sa 07:00".getBytes())).rules(true);
            new OpeningHoursParser(new ByteArrayInputStream("Mo-Sa 09:00".getBytes())).rules(true);
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void serviceTimes() {
        // common values from https://taginfo.openstreetmap.org/keys/service_times#values
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Su 10:00".getBytes())).rules(true);
            new OpeningHoursParser(new ByteArrayInputStream("Su 08:00-10:00".getBytes())).rules(true);
            new OpeningHoursParser(new ByteArrayInputStream("sunset,sunrise".getBytes())).rules(true);
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void weekdayThree() {
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Tue-Thu 09:00-18:00".getBytes())).rules(false);
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Tue-Thu 09:00-18:00".getBytes())).rules(true);
            fail("should throw a ParseException");
        } catch (ParseException pex) {
            assertEquals("Three character weekday at line 1, column 4", pex.getMessage());
        }
    }

    @Test
    public void weekdayDe() {
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Di-Do 09:00-18:00".getBytes())).rules(false);
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        try {
            new OpeningHoursParser(new ByteArrayInputStream("Di-Do 09:00-18:00".getBytes())).rules(true);
            fail("should throw a ParseException");
        } catch (ParseException pex) {
            assertEquals("German weekday abbreviation at line 1, column 3", pex.getMessage());
        }
    }

    @Test
    public void weekRangeTest() {

        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("10:00-12:00".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            Rule r = rules.get(0);
            WeekRange wr = new WeekRange();
            wr.setStartWeek(1);
            wr.setEndWeek(52);
            wr.setInterval(1);
            List<WeekRange> weeks = new ArrayList<WeekRange>();
            weeks.add(wr);
            r.setWeeks(weeks);
            assertEquals(1, wr.getStartWeek());
            assertEquals(52, wr.getEndWeek());
            assertEquals(1, wr.getInterval());
            assertEquals("week 01-52/1 10:00-12:00", r.toString());
            try {
                wr.setStartWeek(-1);
                fail("Should throw an exception");
            } catch (IllegalArgumentException ex) {
                assertEquals("-1 is outside of the 1-53 range", ex.getMessage());
            }
            try {
                wr.setEndWeek(55);
                fail("Should throw an exception");
            } catch (IllegalArgumentException ex) {
                assertEquals("55 is outside of the 1-53 range", ex.getMessage());
            }
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void extendedTimeTest() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("02:00-01:00".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            Rule r = rules.get(0);
            List<TimeSpan> spans = r.getTimes();
            assertEquals(120, spans.get(0).getStart());
            assertEquals(24 * 60 + 60, spans.get(0).getEnd());
            assertEquals("02:00-01:00", spans.get(0).toString());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        parser = new OpeningHoursParser(new ByteArrayInputStream("01:00-26:00".getBytes()));
        try {
            List<Rule> rules = parser.rules(false);
            Rule r = rules.get(0);
            List<TimeSpan> spans = r.getTimes();
            assertEquals(60, spans.get(0).getStart());
            assertEquals(24 * 60 + 120, spans.get(0).getEnd());
            assertEquals("01:00-26:00", spans.get(0).toString());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    @Test
    public void timeTest() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Sa-Su 10.00-20.00".getBytes()));
        try {
            assertEquals("Sa-Su 10:00-20:00", Util.rulesToOpeningHoursString(parser.rules(false)));
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        parser = new OpeningHoursParser(new ByteArrayInputStream("Sa-Su 10.00-20.00".getBytes()));
        try {
            Util.rulesToOpeningHoursString(parser.rules(true));
            fail("Should throw an exception");
        } catch (ParseException pex) {
            assertEquals("Invalid minutes at line 1, column 12", pex.getMessage());
        }
        parser = new OpeningHoursParser(new ByteArrayInputStream("Mo,Tu 04-17".getBytes()));
        try {
            assertEquals("Mo,Tu 04:00-17:00", Util.rulesToOpeningHoursString(parser.rules(false)));
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
        parser = new OpeningHoursParser(new ByteArrayInputStream("Mo,Tu 04-17".getBytes()));
        try {
            assertEquals("Mo,Tu 04:00-17:00", Util.rulesToOpeningHoursString(parser.rules(true)));
            fail("Should throw an exception");
        } catch (ParseException pex) {
            assertEquals("Hours without minutes", pex.getMessage());
        }
    }

    @Test
    public void translationSupport() {
        I18n.setLocale(Locale.GERMAN);
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH,Mo 10:00-12:00".getBytes()));
            List<Rule> rules = parser.rules(true);
            fail("this should have thrown an exception");
        } catch (ParseException pex) {
            assertEquals("Feiertag in Wochentagbereich in Zeile 1, Zeichen 10", pex.getMessage());
        }
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("10:00-12:00 Su".getBytes()));
            List<Rule> rules = parser.rules(true);
            fail("this should have thrown an exception");
        } catch (ParseException pex) {
            assertEquals("Vorgefunden wurde:  <WEEKDAY> \"Su \" in Zeile 1, Zeichen 10" + System.lineSeparator() + "Erwartet wurde: <EOF>", pex.getMessage());
        }
    }

    @Test
    public void modifiers() {
        try {
            OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH closed \"test\"".getBytes()));
            List<Rule> rules = parser.rules(true);
            assertEquals(1, rules.size());
            RuleModifier modifier = rules.get(0).getModifier();
            assertNotNull(modifier);
            assertEquals(RuleModifier.Modifier.CLOSED, modifier.getModifier());
            assertEquals("test", modifier.getComment());
            //
            parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH \"test\"".getBytes()));
            rules = parser.rules(true);
            assertEquals(1, rules.size());
            modifier = rules.get(0).getModifier();
            assertNotNull(modifier);
            assertNull(modifier.getModifier());
            assertEquals("test", modifier.getComment());
            //
            parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH unknown".getBytes()));
            rules = parser.rules(true);
            assertEquals(1, rules.size());
            modifier = rules.get(0).getModifier();
            assertNotNull(modifier);
            assertEquals(RuleModifier.Modifier.UNKNOWN, modifier.getModifier());
            assertNull(modifier.getComment());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }

    /**
     * Check that restarting and reporting more than just the first error worked
     */
    @Test
    public void multipleErrorsReporting() {
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Fx-Sa 02:00-01.00".getBytes()));
        try {
            List<Rule> rules = parser.rules(true);
            fail("this should have thrown an exception");
        } catch (ParseException pex) {
            assertTrue(pex instanceof OpeningHoursParseException);
            assertEquals(2, ((OpeningHoursParseException) pex).getExceptions().size());
            OpeningHoursParseException ex = ((OpeningHoursParseException) pex).getExceptions().get(0);
            assertTrue(ex.getMessage().startsWith("Encountered:  <UNEXPECTED_CHAR> \"F \" at line 0, column 0"));
            assertEquals(0, ex.getLine());
            assertEquals(0, ex.getColumn());
            ex = ((OpeningHoursParseException) pex).getExceptions().get(1);
            assertEquals(1, ex.getLine());
            assertEquals(17, ex.getColumn());
            assertEquals("Invalid minutes at line 1, column 17", ex.getMessage());
        }
    }
    
    @Test
    public void noSpaceAfterColon() {       
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Oct-Mar:07:00-20:00; Apr:07:00-22:00; May-Sep:07:00-23:00".getBytes()));   
        try {
            List<Rule> rules = parser.rules(true);            
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }
    
    @Test
    public void dateRangeWithOccurance() {       
        OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Jan 1-Mar Mo[1], Jun 1-Jul Tu[1]".getBytes()));   
        try {
            List<Rule> rules = parser.rules(true);      
            assertEquals(1, rules.size());
        } catch (ParseException pex) {
            fail(pex.getMessage());
        }
    }
}
