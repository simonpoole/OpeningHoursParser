
package ch.poole.openinghoursparser;

import java.io.BufferedReader;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import ch.poole.openinghoursparser.OpeningHoursParser;
import ch.poole.openinghoursparser.ParseException;
import ch.poole.openinghoursparser.Rule;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the OpeningHoursParser
 * @author Simon Poole
 *
 */
public class OpeningHoursParserTest {

	@Test
	public void testDataParsing() {
		parseData("test-data/oh.txt", false, 143070, 18198, 2);
	}
	
	@Test
	public void testDataParsingStrict() {
		parseData("test-data/oh.txt", true, 131590, 29678, 2);
	}
	
	/**
	 * This completes successfully if the number of errors etc remain the same, this is naturally a bit trivial since it doesn't test the actual parse result 
	 */
	private void parseData(String inputFile, boolean strict, int expectedSuccessful, int expectedErrors, int expectedLexical)
	{
		int successful = 0;
		int errors = 0;
		int lexical = 0;
		BufferedReader br = null;
		try
		{ 

			br = new BufferedReader(new InputStreamReader(new FileInputStream("test-data/oh.txt"), "UTF8"));
			String line;
			while ((line = br.readLine()) != null) {
				try
				{
					OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(line.getBytes()));
					ArrayList<Rule> rules = parser.rules(strict);
//					for (Rule rl:rules)
//					{
//						System.out.println(rl.toString());
//					}
					successful++;
				}
				catch (ParseException pex) {
					if (pex.toString().contains("Lexical")) {
						lexical++;
					} else {
						System.out.println("Parser exception for " + line + " " + pex.toString());
					}
					// pex.printStackTrace();
					
					errors++;
				}
				catch (NumberFormatException nfx) {
					System.out.println("Parser exception for " + line + " " + nfx.toString());
					// pex.printStackTrace();
					lexical++;
					errors++;
				}
				catch (Error err) {
					if (err.toString().contains("Lexical")) {
						lexical++;
					} else {
						System.out.println("Parser err for " + line + " " + err.toString());
						// err.printStackTrace();
					}
					errors++;
				}
			}
		} catch (FileNotFoundException fnfex)
		{
			System.out.println("File not found " + fnfex.toString());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Successful " + successful + " errors " + errors + " of which " + lexical + " are lexical errors");
		assertEquals(expectedSuccessful,successful);
		assertEquals(expectedErrors,errors);
		assertEquals(expectedLexical,lexical);
		
  	}
	
}
