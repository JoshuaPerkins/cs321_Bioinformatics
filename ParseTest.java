import java.io.*;

public class ParseTest {

    // Usage <subseq length> <gbk filename>
    // k = subsequence length
    // gbk_file = filename
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        File gbk_file = new File(args[1]);
        ParseFile.parseGbk(gbk_file, k);

        }

}
