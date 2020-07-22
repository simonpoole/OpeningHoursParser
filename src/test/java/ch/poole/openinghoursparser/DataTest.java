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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the OpeningHoursParser
 * 
 * @author Simon Poole
 *
 */
public class DataTest {

    boolean quiet = true; // stops large amounts of not very helpful output

    /**
     * Compare non-strict mode output
     */
    @Test
    public void regressionTest() {
        parseData("test-data/oh.txt", false, false, "test-data/oh.txt-result");
        parseData("test-data/oh.txt", false, true, "test-data/oh.txt-debug-result");
    }

    /**
     * Compare strict mode output
     */
    @Test
    public void regressionTestStrict() {
        parseData("test-data/oh.txt", true, false, "test-data/oh.txt-result-strict");
        parseData("test-data/oh.txt", true, true, "test-data/oh.txt-debug-result-strict");
    }

    /**
     * This completes successfully if parsing gives the same success result and for successful parses the same
     * regenerated OH string
     * 
     * @param inputFile input data file
     * @param strict if true use strict mode
     * @param debug if true produce debug output
     * @param resultsFile file to write results to
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

            inputRules = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
            try {
                inputExpected = new BufferedReader(new InputStreamReader(new FileInputStream(resultsFile), StandardCharsets.UTF_8));
            } catch (FileNotFoundException fnfex) {
                System.out.println("File not found " + fnfex.toString());
            }
            outputExpected = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(inputFile + "-result" + (strict ? "-strict" : "") + (debug ? "-debug" : "") + "-temp"), StandardCharsets.UTF_8));
            outputFail = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(inputFile + "-fail" + (strict ? "-strict" + (debug ? "-debug" : "") : "")), StandardCharsets.UTF_8));

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
                    List<Rule> rules = parser.rules(strict);
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
                    } else if (!quiet) {
                        System.out.println("Parser exception for " + line + " " + pex.toString());
                    }
                    errors++;
                    outputExpected.write("1\n");
                    outputFail.write(line + "\t" + pex.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals("1", expectedResultCode);
                    }
                } catch (NumberFormatException nfx) {
                    if (!quiet) {
                        System.out.println("Parser exception for " + line + " " + nfx.toString());
                    }
                    lexical++;
                    errors++;
                    outputExpected.write("2\n");
                    outputFail.write(line + "\t" + nfx.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals("2", expectedResultCode);
                    }
                } catch (Error err) {
                    if (err.toString().contains("Lexical")) {
                        lexical++;
                    } else if (!quiet) {
                        System.out.println("Parser err for " + line + " " + err.toString());
                    }
                    errors++;
                    outputExpected.write("3\n");
                    outputFail.write(line + "\t" + err.toString() + "\n");
                    if (expectedResultCode != null) {
                        assertEquals("3", expectedResultCode);
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
