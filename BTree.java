/**
 * BTree class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
import javafx.scene.Parent;

import java.io.*;

public class BTree {
    // Class Variables
    int degree;    // degree of the B-tree
    long fileOffset;
    long nextSpot;
    long nodeSize = 4096;  // size of each node in the B-tree
    BTreeNode root; // root node of the B-tree
    RandomAccessFile bTreeFile; // binary file the B-tree is saved in

    /**
     * Constructor method for a B-tree
     *
     * @param degree:   degree of the B-tree
     * @param fileName: name of the file where the B-tree is to be stored
     */
    public BTree(int degree, String fileName) throws IOException {
        bTreeFile = new RandomAccessFile(fileName, "rw");
        root = createBTN(degree);
        this.degree = degree;
    }

    /**
     * Method to create and add a node to a B-tree
     *
     * @param degree: degree of the B-tree
     * @return result: node that is created
     */
    public BTreeNode createBTN(int degree) throws IOException {
        BTreeNode result = new BTreeNode(degree);
        long fileOffset = bTreeFile.length();
        result.setFileOffset(fileOffset, bTreeFile);
        result.writeNode(bTreeFile);
        return result;
    }

    public BTreeNode findLeaf() {

    }

    /**
     * Method that inserts a key into a B-tree
     *
     * @param key: key to be inserted
     */
    public void insertNonFull(long key, BTreeNode node) throws IOException {
        int i = 0;
        int j = 0;
        // if the node has space
        boolean greaterThan = true;
        // find key greater than key to be inserted
        while (i < node.numKeys && greaterThan) {
            greaterThan = key > node.keys[i].getKey();
            if (greaterThan) i++;
        }
        if (key == node.keys[i].getKey()) {
            node.keys[i].freqIncrement();
            return;
        }
        for (j = node.numKeys; j > i; j--) {
            node.keys[j] = node.keys[j - 1];
        }
        node.keys[i].setKey(key);
        node.numKeys++;
    }

    public void insert(long pointer, long key) throws IOException{
        BTreeNode node = readNode(pointer);
        // node is full
         if (node.numKeys < 2 * degree - 1) {

         }
        boolean greaterThan = true;
        // find key greater than key to be inserted
        while(i < node.numKeys && greaterThan){
            greaterThan = key > node.keys[i].getKey();
            if(greaterThan) i++;
        }
        if(key == node.keys[i].getKey()){
            node.keys[i].freqIncrement();
            return;
        }
        int middleIndex = degree-1;
        if(i > middleIndex){
            middleIndex++;
        }
        split(node, middleIndex, key);
        node.numKeys++;
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
     * Method that splits a B-tree node in half and pushes middle to parent
     */
    public void split(BTreeNode node, int middleIndex, long insertKey) throws IOException{
        // move middleIndex key to parent
        long parentPointer = node.parent;
        // if we are splitting a non-root node
        if(parentPointer != 0) {
            setFileOffset(parentPointer);
            BTreeNode parent = readNode(parentPointer);
            insert(insertKey, parent);
            BTreeNode child = new BTreeNode(degree);
            int i = 0;
            int j = 0;
            for(i = middleIndex; i < node.keys.length-1; i++){
                child.keys[j] = node.keys[i];
                j++;
            }

        }
        // call insert(insertKey)
    }

    /**
     * Method for setting the file offset in a B-Tree node
     * @param fileOffset: desired file offset
     */
    public void setFileOffset(long fileOffset) throws IOException{
        this.fileOffset = fileOffset;
        bTreeFile.seek(fileOffset);
    }

    /**
     * Method for writing a node to a binary file
     * @param node: node to be written to RAF
     */
    public void writeNode(BTreeNode node) throws IOException{
        bTreeFile.seek(fileOffset);
        bTreeFile.writeLong(fileOffset);
//        int i;
//        for(i=0; i < this.children.length; i++){
//            bTreeFile.writeLong(children[i]);
//        }
    }

    /**
     * Method for reading a node from a binary file
     * @param pointer: pointer to node to be read from RAF
     */
    public BTreeNode readNode(long pointer) throws IOException{
        bTreeFile.seek(fileOffset);
        long afo = bTreeFile.readLong();
        int i;
//        for(i=0; i < this.children.length; i++){
//            this.children[i] = bTreeFile.readLong();
//        }
//        return afo;
    }

    private class BTreeNode {
        // class variables
        long fileOffset;
        int degree;
        long parent; // parent pointer
        long children[]; // array of children
        TreeObject keys[]; // array of keys and frequencies
        int numKeys; // key count
        long numChildren; // children count
        boolean isLeaf; // leaf checker

        /**
         * Constructor method for a BTreeNode
         */
        public BTreeNode(int degree){
            if(degree > 0 && degree <= 128) {
                this.degree = degree;
            }
            else {
                this.degree = 128; // default degree
            }
            this.children = new long[2 * degree]; // max number of children
            this.keys = new TreeObject[2 * degree - 1]; // max number of keys
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
    }
}




























