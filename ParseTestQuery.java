import java.io.*;

/**
 * ParseTestQuery class for performing tests to see if the substrings are being parsed correctly.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class ParseTestQuery {

    /**
     * Tests the parsing of BTree file information from the filename & substrings from query files.
     * Usage <BTree filename> <query filename>
     *
     * @param args The arguments passed to the tester by the user.
     */
    public static void main(String[] args) {
        int subsequenceLengthCheck;
        int treeDegree;

        // Sets variable to print testing information
        ParseFile.parseTest = true;

        // Sets file to parse from user input
        String btree_filename = args[0];
        File query_file = new File(args[1]);

        long key;
        int subsequenceLength = 0;
        String currentLine;

        try {
            FileReader readFile = new FileReader(query_file);
            BufferedReader readBuffer = new BufferedReader(readFile);

            System.out.println("\nTesting Query File Parsing:\n");
            while ((currentLine = readBuffer.readLine()) != null) {
                currentLine = currentLine.toLowerCase();
                key = GeneConvert.subsequenceToLong(currentLine, currentLine.length());
                subsequenceLength = currentLine.length();
                System.out.println("Parsed SubSequence: " + currentLine);
                System.out.println("Parsed Key: " + key);
                System.out.println();
            }

            // Closes the File/Buffer Readers
            readBuffer.close();
            readFile.close();
        }
        // Catches exception if file not found
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Cannot open file : " + e.getMessage() + "\n\n");
            System.out.println("Make sure that the btree / query file is in the same folder as the other java files for this project.");
            System.exit(0);
        }
        // Catches exception if error closing readers
        catch (IOException e) {
            System.out.println("ERROR: When closing Buffer/File : " + e.getMessage() + "\n\n");
            System.exit(0);
        }

        // Sets the subsequence length and degree from btree name
        subsequenceLengthCheck = ParseFile.parseKeyDegree(btree_filename, 0);
        treeDegree = ParseFile.parseKeyDegree(btree_filename, 1);

        System.out.println("\nTesting BTree Information Parsing:\n");
        System.out.println("Parsed BTree subsequence length: " + subsequenceLengthCheck);
        System.out.println("Parsed BTree degree: " + treeDegree);

        if (subsequenceLength != subsequenceLengthCheck) {
            System.out.println("ERROR: BTree & query file subsequence lengths (k) do not match.\n");
        }
    }
}
