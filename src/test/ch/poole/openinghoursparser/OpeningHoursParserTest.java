
package ch.poole.openinghoursparser;

import static org.junit.Assert.assertEquals;

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
	
	/**
	 * This completes successfully if parsing dives the same success result, this is naturally a bit trivial since it doesn't test the actual parse result 
	 */
	private void parseData(String inputFile, boolean strict, String resultsFile)
	{
		int successful = 0;
		int errors = 0;
		int lexical = 0;
		BufferedReader brInput = null;
		BufferedReader brResults = null;
		BufferedWriter bw = null;
		String line = null;
		try
		{ 

			brInput = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
			try {
				brResults = new BufferedReader(new InputStreamReader(new FileInputStream(resultsFile), "UTF8"));
			} catch (FileNotFoundException fnfex)
			{
				System.out.println("File not found " + fnfex.toString());
			} 
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputFile+"-result" + (strict?"-strict":"")+"-temp"), "UTF8"));

			String expectedResult = null;
			while ((line = brInput.readLine()) != null) {
				if (brResults != null) {
					expectedResult = brResults.readLine();
				}
				try
				{
					OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(line.getBytes()));
					ArrayList<Rule> rules = parser.rules(strict);
//					for (Rule rl:rules)
//					{
//						System.out.println(rl.toString());
//					}
					successful++;
					bw.write("0\n");
					if (expectedResult != null) {
						assertEquals(expectedResult,"0");
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
					bw.write("1\n");
					if (expectedResult != null) {
						assertEquals(expectedResult,"1");
					}
				}
				catch (NumberFormatException nfx) {
					System.out.println("Parser exception for " + line + " " + nfx.toString());
					// pex.printStackTrace();
					lexical++;
					errors++;
					bw.write("2\n");
					if (expectedResult != null) {
						assertEquals(expectedResult,"2");
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
					bw.write("3\n");
					if (expectedResult != null) {
						assertEquals(expectedResult,"3");
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
			if (brInput != null) {
				try {
					brInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Successful " + successful + " errors " + errors + " of which " + lexical + " are lexical errors");	
  	}
	
}
