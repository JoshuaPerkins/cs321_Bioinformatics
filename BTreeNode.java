
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
    long parent; // parent pointer
    long children[]; // array of children
    TreeObject treeObject[]; // tree object array
    int numKeys; // key count
    int numChildren; // children count
    boolean isLeaf; // leaf checker

    /**
     * Constructor for BTreeNode
     * key, and frequency are contained in TreeObject
     * @param degree
     */
    public BTreeNode(int degree){
        this.degree = degree;
        this.treeObject = new TreeObject[2 * degree - 1]; // treeObject holds the keys and frequency
        this.children = new long[2 * degree]; // max number of children
    }

    /**
     * Retrieve tree object from a certain index.
     * @param index
     * @return
     */
    public TreeObject getTreeObject(int index){
        return treeObject[index];
    }

    /**
     * returns number of keys
     * @return
     */
    public int getNumKeys(){
        return numKeys;
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
        if(numKeys == this.treeObject.length){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @return returns parents
     */
    public long getParent(){
        return this.parent;
    }

    /**
     * Setting the file offset to fileOS
     * @param fileOS
     */
    public void setFileOffset(int fileOS){
        this.fileOffset = fileOS;
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
    public BTreeNode readNode(RandomAccessFile myFile) throws IOException{
        BTreeNode newNode = null;
        // checking things and stuff

        // checking cache for node before anything else.
        if(bTreeCache != null){
            newNode = readNode(offset);
            if(newNode != null){
                return newNode;
            }
        }


        newNode = new BTreeNode(this.degree); //default degree, change.
        TreeObject obj = null;



        int i = 0;
        return newNode;
        //myFile.seek(fileOffset);
        //long afo = myFile.readLong();
        //int i;
        //for(i=0; i < this.children.length; i++){
         //   this.children[i] = myFile.readLong();
        //}
        //return afo;
    }

    /**
     * Method for setting the file offset in a B-Tree node
     * @param fileOffset: desired file offset
     */
    public void setFileOffset(long fileOffset, RandomAccessFile myFile) throws IOException{
        this.fileOffset = fileOffset;
        myFile.seek(fileOffset);
    }

    /**
     * returns node offset
     * @return node offset
     */
    public long getOffset(){
        return this.fileOffset;
    }



    /**
     * Prints all the keys
     */
    public void printKeys(){
        int i = 0;
        for(i = 0; i < keys.length; i++ ){
            System.out.print(keys[i]+" ");
        }
    }
}
