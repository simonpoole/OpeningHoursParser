package ch.poole.openinghoursparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

/**
 * Super simple line by line comparison of two files with the input data in the third input
 * 
 * @author Simon
 *
 */
public class Diff {

    /**
     * Main
     * 
     * @param args reference file - new file - input file - optional output file
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        List<String> o1 = readLines(args[0]);
        List<String> o2 = readLines(args[1]);
        List<String> in = readLines(args[2]);

        PrintStream os = System.out;
        if (args.length == 4) {
            try {
                os = new PrintStream(new FileOutputStream(args[3]));
            } catch (FileNotFoundException e) {
                System.err.println("File not found " + e.getMessage());
                System.exit(1);
            }
        }

        final int size = o1.size();
        if (size != o2.size() && size != in.size()) {
            System.err.println("File sizes differ " + size + " " + o2.size() + " " + in.size());
            System.exit(1);
        }

        for (int i = 0; i < size; i++) {
            final String s1 = o1.get(i);
            final String s2 = o2.get(i);
            if (!s1.equals(s2)) {
                os.println("Line " + (i + 1) + "\t" + s1 + "\t" + s2 + "\t" + in.get(i));
            }
        }
    }

    /**
     * Read the contents of a file in to a List of String
     * 
     * @param file input file path
     * @return a List of String
     */
    private static List<String> readLines(@NotNull String file) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Couldn't read " + file + " " + e.getMessage());
            System.exit(1);
        }
        return lines;
    }
}
