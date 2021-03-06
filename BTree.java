import java.io.*;

/**
 * BTree class for implementing a BTree that stores GeneBank data.
 *
 * @author LDirkes
 * Date: May 4, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */

public class BTree {
    // Class Variables
    int degree;    // degree of the B-tree
    long fileOffset;
    int nextSpot = 4096;
    int nodeSize = 4096;  // size of each node in the B-tree
    BTreeNode root; // root node of the B-tree
    RandomAccessFile bTreeFile; // binary file the B-tree is saved in

    /**
     * Constructor method for a B-tree
     *
     * @param degree:   degree of the B-tree
     * @param fileName: name of the file where the B-tree is to be stored
     */
    public BTree(int degree, String fileName, boolean useCache, int cacheSize) throws IOException {
        bTreeFile = new RandomAccessFile(fileName, "rw");
        // Unsure of use commented for now
//        bTreeFile.setLength(8192);
//        bTreeFile.writeLong(1111111);
        root = createBTN(degree);
        root.parent = 0;
        this.degree = degree;
    }

    /**
     * Returns rood node.
     *
     * @return root node
     */
    public BTreeNode getRoot() {
        return root;
    }

    /**
     * Method to create and add a node to a B-tree
     *
     * @param degree: degree of the B-tree
     * @return result: node that is created
     */
    public BTreeNode createBTN(int degree) throws IOException {
        BTreeNode result = new BTreeNode(degree);
        result.fileOffset = 4096;
        result.isLeaf = true;
        result.keys[0] = new TreeObject(0);
        result.keys[0].setFreq(0);
        result.numKeys = 0;
        result.numChildren = 0;
        writeNode(true, result);
        return result;
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
            if(i == node.numKeys){
                node.keys[i] = new TreeObject(key);
                node.numKeys++;
                writeNode(false, node);
                return;
            }
            if (key == node.keys[i].getKey()) {
                node.keys[i].freqIncrement();
                writeNode(false, node);
                return;
            }
            for (j = node.numKeys; j > i; j--) {
                node.keys[j] = node.keys[j - 1];
            }
            node.keys[i] = new TreeObject(key);
            node.numKeys++;
            writeNode(false, node);
        }
        else {
            i = node.numKeys - 1;
            while(i > 0 && key < node.keys[i].getKey()){
                i--;
            }
            // MUST CHECK FOR SAME ITEM TO INCREMENT FREQUENCY OF NON LEAF NODE ELEMENTS
            if (i >= 0 && key == node.keys[i].getKey()) {
                node.keys[i].freqIncrement();
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
    public void insert(int pointer, long key) throws IOException{
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
        newParent.numKeys = 0;
        writeNode(true, newParent);
        splitChild(newParent, node, 0);
        insertNonFull(key, newParent);
        return;
    }

    /**
     * Method that finds the frequency of a specific key
     * @param key: long value
     * @return frequency: frequency of the key within the B-tree
     */
    public int search(long key, int pointer) throws IOException{
       int frequency = 0;
       BTreeNode node = readNode(pointer);
       int i = 0;
       boolean greaterThan = true;
       boolean found = false;
       while(i < node.numKeys && greaterThan && !found){
           if(key == node.keys[i].getKey()){
               frequency = node.keys[i].getFreq();
               found = true;
           }
           greaterThan = key > node.keys[i].getKey();
           i++;
       }
       if(!found && !node.isLeaf()){
           frequency = search(key,node.children[i]);
       }
       return frequency;
    }

    /**
     * Method that splits a B-tree node in half and pushes middle to parent
     */
    public void splitChild(BTreeNode parent, BTreeNode fullChild, int childIndex) throws IOException{
        BTreeNode newChild = new BTreeNode(degree);
        newChild.isLeaf = fullChild.isLeaf;
        newChild.parent = fullChild.parent;
        // splitting the keys between the two children
        for(int i = 0; i < degree - 1; i++){
            newChild.keys[i] = fullChild.keys[i+degree];
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
        for(int k = parent.numChildren; k > childIndex; k--){
            parent.children[k] = parent.children[k-1];
        }
        // inserting newChild's pointer into parent's children array
        parent.children[childIndex] = nextSpot;
        parent.numChildren++;
        // shifting keys in the parent node
        for(int n = parent.numKeys; n > childIndex; n--){
            parent.keys[n].setKey(parent.keys[n-1].getKey());
        }
        parent.keys[childIndex] = fullChild.keys[degree];
        newChild.parent = fullChild.parent;
        parent.numKeys++;
        writeNode(false, parent);
        writeNode(false, fullChild);
        writeNode(true, newChild);
    }

    /**
     * Method for setting the file offset in a B-Tree node
     *
     * @param fileOffset: desired file offset
     */
    public void setFileOffset(long fileOffset) throws IOException{
        this.fileOffset = fileOffset;
        bTreeFile.seek(fileOffset);
    }

    /**
     * Method for writing a node to a binary file
     *
     * @param node: node to be written to RAF
     */
    public void writeNode(boolean isNew, BTreeNode node) throws IOException{
        int location;
        if(isNew){
            location = nextSpot;
            nextSpot += 4096;
            if(bTreeFile.length() < location + 4096){
                bTreeFile.setLength(location + 4096);
            }
        }
        else {
            location = node.fileOffset;
        }
        bTreeFile.seek(location);
        bTreeFile.writeInt(node.parent);
        bTreeFile.writeInt(node.numChildren);
        bTreeFile.writeInt(node.numKeys);
        bTreeFile.writeInt(node.fileOffset);
        bTreeFile.writeBoolean(node.isLeaf);
        bTreeFile.seek(location+24);
        for(int i=0; i < node.numKeys; i++){
            bTreeFile.writeLong(node.keys[i].getKey());
            bTreeFile.writeInt(node.keys[i].getFreq());
        }
        bTreeFile.seek(location+3064);
        for(int j=0; j < node.numChildren; j++){
            bTreeFile.writeInt(node.children[j]);
        }
        // Resets root node so that the program is able to see it
        // When removed the root node is lost; Unsure of reasoning
        // Cannot traverse through children for some reason
        if (node.fileOffset == 4096) {
            root = node;
        }
    }

    /**
     * Method for reading a node from a binary file
     *
     * @param pointer: pointer to node to be read from RAF
     */
    public BTreeNode readNode(int pointer) throws IOException{
        BTreeNode node = new BTreeNode(degree);
        bTreeFile.seek(pointer);
        node.parent = bTreeFile.readInt();
        node.numChildren = bTreeFile.readInt();
        node.numKeys = bTreeFile.readInt();
        node.fileOffset = bTreeFile.readInt();
        node.isLeaf = bTreeFile.readBoolean();
        bTreeFile.seek(pointer+24);
        for(int i=0; i < node.numKeys; i++){
            node.keys[i] = new TreeObject(bTreeFile.readLong());
            node.keys[i].setFreq(bTreeFile.readInt());
        }
        bTreeFile.seek(pointer+3064);
        for(int j=0; j < node.numChildren; j++){
            node.children[j] = bTreeFile.readInt();
        }
        return node;
    }

    /**
     * Inorder Traversal of the BTree; prints the frequency and key information from the nodes.
     *
     * @param node The BTree node to traverse and write information.
     * @param k The subsequence length.
     * @param writer The PrintWriter
     * @throws IOException
     */
    public void inorderDebugPrinter(BTreeNode node, int k, PrintWriter writer) throws IOException {
        for (int i = 0; i < node.numKeys; i++){
            // Print statements for debugging while it checks the node information
            System.out.println(node.numKeys);
            System.out.print("\n" + node.keys[i].getFreq() + " ");
            System.out.print(GeneConvert.longToSubsequence(node.keys[i].getKey(), k) + " ");
            System.out.print(node.keys[i].getKey() + "\n");
            // Printer writer to file for node information
            writer.print(node.keys[i].getFreq()+ " ");
            writer.println(GeneConvert.longToSubsequence(node.keys[i].getKey(), k));
        }
        // Currently prints child more than once then ends if (|| root == node) is added
        // Checks for leafs and recursively calls down to print other information
        if ((!node.isLeaf())){
            for (int i = 0; i < node.numKeys + 1; ++i){
                int offset = node.children[i];
                BTreeNode x = readNode(offset);
                inorderDebugPrinter(x, k, writer);
                if (i < node.numKeys) {
                    writer.print(node.keys[i].getFreq() + " ");
                    writer.println(GeneConvert.longToSubsequence(node.keys[i].getKey(),k));
                }
            }
        }
    }

    /**
     * BTreeNode class that helps the BTree use/creation.
     */
    class BTreeNode {
        // class variables
        int fileOffset;
        int degree;
        int parent; // parent pointer
        int children[]; // array of children
        TreeObject keys[]; // array of keys and frequencies
        int numKeys; // key count
        int numChildren; // children count
        boolean isLeaf; // leaf checker

        /**
         * Constructor method for a BTreeNode
         */
        public BTreeNode(int degree){
            if(degree > 0 && degree <= 127) {
                this.degree = degree;
            }
            else {
                this.degree = 127; // default degree
            }
            this.children = new int[2 * degree]; // max number of children
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

    /**
     * TreeObject class that stores the key/freq values in BTreeNodes.
     */
    public class TreeObject {

        private long key;
        private int freq;

        // Sets initial values for key, and frequency
        public TreeObject( long key){
            this.key = key;
            this.freq = 1;
        }

        // Sets key
        public void setKey(long key){
            this.key = key;
        }

        // Returns key
        public long getKey(){
            return this.key;
        }

        // Increments frequency
        public void freqIncrement(){ this.freq++; }

        // Sets frequency
        public void setFreq(int freq){
            this.freq = freq;
        }

        // Returns current frequency
        public int getFreq(){
            return this.freq;
        }
    }
}




























