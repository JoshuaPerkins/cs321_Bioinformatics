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
    int degree;    // degree of the B-tree
    long nodeSize;  // size of each node in the B-tree
    BTreeNode root; // root node of the B-tree
    RandomAccessFile bTreeFile; // binary file the B-tree is saved in

    /**
     * Constructor method for a B-tree
     * @param degree: degree of the B-tree
     * @param fileName: name of the file where the B-tree is to be stored
     */
    public BTree(int degree, String fileName) throws IOException{
        bTreeFile = new RandomAccessFile(fileName, "rw");
        root = createBTN(degree);
        this.degree = degree;
    }

    /**
     * Method to create and add a node to a B-tree
     * @param degree: degree of the B-tree
     * @return result: node that is created
     */
    public BTreeNode createBTN(int degree) throws IOException{
        BTreeNode result = new BTreeNode(degree);
        long fileOffset = bTreeFile.length();
        result.setFileOffset(fileOffset, bTreeFile);
        result.writeNode(bTreeFile);
        return result;
    }

    /**
     * Method that inserts a key into a B-tree
     * @param key: key to be inserted
     */
    public void insert(long key){
        BTreeNode leaf = findLeaf();
        int i = 0;
        int j = 0;
        // if the node has space
        if(leaf.numKeys < 2*degree - 1){
            boolean greaterThan = true;
            // find key greater than key to be inserted
            while(i < leaf.numKeys && greaterThan){
                greaterThan = key > leaf.keys[i];
                if(greaterThan) i++;
            }
            for(j = leaf.numKeys; j > i; j--){
                leaf.keys[j] = leaf.keys[j-1];
            }
            leaf.keys[i] = key;
        }
        // node is full
        else {
            boolean greaterThan = true;
            // find key greater than key to be inserted
            while(i < leaf.numKeys && greaterThan){
                greaterThan = key > leaf.keys[i];
                if(greaterThan) i++;
            }
            int middleIndex = degree-1;
            if(i > middleIndex){
                middleIndex++;
                i--;
            }
            split(leaf, middleIndex, key);
        }
        leaf.numKeys++;
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
    public void split(BTreeNode node, int middleIndex, long insertKey){
        // move middleIndex key to parent
        if(node.hasParent()) {
            BTreeNode parent = node.getParent();
            if (parent.numKeys < 2 * degree - 1) {

            }
            else {

            }
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
}




























