
/**
 * BTreeNode class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
import java.io.*;

public class BTreeNode {
    // class variables
    long fileOffset;
    int degree;
    long children[];
    long keys[];
    long freqency[];
    int numKeys;
    boolean isLeaf;

    /**
     * Constructor method for a BTreeNode
     */
    public BTreeNode(int degree){
        if(degree > 0) {
            this.degree = degree;
        }
        else{
            this.degree = 170; // default degree
        }
        this.children = new long[2 * degree];
        this.keys = new long[2 * degree - 1];
        this.freqency = new long[2 * degree - 1];
    }

    /**
     * Checking node for children
     * @param
     * @param index
     * @return
     */
    public boolean isLeaf(int index){
        if(this.children[index] == 0){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Method for writing a node to a binary file
     * @param myFile: file to be written to
     */
    public void writeNode(RandomAccessFile myFile) throws IOException{
        myFile.seek(fileOffset);
        myFile.writeLong(fileOffset);
        int i;
        for(i=0; i < this.children.length; i++){
            myFile.writeLong(children[i]);
        }
    }

    /**
     * Method for reading a node from a binary file
     * @param myFile: file to be read from
     */
    public long readNode(RandomAccessFile myFile) throws IOException{
        myFile.seek(fileOffset);
        long afo = myFile.readLong();
        int i;
        for(i=0; i < this.children.length; i++){
            this.children[i] = myFile.readLong();
        }
        return afo;
    }

    /**
     * Method for setting the file offset in a B-Tree node
     * @param fileOffset: desired file offset
     */
    public void setFileOffset(long fileOffset, RandomAccessFile myFile) throws IOException{
        this.fileOffset = fileOffset;
        myFile.seek(fileOffset);
    }
    

    public long getFrequency(){
        return this.freqency[1];
    }
}
