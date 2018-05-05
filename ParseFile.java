import java.io.*;

/**
 * ParseFile class for parsing gbk files.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */

class ParseFile {

    // Variables used when performing unit tests
    static boolean parseTest = false;   // When testing is set to true
    private static int parseNum = 1;    // Current ORIGIN // DNA sequence parse

    /**
     * Parses the gbk file into sequences in preperation to be converted to subsequences and added into the BTree.
     *
     * @param gbk_file          The file to be parsed.
     * @param subsequenceLength The substring length.
     */
    static void parseGbk(File gbk_file, int subsequenceLength, BTree myBTree) {

        try {
            // Creates the File/Buffered Readers to process gbk file
            FileReader readFile = new FileReader(gbk_file);
            BufferedReader readBuffer = new BufferedReader(readFile);
            // Variable that holds the current line of text being processed
            String currentLine;
            StringBuilder currentSequence = new StringBuilder();
            String sequence = "";
            // Variables to track current character and line index
            char currentChar;
            int currentLinePosition = 0;
            // Set to true when ORIGIN is found (set false again when // found)
            boolean parseRun = false;

            // Reads through gbk file to parse out the sequences
            while ((currentLine = readBuffer.readLine()) != null) {
                if (currentLine.startsWith("ORIGIN")) {
                    // Unit testing print statements
                    if (parseTest) {
                        System.out.println("Found ORIGIN\n");
                    }
                    // Sets start of DNA sequence
                    parseRun = true;
                    // Resets variables for use in new sequence making sure everything is empty or 0
                    if (currentSequence.length() > 0) {
                        currentSequence.delete(0, currentSequence.length());
                    }
                    sequence = "";
                    currentLine = "";
                    currentLinePosition = 0;
                }
                if (parseRun) {
                    // Checks for end of DNA sequence
                    if (currentLine.startsWith("//")) {
                        // Unit testing print statements
                        if (parseTest) {
                            System.out.println("Found //\n");
                        }
                        // Resets the parsing variable to be able to check for another sequence
                        parseRun = false;
                        // Parse subsequences to add to BTree once // is found denoting the end of a DNA sequence
                        parseSubsequences(sequence, subsequenceLength, myBTree);
                    } else {
                        // Loops while characters in the line exist
                        while (currentLinePosition < currentLine.length()) {
                            // Sets current character to char from the position in current line
                            // Characters should be in lowercase to check correctly against switch
                            currentChar = currentLine.toLowerCase().charAt(currentLinePosition);
                            // Increments line position for character indexing
                            currentLinePosition++;
                            // Inserts character into sequence if they are 'a','t','c','g', or 'n'
                            switch (currentChar) {
                                case 'a':
                                    currentSequence.append(currentChar);
                                    break;
                                case 't':
                                    currentSequence.append(currentChar);
                                    break;
                                case 'c':
                                    currentSequence.append(currentChar);
                                    break;
                                case 'g':
                                    currentSequence.append(currentChar);
                                    break;
                                case 'n':
                                    currentSequence.append(currentChar);
                                    break;
                                default:
                                    // Skips white space and number characters
                            }
                        }
                        // Resets line position to set up for next line in DNA sequence
                        currentLinePosition = 0;
                    }
                }
                // Sets the current sequence into the sequence String
                if (parseRun) {
                    sequence = currentSequence.toString();
                    // Unit testing print statements
                    if (parseTest) {
                        System.out.println("Current Sequence: " + sequence);
                    }
                }
            }
            // Closes the File/Buffer Readers
            readBuffer.close();
            readFile.close();
        }
        // Catches exception if file not found
        catch (FileNotFoundException e) {
            System.out.println("ERROR: Cannot open file : " + e.getMessage() + "\n\n");
            System.out.println("Make sure that the gbk file is in the same folder as the other java files for this project.\n");
            System.exit(0);
        }
        // Catches exception if error closing readers
        catch (IOException e) {
            System.out.println("ERROR: When closing Buffer/File : " + e.getMessage() + "\n\n");
            System.exit(0);
        }
    }

