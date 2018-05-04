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
        result.fileOffset = bTreeFile.length();
        writeNode(true, result);
        return result;
    }

    public BTreeNode findLeaf() {

    }

    /**
     * Method that inserts a key into a non-full BTreeNode
     * @param key: key to be inserted
     * @param node: node that key is to be inserted in
     */
    public void insertNonFull(long key, BTreeNode node) throws IOException {
        int i = 0;
        int j = 0;
        // if the node is a leaf
        if(node.isLeaf){
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
            writeNode(false, node);
        }
        else {
            int i = node.numKeys;
            while(i > 1 && key < node.keys[i].getKey()){
                i--;
            }
            BTreeNode child = readNode(node.children[i]);
            if(child.numKeys == 2*degree-1){
                splitChild(node, child, i);
                if(key > node.keys[i].getKey()){
                    i++;
                }
            }
            insertNonFull(key, child);
        }

    }

    /**
     * Method that inserts a key into a B-Tree
     * @param pointer
     * @param key
     * @throws IOException
     */
    public void insert(long pointer, long key) throws IOException{
        int i = 0;
        BTreeNode node = readNode(pointer);
        // if node has room
        if (node.numKeys < 2 * degree - 1) {
            insertNonFull(key, node);
            return;
        }
        // if the node is full
        BTreeNode newParent = new BTreeNode(degree);
        if(node.parent != 0) {
            BTreeNode oldParent = readNode(node.parent);
            // find the child's pointer in the old parent's children array
            while (pointer != oldParent.children[i]) {
                i++;
            }
            oldParent.children[i] = nextSpot;
        }
        newParent.children[0] = pointer;
        newParent.isLeaf = false;
        newParent.numChildren = 0;
        newParent.numKeys = 1;
        writeNode(true, newParent);

//        boolean greaterThan = true;
//        // find key greater than key to be inserted
//        while(i < node.numKeys && greaterThan){
//            greaterThan = key > node.keys[i].getKey();
//            if(greaterThan) i++;
//        }
//        if(key == node.keys[i].getKey()){
//            node.keys[i].freqIncrement();
//            return;
//        }
//        int middleIndex = degree-1;
//        if(i > middleIndex){
//            middleIndex++;
//        }
//        split(node, middleIndex, key);
//        node.numKeys++;
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
    public void splitChild(BTreeNode parent, BTreeNode fullChild, int childIndex) throws IOException{
        BTreeNode newChild = new BTreeNode(degree);
        newChild.isLeaf = fullChild.isLeaf;
        // splitting the keys between the two children
        for(int i = 0; i < degree - 1; i++){
            newChild.keys[i].setKey(fullChild.keys[i+degree].getKey());
            newChild.numKeys++;
            fullChild.numKeys--;
        }
        // splitting the children pointers between the two children
        if(!fullChild.isLeaf){
            for(int j = 0; j < degree-1; j++){
                newChild.children[j] = fullChild.children[j+degree];
                newChild.numChildren++;
                fullChild.numChildren--;
            }
        }
        // shifting children pointers in the parent node
        for(int k = (int)parent.numChildren; k > childIndex; k--){
            parent.children[k] = parent.children[k-1];
        }
        // inserting newChild's pointer into parent's children array
        parent.children[childIndex] = nextSpot;
        // shifting keys in the parent node
        for(int n = (int)parent.numKeys; n > childIndex - 1; n--){
            parent.keys[n].setKey(parent.keys[n-1].getKey());
        }
        parent.keys[childIndex].setKey(fullChild.keys[degree].getKey());
        parent.numKeys++;
        writeNode(false, parent);
        writeNode(false, fullChild);
        writeNode(true, newChild);
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
    public void writeNode(boolean isNew, BTreeNode node) throws IOException{
        if(isNew){
            nextSpot += 4096;
        }
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
            return isLeaf;
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




























