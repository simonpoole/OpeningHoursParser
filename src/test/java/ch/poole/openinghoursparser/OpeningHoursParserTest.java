
package ch.poole.openinghoursparser;

import static org.junit.Assert.assertEquals;
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
			for (Rule r:rules) {
				System.out.println(r);
			}
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
		try
		{
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH 10:00-12:00".getBytes()));
			ArrayList<Rule> rules = parser.rules(false);
			for (Rule r:rules) {
				System.out.println(r);
			}
		} catch (ParseException pex) {
			fail(pex.getMessage());
		}
		try
		{
			OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream("Su,PH 10:00-12:00".getBytes()));
			ArrayList<Rule> rules = parser.rules(true);
			for (Rule r:rules) {
				System.out.println(r);
			}
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
	
}
