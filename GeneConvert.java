/**
 * GeneConvert class for converting the subsequences & keys.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
class GeneConvert {

    // Variables defining the characters used in DNA parsing; set to bit format
    private static final long BASE_A = 0b00L;
    private static final long BASE_T = 0b11L;
    private static final long BASE_C = 0b01L;
    private static final long BASE_G = 0b10L;
    // Array holding characters at locations denoted by bit their appropriate bit values
    private static final char geneMap[] = {'a', 'c', 'g', 't'};

    /**
     * Converts a subsequence to a long to be used as a key.
     *
     * @param subseq The String subsequence to be transformed into a key.
     * @param k The length of the subsequence.
     * @return The key that has been converted as a long.
     */
    static long subsequenceToLong(String subseq, int k) {
        // Variables used to hold conversion values
        long key = 0x00;
        long temp = 0;
        int shift;

        // Loops through the subsequence one character at a time
        for (int i = 0; i < k; i++) {
            // Sets up bit shift value to assign appropriate position in key
            shift = 2*i;
            // Converts current character to a value shifted to the correct position
            switch (subseq.charAt(i)) {
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
            // Adds the new character into the key at the correct position (Bitwise OR)
            key = key | temp;
        }
        // Returns the final converted substring as a key value
        return key;
    }

    /**
     * Converts a key into a String to represent the subsequence.
     *
     * @param key The long key to be transformed into a subsequence.
     * @param k The length of the subsequence.
     * @return The subsequence that has been converted as a String.
     */
    static String longToSubsequence(long key, int k) {
        // Variables used to hold conversion values
        StringBuilder result = new StringBuilder();
        int shift;
        long temp;
        char gene;

        // Loops through key until subsequence length is met
        for (int i = 0; i < k; i++) {
            // Sets up bit shift value to assign appropriate position in the key to read
            shift = 2*i;
            // Saves the current full key into temp
            temp = key;
            // Saves temp as a new shifted value to localize the current char bits
            temp = temp >> shift;
            // Sets temp to char value contained in first two bits (Bitwise AND)
            temp = temp & 0b11L;
            // Sets character for current key position based on temp value
            gene = geneMap[(int)temp];
            // Appends character to subsequence string builder
            result.append(gene);
        }
        // Returns the resulting subsequence
        return result.toString();
    }
}
