import java.io.*;

/**
 * ParseTestGbk class for performing tests to see if the substrings are being parsed correctly.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class ParseTestGbk {

    /**
     * Tests the parsing of substrings from gbk files.
     * Usage <subseq length> <gbk filename>
     * Where k = subsequence length & gbk_file = filename
     *
     * @param args The arguments passed to the tester by the user.
     */
    public static void main(String[] args) {
        // Sets variable to print testing information
        ParseFile.parseTest = true;

        // Sets substring length
        int k = Integer.parseInt(args[0]);

        // Sets file to parse from user input
        File gbk_file = new File(args[1]);

        // Parses subsequences from found DNA sequence strings
        ParseFile.parseGbk(gbk_file, k, null);
    }
}
