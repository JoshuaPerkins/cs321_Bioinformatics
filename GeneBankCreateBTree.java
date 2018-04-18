import java.io.*;

/**
 * GeneBankCreateBTree class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class GeneBankCreateBTree {

    static final long BASE_A = 0b00L;
    static final long BASE_T = 0b11L;
    static final long BASE_C = 0b01L;
    static final long BASE_G = 0b10L;

    static final char geneMap[] = {(char)BASE_A, (char)BASE_C, (char)BASE_G, (char)BASE_T};

    public static void main(String[] args) {

        final int MIN_ARGS_IN = 5;
        final int MAX_ARGS_IN = 6;

        final int OPTIMUM_DEGREE = 170;

        final String DEBUG_FILE_OUT = "dump";

        // Defined DNA Bases


        final int MAX_SEQUENCE_LENGTH = 31;

        boolean useCache = false;
        int treeDegree = 0;
        int sequenceLength = 0;
        int cacheSize = 0;
        int debugLevel = 0;

        if ((args.length < MIN_ARGS_IN) || (args.length > MAX_ARGS_IN)) {
            System.out.println("ERROR: Incorrect arguments length; check usage.\n");
            printProgramUse();
        }

        int index;

        for (index = 0; index < args.length; index++) {
            // try parsing and error dependent on index
        }

        try {
            // Set up sequence length from user input
            if (Integer.parseInt(args[0]) == 1) {
                useCache = true;
            }
            else if (Integer.parseInt(args[0]) == 0) {
                useCache = false;
            }
            else {
                System.out.println("ERROR: Check correct usage for <cache>.\n");
                printProgramUse();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache> : " + e.getMessage() + "\n\n");
            printProgramUse();
        }

        try {
            // Set up sequence length from user input
            treeDegree = Integer.parseInt(args[1]);
            if (treeDegree < 0) {
                System.out.println("ERROR: Check correct usage for <degree>.\n");
                printProgramUse();
            }
            else if (treeDegree == 0) {
                treeDegree = OPTIMUM_DEGREE;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <degree> : " + e.getMessage() + "\n\n");
            printProgramUse();
        }

        try {
            // Set up sequence length from user input
            sequenceLength = Integer.parseInt(args[3]);
            if ((sequenceLength < 1) || (sequenceLength > MAX_SEQUENCE_LENGTH)) {
                System.out.println("ERROR: Check correct usage for <sequence length>.\n");
                printProgramUse();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <sequence length> : " + e.getMessage() + "\n\n");
            printProgramUse();
        }

        try {
            // Set up sequence length from user input
            cacheSize = Integer.parseInt(args[4]);
            if (cacheSize < 1) {
                System.out.println("ERROR: Check correct usage for <cache size>.\n");
                printProgramUse();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
            printProgramUse();
        }

        if (args.length > MIN_ARGS_IN) {
            try {
                // Set up sequence length from user input
                debugLevel = Integer.parseInt(args[5]);
                switch (debugLevel) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        System.out.println("ERROR: Check correct usage for <cache size>.\n");
                        printProgramUse();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
                printProgramUse();
            }
        }

        try {
            File gbk_file = new File(args[2]);

            // Creates the File/Buffered Readers to process gbk file
            FileReader readFile = new FileReader(gbk_file);
            BufferedReader readBuffer = new BufferedReader(readFile);
            // Variable that holds the current line of text being processed
            String currentLine;
            char currentChar;
            int currentLinePosition = 0;
            int currentSequencePosition = 0;
            long currentSequence = 0;

            boolean parseRun = false;


            // Reads through gbk file to parse out the sequences
            while ((currentLine = readBuffer.readLine().trim()) != null) {  // trim? toLower/Upper?
                if (currentLine.startsWith("ORIGIN")) {
                    parseRun = true;
                }
                if (parseRun) {
                    if (currentLine.startsWith("//")) {
                        parseRun = false;
                        currentSequence = 0;
                        currentSequencePosition = 0;
                        break;
                    } else {
                        while (currentLinePosition < currentLine.length()) {
                            currentChar = currentLine.charAt(currentLinePosition); // toLower/Upper?
                            currentLinePosition++;

                            // Inserts character into sequence
                            switch (currentChar) {
                                case 'a':
                                    currentSequence = (currentSequence | (BASE_A << currentSequencePosition));
                                    currentSequencePosition += 2;
                                    break;
                                case 't':
                                    currentSequence = (currentSequence | (BASE_T << currentSequencePosition));
                                    currentSequencePosition += 2;
                                    break;
                                case 'c':
                                    currentSequence = (currentSequence | (BASE_C << currentSequencePosition));
                                    currentSequencePosition += 2;
                                    break;
                                case 'g':
                                    currentSequence = (currentSequence | (BASE_G << currentSequencePosition));
                                    currentSequencePosition += 2;
                                    break;
                                case 'n':
                                    // Ends subsequence and resets parsing for a new sequence
                                    currentSequencePosition = 0;
                                    currentSequence = 0;
                                    continue;
                                default:
                                    // Skips white space and number characters
                                    continue;
                            }
                            if (currentSequencePosition >= sequenceLength) {
                                // insert sequence
                            }
                        }
                    }
                }
                // Resets line position for new line input
                currentLinePosition = 0;
            }
            // Closes the File/Buffer Readers
            readBuffer.close();
            readFile.close();
        }
        // Catches exception if file not found
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Cannot open file : " + e.getMessage() + "\n\n");
            System.out.println("Make sure that the gbk file is in the same folder as the HashTest and other java files for this project.");
            System.exit(0);
        }
        // Catches exception if error closing readers
        catch (IOException e) {
            System.out.println("ERROR: When closing Buffer/File : " + e.getMessage() + "\n\n");
            System.exit(0);
        }

        // Checks debug level and prints to file accordingly
        try {
            if (debugLevel == 1) {
                // my solution template
//                // Saves the stdout to be reset afterwards
//                PrintStream stdout = System.out;
//                // Creates the output stream and sets the standard output to it
//                PrintStream debugOut = new PrintStream(new FileOutputStream(DEBUG_FILE_OUT));
//                System.setOut(debugOut);
//                // Closes the output file
//                debugOut.close();
//                // Resets output to stdout
//                System.setOut(stdout);

                // Other solution
                File dumpFile = new File(DEBUG_FILE_OUT);
                dumpFile.delete();          // delete handle return?
                dumpFile.createNewFile();   // create handle return?
                PrintWriter writer = new PrintWriter(dumpFile);
                //tree.inOrderPrintToWriter(tree.getRoot(),writer,sequenceLength);
                writer.close();
            }
        }
        // Catches exception if file not found
        catch (FileNotFoundException e) {
            System.out.println("Cannot open file : " + e.getMessage() + "\n\n");
        }
        catch (IOException e) {
            System.out.println("ERROR: When closing Buffer/File : " + e.getMessage() + "\n\n");
            System.exit(0);
        }
    }

    private static void printProgramUse() {
        //System.out.println("Usage: java GeneBankCreateBTree <degree> <gbk file> <sequence length> [<debug level>]\n");
        System.out.println("Usage: java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> <cache size> [<debug level>]\n");   // cache optional?
        System.exit(0);
    }

    Long subsequenceToLong(String subsequence, int k) {
        long key = 0x00;
        long temp = 0;
        int shift;

        for (int i = 0; i < k; i++) {
            shift = 2*i;
            switch (subsequence.charAt(i)) {
                case 'a':
                    temp = BASE_A << shift;
                    break;
                case 't':
                    temp = BASE_T << shift;
                    break;
                case 'c':
                    temp = BASE_C << shift;
                    break;
                case 'g':
                    temp = BASE_G << shift;
                    break;
            }
            key = key | temp;
        }
        return key;
    }

    String longToSubsequence(long key, int k) {
        StringBuilder result = new StringBuilder();
        int shift;
        long temp;
        char gene;

        for (int i = 0; i < k; i++) {
            shift = 2*i;
            temp = key;

            temp = temp >> shift;
            temp = temp & 0b11L;

            gene = geneMap[(int)temp];
            result.append(gene);
        }
        String subsequence = result.toString();
        return subsequence;
    }
}