    /**
     * Parses the sequences into subsequences and adds them into the BTree.
     *
     * @param seq The DNA sequence parsed from the gbk file.
     * @param k   The subsequence length.
     */
    private static void parseSubsequences(String seq, int k, BTree myBTree) throws IOException {
        // Variables used in method
        long key;
        int counter = 0;
        int currentSequencePosition = 0;
        StringBuilder currentSubseq = new StringBuilder();

        // Loops while the sequence has more characters to process
        while (currentSequencePosition < seq.length()) {
            // Loops while the subsequence is not at the proper length
            while ((counter < k)) {
                // Appends the current character to the subsequence
                currentSubseq.append(seq.charAt(currentSequencePosition));
                // Increments the subsequence length counter
                counter++;
                // Increments the sequence position index
                currentSequencePosition++;
            }
            // Stores the parsed subsequence into a string;
            String parsedSubseq = currentSubseq.toString();
            // Decrements the subsequence length counter preparing for the next character
            counter--;

            // Unit testing print statements
            if (parseTest) {
                System.out.println("Parsed SubSequence: " + parsedSubseq);
            }
            // If the subsequence contains 'n' it is not inserted into the BTree
            if (parsedSubseq.contains("n")) {
                // Unit testing print statements
                if (parseTest) {
                    System.out.println("SubSequence Contains 'n'\n");
                }
                // Deletes first character from subsequence to prepare for next subsequence
                currentSubseq.deleteCharAt(0);
            } else {
                // Parsed subsequence converted to key format
                key = GeneConvert.subsequenceToLong(parsedSubseq, k);

                // Unit testing print statements
                if (parseTest) {
                    // Print out current key value
                    System.out.println("Parsed Key: " + key);
                    // Print out conversion to check back to String
                    String revert = GeneConvert.longToSubsequence(key, k);
                    System.out.println("Reverted Key (String): " + revert);
                    System.out.println();
                }
                // Insert subsequence key into tree if not testing
                if (!parseTest) {
                    myBTree.insert(myBTree.getRoot().fileOffset, key);
                }
                // Deletes first character from subsequence to prepare for next subsequence
                currentSubseq.deleteCharAt(0);
            }
        }
        // Unit testing print statements
        if (parseTest) {
            // Prints number of completed parses (current ORIGIN // DNA sequence)
            System.out.println("*** Completed Parse: " + parseNum + " ***");
            parseNum++;
        }
    }

    /**
     * Parses the key length and degree from a BTree file name.
     *
     * @param btree_name The name of the BTree file.
     * @param type The expected return value type; either subsequence length or degree.
     * @return The parsed integer value from the filename.
     */
    static int parseKeyDegree(String btree_name, int type) {
        StringBuilder currentParse = new StringBuilder();
        int value = 0;

        // Variables to parse key length and degree
        char currentChar;
        int currentLinePosition = 0;
        int sectionCount = 0;

        // Continues while more characters are present in the btree file name
        while (currentLinePosition < btree_name.length()) {
            // Sets current character from index
            currentChar = btree_name.charAt(currentLinePosition);
            currentLinePosition++;

            // Parses the sequence length
            if (type == 0) {
                if ((sectionCount == 4) && (currentChar != '.')) {
                    currentParse.append(currentChar);
                }
            }
            // Parses the degree
            if (type == 1) {
                if (sectionCount == 5) {
                    currentParse.append(currentChar);
                }
            }
            // Counts the name sections denoted by '.'
            if (currentChar == '.') {
                sectionCount++;
            }
        }

        try {
            // Parses the integer values from the key length and degree strings
            value = Integer.parseInt(currentParse.toString());
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Cannot parse value of key length/degree from BTree filename : " + e.getMessage() + "\n\n");
            System.exit(0);
        }
        // returns value based on type input
        return value;
    }
}
