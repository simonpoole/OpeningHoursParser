package ch.poole.openinghoursparser;


import java.io.BufferedReader;
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
 * TEsts for the OpeningHoursParser
 * @author Simon Poole
 *
 */
public class Test {

	
	public static void main(String args []) 
	{
		int successful = 0;
		int errors = 0;
		int lexical = 0;
		try
		{ 

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("tests/sample.txt"), "UTF8"));
			String line;
			while ((line = br.readLine()) != null) {
				try
				{
					OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(line.getBytes()));
					ArrayList<Rule> rules = parser.rules();
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
		}
		System.out.println("Successful " + successful + " errors " + errors + " of which " + lexical + " are lexical errors");
  	}
	
}
