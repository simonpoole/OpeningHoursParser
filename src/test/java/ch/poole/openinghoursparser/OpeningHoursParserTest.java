
package ch.poole.openinghoursparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for the OpeningHoursParser
 * @author Simon Poole
 *
 */
public class OpeningHoursParserTest {

	@Test
	public void regressionTest() {
		parseData("test-data/oh.txt", false, "test-data/oh.txt-result");
	}
	
	@Test
	public void regressionTestStrict() {
		parseData("test-data/oh.txt", true, "test-data/oh.txt-result-strict");
	}
	
	@Test
	public void holidaysVsWeekdays() {
		try
		{
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("PH,Su 10:00-12:00; PH Su 11:00-13:00".getBytes()));
			ArrayList<Rule> rules = parser.rules(false);
			assertEquals(2,rules.size());
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
		try
		{
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH 10:00-12:00".getBytes()));
			ArrayList<Rule> rules = parser.rules(false);
			assertEquals(1,rules.size());
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
		try
		{
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH 10:00-12:00".getBytes()));
			ArrayList<Rule> rules = parser.rules(true);
			fail("this should have thrown an exception");
		} catch (ParseException pex) {
		}
	}
	
	/**
	 * This completes successfully if parsing gives the same success result and for sucessful parses the same regenerated OH string
	 */
	private void parseData(String inputFile, boolean strict, String resultsFile)
	{
		int successful = 0;
		int errors = 0;
		int lexical = 0;
		BufferedReader inputRules = null;
		BufferedReader inputExpected = null;
		BufferedWriter outputExpected = null;
		String line = null;
		try
		{ 

			inputRules = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
			try {
				inputExpected = new BufferedReader(new InputStreamReader(new FileInputStream(resultsFile), "UTF8"));
			} catch (FileNotFoundException fnfex)
			{
				System.out.println("File not found " + fnfex.toString());
			} 
			outputExpected = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputFile+"-result" + (strict?"-strict":"")+"-temp"), "UTF8"));

