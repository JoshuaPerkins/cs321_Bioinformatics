public class GeneConvert {

    static final long BASE_A = 0b00L;
    static final long BASE_T = 0b11L;
    static final long BASE_C = 0b01L;
    static final long BASE_G = 0b10L;

    static final char geneMap[] = {(char)BASE_A, (char)BASE_C, (char)BASE_G, (char)BASE_T};

    static long subsequenceToLong(String subseq, int k) {
        long key = 0x00;
        long temp = 0;
        int shift;

        for (int i = 0; i < k; i++) {
            shift = 2*i;
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
            key = key | temp;
        }
        return key;
    }

    static String longToSubsequence(long key, int k) {
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
