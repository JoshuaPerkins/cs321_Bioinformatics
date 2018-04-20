import java.io.*;

public class BTreeNode {
    /**
     * BTreeNode class for ...
     *
     * @author JPerkins, DFlores
     * Date: April 13, 2018
     *
     * Class: CS 321 - Data Structures
     * Spring 2018 - Steven Cutchin
     */


    private int keyArraySize;
    private int childrenArraySize;
    private int freqArraySize;

    private long offset = 0;
    private long children[] = new long[childrenArraySize];
    long keys[] = new long[keyArraySize];
    long freq[] = new long[freqArraySize];
    long numKeys; // may not use could calucalte the number of keys based on when I find a -1
    boolean isLeaf; // may not use
    long degree; // may not use
    private long afo;

    BTreeNode(long degree) {
        keyArraySize = (int) (2*degree-1);
        freqArraySize = (int) (2*degree-1);
        childrenArraySize = (int) (2*degree);

    }

    public long readNode(RandomAccessFile myFile){
        long ret = 0;
        try {
            myFile.seek(offset);
        } catch (IOException e1) {
            //e1.printStackTrace();
            System.out.println("READ ERROR: Seek not working.");
        }
        try {
            afo = myFile.readLong();
        } catch (IOException e1) {
            //e1.printStackTrace();
            System.out.println("READ ERROR: Unable to read file.");
        }
        int i;
        for(i = 0; i < children.length; i++ ){
            try {
                children[i] = myFile.readLong();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("READ ERROR: Could not read child [" + i + "]");
            }
        }
        return ret;
    }
    public void writeNode(RandomAccessFile myFile){
        try {
            myFile.seek(offset);
        } catch (IOException e1) {
            //e1.printStackTrace();
            System.out.println("WRITE ERROR: Seek not working.");
        }
        try {
            myFile.writeLong(offset);
        } catch (IOException e1) {
            //e1.printStackTrace();
            System.out.println("WRITE ERROR: Unable to reach offset.");
        }
        int i;
        for(i = 0; i < children.length; i++){
            try {
                myFile.writeLong(children[i]);
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("WRITE ERROR: Could not write to child [" + i + "]");
            }
        }
    }
}