			String expectedResultCode = null;
			String expectedResult = null;
			while ((line = inputRules.readLine()) != null) {
				if (inputExpected != null) {
					String[] expected = inputExpected.readLine().split("\t");
					expectedResultCode = expected[0];
					if (expected.length == 2) {
						expectedResult = expected[1];
					} else {
						expectedResult = null;
					}
				}
				try
				{
					OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(line.getBytes()));
					ArrayList<Rule> rules = parser.rules(strict);
					successful++;
					outputExpected.write("0\t"+Util.rulesToOpeningHoursString(rules)+"\n");
					if (expectedResultCode != null) {
						assertEquals("0", expectedResultCode);
						if (expectedResult != null) {
							assertEquals(expectedResult,Util.rulesToOpeningHoursString(rules));
						}
					}
				}
				catch (ParseException pex) {
					if (pex.toString().contains("Lexical")) {
						lexical++;
					} else {
						System.out.println("Parser exception for " + line + " " + pex.toString());
					}
					// pex.printStackTrace();
					errors++;
					outputExpected.write("1\n");
					if (expectedResultCode != null) {
						assertEquals("1",expectedResultCode);
					}
				}
				catch (NumberFormatException nfx) {
					System.out.println("Parser exception for " + line + " " + nfx.toString());
					// pex.printStackTrace();
					lexical++;
					errors++;
					outputExpected.write("2\n");
					if (expectedResultCode != null) {
						assertEquals("2",expectedResultCode);
					}
				}
				catch (Error err) {
					if (err.toString().contains("Lexical")) {
						lexical++;
					} else {
						System.out.println("Parser err for " + line + " " + err.toString());
						// err.printStackTrace();
					}
					errors++;
					outputExpected.write("3\n");
					if (expectedResultCode != null) {
						assertEquals("3",expectedResultCode);
					}
				}
			}
		} catch (FileNotFoundException fnfex)
		{
			System.out.println("File not found " + fnfex.toString());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AssertionError ae) {
			System.out.println("Assertion failed for " + line);
			throw ae;
		} finally {
			if (inputRules != null) {
				try {
					inputRules.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (outputExpected != null) {
				try {
					outputExpected.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Successful " + successful + " errors " + errors + " of which " + lexical + " are lexical errors");	
  	}
	
	@Test
	public void equalsTests() {
		DateWithOffset dwo1 = new DateWithOffset();
		dwo1.day = 1;
		dwo1.dayOffset = 1;
		dwo1.month = "Jan";
		dwo1.nth = 1;
		dwo1.openEnded = true;
		dwo1.varDate = "easter";
		dwo1.weekDay = "Mo";
		dwo1.weekDayOffset = "+Mo";
		dwo1.weekDayOffsetPositive = true;
		dwo1.year = 1999;
		
		assertEquals(dwo1,dwo1);
		
		DateWithOffset dwo2 = new DateWithOffset();
		dwo2.day = 1;
		dwo2.dayOffset = 1;
		dwo2.month = "Jan";
		dwo2.nth = 1;
		dwo2.openEnded = true;
		dwo2.varDate = "easter";
		dwo2.weekDay = "Mo";
		dwo2.weekDayOffset = "+Mo";
		dwo2.weekDayOffsetPositive = true;
		dwo2.year = 1999;
		
		assertEquals(dwo1, dwo2);
		assertEquals(dwo1.hashCode(),dwo2.hashCode());
		
		dwo2.day=2;
		assertFalse(dwo1.equals(dwo2));
		dwo2.day=1;
		assertEquals(dwo1, dwo2);
		dwo2.dayOffset=2;
		assertFalse(dwo1.equals(dwo2));
		dwo2.dayOffset=1;
		assertEquals(dwo1, dwo2);
		dwo2.month = "Feb";
		assertFalse(dwo1.equals(dwo2));
		dwo2.month = "Jan";
		assertEquals(dwo1, dwo2);
		dwo2.nth=2;
		assertFalse(dwo1.equals(dwo2));
		dwo2.nth=1;
		assertEquals(dwo1, dwo2);
		dwo2.openEnded=false;
		assertFalse(dwo1.equals(dwo2));
		dwo2.openEnded=true;
		assertEquals(dwo1, dwo2);
		dwo2.varDate=null;
		assertFalse(dwo1.equals(dwo2));
		dwo2.varDate="easter";
		assertEquals(dwo1, dwo2);
		dwo2.weekDay = "Tu";
		assertFalse(dwo1.equals(dwo2));
		dwo2.weekDay = "Mo";
		assertEquals(dwo1, dwo2);
		dwo2.weekDayOffset = "+Tu";
		assertFalse(dwo1.equals(dwo2));
		dwo2.weekDayOffset = "+Mo";
		assertEquals(dwo1, dwo2);
		dwo2.weekDayOffsetPositive=false;
		assertFalse(dwo1.equals(dwo2));
		dwo2.weekDayOffsetPositive=true;
		assertEquals(dwo1, dwo2);
		dwo2.year = 2000;
		assertFalse(dwo1.equals(dwo2));
		
		TimeSpan ts1 = new TimeSpan();
		ts1.start=1;
		
		VariableTime vt1 = new VariableTime();
		vt1.event = "Sunrise";
		vt1.offset = 0;
		assertEquals(vt1,vt1);
		
		ts1.startEvent = vt1;
		ts1.end=3;
		ts1.endEvent = null;
		ts1.openEnded=true;
		ts1.interval=0; 
		
		assertEquals(ts1,ts1);
		
		TimeSpan ts2 = new TimeSpan();
		ts2.start=1;
		
		VariableTime vt2 = new VariableTime();
		vt2.event = "Sunrise";
		vt2.offset = 0;
		
		ts2.startEvent = vt2;
		ts2.end=3;
		ts1.endEvent = null;
		ts2.openEnded=true;
		ts2.interval=0; 

		assertEquals(vt1,vt2);
		assertEquals(vt1.hashCode(),vt2.hashCode());
		
		assertEquals(ts1,ts2);
		assertEquals(ts1.hashCode(),ts2.hashCode());
		
		assertEquals(ts1,ts2);
		ts2.start=2;
		assertFalse(ts1.equals(ts2));
		ts2.start=1;
		assertEquals(ts1,ts2);
		vt2.event = "Sunset";
		assertFalse(ts1.equals(ts2));
		vt2.event = "Sunrise";
		assertEquals(ts1,ts2);
		ts2.end=4;
		assertFalse(ts1.equals(ts2));
		ts2.end=3;
		assertEquals(ts1,ts2);
		ts2.openEnded=false;
		assertFalse(ts1.equals(ts2));
		ts2.openEnded=true;
		assertEquals(ts1,ts2);
		ts2.interval=1;
		assertFalse(ts1.equals(ts2));
		
		try {
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 PH Mo,Tu 10:00-11:00".getBytes()));
			List<Rule>rules1 = parser.rules(false);
			parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 PH Mo,Tu 10:00-11:00".getBytes()));
			List<Rule>rules2 = parser.rules(false);
			assertEquals(1,rules1.size());
			assertEquals(1,rules2.size());
			assertEquals(rules1.get(0),rules1.get(0));
			assertEquals(rules1.get(0),rules2.get(0));
			assertEquals(rules1.get(0).hashCode(),rules2.get(0).hashCode());
			parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 SH Mo,Tu 10:00-11:00".getBytes()));
			List<Rule>rules3 = parser.rules(false);
			assertFalse(rules1.get(0).equals(rules3.get(0)));
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
	}
	
	@Test
	public void mergeRulesTest() {
		OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 Mo,Tu 10:00-11:00;2010-2011 Th,Fr 13:00-14:00".getBytes()));
		try {
			List<Rule>rules = parser.rules(false);
			assertEquals(2,rules.size());
			List<ArrayList<Rule>> mergeableRules = Util.getMergeableRules(rules);
			assertEquals(1,mergeableRules.size());
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
	}
	
	@Test
	public void twentyFourSeven() {
		OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("2010-2011 Mo,Tu 10:00-11:00, 24/7".getBytes()));
		try {
			List<Rule>rules = parser.rules(false);
			assertEquals(2,rules.size());;
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
	}
	
}
