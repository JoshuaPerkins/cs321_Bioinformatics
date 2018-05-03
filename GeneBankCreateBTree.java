import java.io.*;

/**
 * GeneBankCreateBTree class for creating a BTree from parsed DNA sequences found in gbk files.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class GeneBankCreateBTree {
    /**
     * Main method to create a BTree and parse sequences of DNA & insert the subsequences into the BTree.
     *
     * @param args The user arguments for the program.
     */
    public static void main(String[] args) {
        // Variables to define min/max arguments expected
        final int MIN_ARGS_IN = 5;
        final int MAX_ARGS_IN = 6;
        // Optimum degree based upon disk block size of 4096
        final int OPTIMUM_DEGREE = 170;
        // Debug file name
        final String DEBUG_FILE_OUT = "dump";
        // Maximum subsequence length
        final int MAX_SUBSEQUENCE_LENGTH = 31;
        // Using cache boolean
        boolean useCache = false;
        // Variables defining characteristics of program
        int treeDegree = 0;
        int subsequenceLength = 0;
        int cacheSize = 0;
        int debugLevel = 0;

        // Checks for correct argument length
        if ((args.length < MIN_ARGS_IN) || (args.length > MAX_ARGS_IN)) {
            System.out.println("ERROR: Incorrect arguments length; check usage.\n");
            // Prints program usage if incorrect
            printGeneBankCreateBTreeUse();
        }

        // Sets cache boolean
        try {
            // Set up cache usage from user input
            if (Integer.parseInt(args[0]) == 1) {
                useCache = true;
            }
            else if (Integer.parseInt(args[0]) == 0) {
                useCache = false;
            }
            else {
                System.out.println("ERROR: Check correct usage for <cache>.\n");
                printGeneBankCreateBTreeUse();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache> : " + e.getMessage() + "\n\n");
            printGeneBankCreateBTreeUse();
        }

        // Sets BTree degree
        try {
            // Set up degree from user input
            treeDegree = Integer.parseInt(args[1]);
            if (treeDegree < 0) {
                System.out.println("ERROR: Check correct usage for <degree>.\n");
                printGeneBankCreateBTreeUse();
            }
            else if (treeDegree == 0) {
                treeDegree = OPTIMUM_DEGREE;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <degree> : " + e.getMessage() + "\n\n");
            printGeneBankCreateBTreeUse();
        }

        // Sets subsequence length
        try {
            // Set up subsequence length from user input
            subsequenceLength = Integer.parseInt(args[3]);
            if ((subsequenceLength < 1) || (subsequenceLength > MAX_SUBSEQUENCE_LENGTH)) {
                System.out.println("ERROR: Check correct usage for <sequence length>.\n");
                printGeneBankCreateBTreeUse();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <sequence length> : " + e.getMessage() + "\n\n");
            printGeneBankCreateBTreeUse();
        }

        // Sets cache size
        try {
            // Set up cache size from user input
            cacheSize = Integer.parseInt(args[4]);
            if (cacheSize < 1) {
                System.out.println("ERROR: Check correct usage for <cache size>.\n");
                printGeneBankCreateBTreeUse();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
            printGeneBankCreateBTreeUse();
        }

        // Sets debug level if argument length accounts for it
        if (args.length > MIN_ARGS_IN) {
            try {
                // Set up debug level from user input
                debugLevel = Integer.parseInt(args[5]);
                // Makes sure debug level is 0 or 1
                switch (debugLevel) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        System.out.println("ERROR: Check correct usage for [<debug level>].\n");
                        printGeneBankCreateBTreeUse();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
                printGeneBankCreateBTreeUse();
            }
        }

        // Sets gbk file from user input
        File gbk_file = new File(args[2]);
        // Checks if gbk_file exists and is a file
        if ((!gbk_file.exists()) || (!gbk_file.isFile())) {
            System.out.println("ERROR: gbk file does not exist.");
            printGeneBankCreateBTreeUse();
        }

        // Creates BTree file name
        String treeFile = (gbk_file + ".btree.data." + subsequenceLength + "." + treeDegree);

        // Creates BTree
//        BTree myBTree = new BTree(treeDegree, treeFile, useCache, cacheSize);

        // Parses gbk file and adds subsequences to BTree
//        ParseFile.parseGbk(gbk_file, subsequenceLength, myBTree);

        // Checks debug level and prints to file accordingly
        try {
            if (debugLevel == 1) {
                  // my solution template
//                // Saves the stdout to be reset afterwards
//                PrintStream stdout = System.out;
//                // Creates the output stream and sets the standard output to it
//                PrintStream debugOut = new PrintStream(new FileOutputStream(DEBUG_FILE_OUT));
//                System.setOut(debugOut);
//                // Prints inorder traversal of BTree
//                myBTree.inorderPrint();
//                // Closes the output file
//                debugOut.close();
//                // Resets output to stdout
//                System.setOut(stdout);

                // Other solution
                // Creates a new dump file for debug information
                File dumpFile = new File(DEBUG_FILE_OUT);
                dumpFile.delete();          // delete handle return?
                dumpFile.createNewFile();   // create handle return?
                PrintWriter writer = new PrintWriter(dumpFile);
                // Traverses the BTree writing the frequency and subsequence information to the debug file
//                myBTree.inorderPrint(myBTree.getRoot(), writer, sequenceLength);
                // Closes the writer
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

    /**
     * Prints the correct usage for GeneBankCreateBTree.
     */
    private static void printGeneBankCreateBTreeUse() {
        //System.out.println("Usage: java GeneBankCreateBTree <degree> <gbk file> <sequence length> [<debug level>]\n");
        System.out.println("Usage: java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> <cache size> [<debug level>]\n");   // cache optional?
        System.out.println("No cache = 0; Cache = 1.\n");
        System.out.println("Degree must be a positive integer.\n");
        System.out.println("Gbk file must be an existing genome file of extension (.gbk).\n");
        System.out.println("Sequence length must be in the range of 1 to 31.\n");
        System.out.println("Cache size must be a positive integer.\n");
        System.out.println("Debug level 0 provide standard messaging; level 1 writes a file containing the inorder traversal of the BTree.\n");
        System.exit(0);
    }
}
