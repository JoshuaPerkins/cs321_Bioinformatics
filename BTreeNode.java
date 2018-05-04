
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
    long parent[]; // parent pointer
    long children[]; // array of children
    long keys[]; // array of keys
    long freqency[];
    long numKeys; // key count
    long numChildren; // children count
    boolean isLeaf; // leaf checker

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
        this.children = new long[2 * degree]; // max number of children
        this.keys = new long[2 * degree - 1]; // max number of keys
        this.freqency = new long[2 * degree - 1]; // array to hold frequency
    }

    /**
     * Checking node for children
     * @return true/false
     */
    public boolean isLeaf(){
        if(this.isLeaf){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checking for max keys
     * @return true/false
     */
    public boolean isFull(){
        if(numKeys == this.keys.length){
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

    public long getFreqency(int i){
        return freqency[i];
    }

}
