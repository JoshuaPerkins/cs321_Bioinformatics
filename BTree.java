/**
 * BTree class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
import java.io.*;


public class BTree{
    // Class Variables
    long degree;    // degree of the B-tree
    long nodeSize;  // size of each node in the B-tree
    BTreeNode root; // root node of the B-tree
    RandomAccessFile bTreeFile; // binary file the B-tree is saved in

    Cache bTreeCache = new Cache(500); // initial cache size: df

    /**
     * Constructor method for a B-tree
     * @param degree: degree of the B-tree
     * @param fileName: name of the file where the B-tree is to be stored
     */
    public BTree(int degree, String fileName) throws IOException{
        bTreeFile = new RandomAccessFile(fileName, "rw");
        root = createBTN(degree, bTreeFile);
        bTreeCache.addObject(root); // adding root to cache: df
    }

    /**
     * Method to create and add a node to a B-tree
     * @param myFile: B-Tree file where the node is created
     * @return result: node that is created
     */
    public BTreeNode createBTN(int degree, RandomAccessFile myFile) throws IOException{
        BTreeNode result = new BTreeNode(degree);
        result.numChildren = 0; //df
        result.numKeys = 0; //df
        result.isLeaf = true; //df
        long fileOffset = myFile.length();
        result.setFileOffset(fileOffset, myFile);
        result.writeNode(myFile);
        bTreeCache.addObject(result); // adding BTN to cache :df
        return result;
    }

    /**
     * Method that inserts a key into a B-tree
     * @param key: key to be inserted
     */
    public void insert(long key){

    }

    /**
     * Method that deletes a key from a B-tree
     * @param key: key to be deleted
     */
    public void delete(long key){

    }

    /**
     * Method that finds the frequency of a specific key
     * @param key: long value
     * @return frequency: frequency of the key within the B-tree
     */
    public long search(long key) {
       long frequency = 0;
       return frequency;
    }

    /**
     * Method that splits a B-tree node in half
     */
    public void split(){

    }
}
