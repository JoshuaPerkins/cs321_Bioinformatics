import java.io.*;
import java.util.ArrayList;

/**
 * GeneBankSearch class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class GeneBankSearch {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        final int MIN_ARGS_IN = 4;
        final int MAX_ARGS_IN = 5;

        boolean useCache = false;
        int cacheSize = 0;
        int debugLevel = 0;

        int subsequenceLength;


        if ((args.length < MIN_ARGS_IN) || (args.length > MAX_ARGS_IN)) {
            System.out.println("ERROR: Incorrect arguments length; check usage.\n");
            printGeneBankSearchUse();
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
                printGeneBankSearchUse();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache> : " + e.getMessage() + "\n\n");
            printGeneBankSearchUse();
        }

        try {
            // Set up sequence length from user input
            cacheSize = Integer.parseInt(args[3]);
            if (cacheSize < 1) {
                System.out.println("ERROR: Check correct usage for <cache size>.\n");
                printGeneBankSearchUse();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
            printGeneBankSearchUse();
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
                        printGeneBankSearchUse();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Incorrect input for <cache size> : " + e.getMessage() + "\n\n");
                printGeneBankSearchUse();
            }
        }

        try {
            File btree_file = new File(args[1]);
            File query_file = new File(args[2]);

            // Creates the File/Buffered Readers to process gbk file
            FileReader readFile = new FileReader(query_file);
            BufferedReader readBuffer = new BufferedReader(readFile);
            // Variable that holds the current line of text being processed
            String currentLine;
            ArrayList<String> queryArray = new ArrayList<String>();

            char currentChar;
            int currentLinePosition = 0;
            int currentSequencePosition = 0;
            long currentSequence = 0;

            boolean parseRun = false;

            while ((currentLine = readBuffer.readLine()) != null) {  // trim? toLower/Upper?
                    queryArray.add(currentLine);
            }

            subsequenceLength = queryArray.get(0).length();


            for (int i = 0; i < queryArray.size(); i++) {
                long key = GeneConvert.subsequenceToLong(queryArray.get(i), subsequenceLength);

                // Searches for key in BTree and prints frequency information
//                if (BTree.find(key)) {
//                    //Print frequency info
//                    printFrequencyInfo();
//                }
            }



            // Closes the File/Buffer Readers
            readBuffer.close();
            readFile.close();
        }
        // Catches exception if file not found
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Cannot open file : " + e.getMessage() + "\n\n");
            System.out.println("Make sure that the btree / query file is in the same folder as the HashTest and other java files for this project.");
            System.exit(0);
        }
        // Catches exception if error closing readers
        catch (IOException e) {
            System.out.println("ERROR: When closing Buffer/File : " + e.getMessage() + "\n\n");
            System.exit(0);
        }

    }

    /**
     *
     */
    private static void printGeneBankSearchUse() {
        //System.out.println("Usage: java GeneBankSearch <degree> <gbk file> <sequence length> [<debug level>]\n");
        System.out.println("Usage: java GeneBankSearch <cache> <btree file> <query file> <cache size> [<debug level>]\n");   // cache optional?
        System.exit(0);
    }

    /**/
    private static void printFrequencyInfo() {
        System.out.println();
    }

}
