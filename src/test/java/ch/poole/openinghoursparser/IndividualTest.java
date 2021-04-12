
package ch.poole.openinghoursparser;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Individual testing for the OpeningHoursParser receiving
 * inputs from System.in
 * 
 * 
 * @author Vuong Ho
 *
 */

public class IndividualTest {
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        
        while(true) {
            System.out.print("Please enter your input: ");
            String input = sc.nextLine();

            try {
                OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(input.getBytes()));
                List<ch.poole.openinghoursparser.Rule> rules = parser.rules(false);
                
                System.out.println("Legal input string");
                System.out.println("Detected rules in input string");
                for(ch.poole.openinghoursparser.Rule rule : rules) {
                    System.out.println(rule.toDebugString());
                }
    
    
            } catch (OpeningHoursParseException e) {
                System.out.println("Illegal input string");
                e.printStackTrace();
            }
        }
        

    }

 
}
