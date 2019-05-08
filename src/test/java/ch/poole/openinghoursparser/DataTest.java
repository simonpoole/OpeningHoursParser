
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
 * 
 * @author Simon Poole
 *
 */
public class DataTest {

    @Test
    public void regressionTest() {
        parseData("test-data/oh.txt", false, false, "test-data/oh.txt-result");
        parseData("test-data/oh.txt", false, true, "test-data/oh.txt-debug-result");
    }

    @Test
    public void regressionTestStrict() {
        parseData("test-data/oh.txt", true, false, "test-data/oh.txt-result-strict");
        parseData("test-data/oh.txt", true, true, "test-data/oh.txt-debug-result-strict");
    }

    /**
     * This completes successfully if parsing gives the same success result and for successful parses the same
     * regenerated OH string
     */
    private void parseData(String inputFile, boolean strict, boolean debug, String resultsFile) {
        int successful = 0;
        int errors = 0;
        int lexical = 0;
        BufferedReader inputRules = null;
        BufferedReader inputExpected = null;
        BufferedWriter outputExpected = null;
        BufferedWriter outputFail = null;
        String line = null;
        try {

            inputRules = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
            try {
                inputExpected = new BufferedReader(new InputStreamReader(new FileInputStream(resultsFile), "UTF8"));
            } catch (FileNotFoundException fnfex) {
                System.out.println("File not found " + fnfex.toString());
            }
            outputExpected = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(inputFile + "-result" + (strict ? "-strict" : "") + (debug ? "-debug" : "") + "-temp"), "UTF8"));
            outputFail = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(inputFile + "-fail" + (strict ? "-strict" + (debug ? "-debug" : "") : "")), "UTF8"));

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
                try {
                    OpeningHoursParser parser = new OpeningHoursParser(new ByteArrayInputStream(line.getBytes()));
                    ArrayList<Rule> rules = parser.rules(strict);
                    successful++;
                    if (debug) {
                        outputExpected.write("0\t" + Util.rulesToOpeningHoursDebugString(rules) + "\n");
                        if (expectedResultCode != null) {
                            assertEquals("0", expectedResultCode);
                            if (expectedResult != null) {
                                assertEquals(expectedResult, Util.rulesToOpeningHoursDebugString(rules));
                            }
                        }
                    } else {
                        outputExpected.write("0\t" + Util.rulesToOpeningHoursString(rules) + "\n");
                        if (expectedResultCode != null) {
                            assertEquals("0", expectedResultCode);
                            if (expectedResult != null) {
                                assertEquals(expectedResult, Util.rulesToOpeningHoursString(rules));
                            }
                        }
                    }
                } catch (ParseException pex) {
                    if (pex.toString().contains("Lexical")) {
                        lexical++;
                    } else {
                        System.out.println("Parser exception for " + line + " " + pex.toString());
                    }
                    //pex.printStackTrace();
                    errors++;
                    outputExpected.write("1\n");
                    outputFail.write(line + "\t" + pex.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals(expectedResultCode, "1");
                    }
                } catch (NumberFormatException nfx) {
                    System.out.println("Parser exception for " + line + " " + nfx.toString());
                    // pex.printStackTrace();
                    lexical++;
                    errors++;
                    outputExpected.write("2\n");
                    outputFail.write(line + "\t" + nfx.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals(expectedResultCode, "2");
                    }
                } catch (Error err) {
                    if (err.toString().contains("Lexical")) {
                        lexical++;
                    } else {
                        System.out.println("Parser err for " + line + " " + err.toString());
                        // err.printStackTrace();
                    }
                    errors++;
                    outputExpected.write("3\n");
                    outputFail.write(line + "\t" + err.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals(expectedResultCode, "3");
                    }
                }
            }
        } catch (FileNotFoundException fnfex) {
            System.out.println("File not found " + fnfex.toString());
        } catch (IOException e) {
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
                    e.printStackTrace();
                }
            }
            if (outputExpected != null) {
                try {
                    outputExpected.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputFail != null) {
                try {
                    outputFail.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Successful " + successful + " errors " + errors + " of which " + lexical + " are lexical errors");
    }
}
