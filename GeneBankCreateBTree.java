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

    public static void main(String[] args) {

        // Defined DNA Bases
        final long BASE_A = 0b00L;
        final long BASE_T = 0b11L;
        final long BASE_C = 0b01L;
        final long BASE_G = 0b10L;

        final int MAX_SEQUENCE_LENGTH = 31;

        File gbk_file = new File(args[2]);

        try {
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
            while ((currentLine = readBuffer.readLine().trim()) != null) {  // toLower/Upper?
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

                            // Inserts into sequence
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
                                    // Ends subsequence and continues parse
                                    currentSequencePosition = 0;
                                    currentSequence = 0;
                                    continue;
                                default:
                                    continue;
                            }
                        }
                    }
                }


            }
            // Closes the File/Buffer Readers
            readBuffer.close();
            readFile.close();
        }
        // Catches exception if file not found
        catch(FileNotFoundException e) {
            System.out.println("Cannot open file : " + e.getMessage() + "\n\n");
            System.out.println("Make sure that the gbk file is in the same folder as the HashTest and other java files for this project.");
        }
        // Catches exception if error closing readers
        catch (IOException e) {
            System.out.println("Error closing Buffer/File : " + e.getMessage() + "\n\n");
        }

    }

}
