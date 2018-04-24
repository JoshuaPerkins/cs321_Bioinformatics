import java.io.*;

public class ParseFile {

    static void parseGbk(File gbk, int k) {

        File gbk_file = gbk;
        int subsequenceLength = k;

        try {
            // Creates the File/Buffered Readers to process gbk file
            FileReader readFile = new FileReader(gbk_file);
            BufferedReader readBuffer = new BufferedReader(readFile);
            // Variable that holds the current line of text being processed
            String currentLine;
            StringBuilder currentSequence = new StringBuilder();
            String sequence = "";

            char currentChar;
            int currentLinePosition = 0;
            int currentSequencePosition = 0;

            boolean parseRun = false;
            //boolean parseComplete = false;

            // Reads through gbk file to parse out the sequences
            while ((currentLine = readBuffer.readLine()) != null) {  // trim? toLower/Upper?  && (currentLine.length() != 0)
                if (currentLine.startsWith("ORIGIN")) {
                    System.out.println("Found ORIGIN\n");
                    parseRun = true;

                    if (currentSequence.length() > 0) {
                        currentSequence.delete(0, currentSequence.length());
                    }
                    sequence = "";
                    currentLine = "";
                    currentLinePosition = 0;
                }
                if (parseRun) {
                    if (currentLine.startsWith("//")) {
                        System.out.println("Found //\n");
                        parseRun = false;
                        // Subsequence Parsing...
                        parseSubsequences(sequence, subsequenceLength);
                    } else {

                        while (currentLinePosition < currentLine.length()) {
                            currentChar = currentLine.charAt(currentLinePosition); // toLower/Upper?
                            currentLinePosition++;

                            // Inserts character into sequence
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
//                                    // Ends subsequence and resets parsing for a new subsequence (DONT need in parsing sequence)
//                                    currentSequencePosition = 0;
//                                    currentSequence.delete(0, currentSequence.length());
//                                    continue;
                                default:
                                    // Skips white space and number characters
                                    continue;
                            }
                        }
                        currentLinePosition = 0;
                    }
                }
                if (parseRun) {
                    sequence = currentSequence.toString();
                    System.out.println("Current Sequence: " + sequence);
                }
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

}
    //#######################################
    //#######################################
    // NEEDS REMOVED -- for testing
    static int parseNum = 0;

    static void parseSubsequences(String seq, int k) {
        StringBuilder result = new StringBuilder();

        long key = 0;
        int counter = 0;
        int currentSequencePosition = 0;
        StringBuilder currentSubseq = new StringBuilder();
        while (currentSequencePosition < seq.length()) {

            while ((counter < k)) {
                currentSubseq.append(seq.charAt(currentSequencePosition));

                counter++;
                currentSequencePosition++;
            }

            String parsedSubseq = currentSubseq.toString();
            counter--;

            System.out.println("Parsed SubSequence: " + parsedSubseq);
            if (parsedSubseq.contains("n")) {
                System.out.println("SubSequence Contains 'n'\n");
                currentSubseq.deleteCharAt(0);
            }
            else {
                key = GeneConvert.subsequenceToLong(parsedSubseq, k);
                System.out.println("Parsed Key: " + key);
                System.out.println();
                // Insert subsequence key into tree
//                    myBTree.insert(key);
                currentSubseq.deleteCharAt(0);
            }
        }
        System.out.println("*** Completed Parse: " + parseNum + " ***");
        parseNum++;
    }
}
