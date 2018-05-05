import java.io.IOException;
import java.io.PrintWriter;

/**
 * BTreeTraversal class for performing an inorder traversal.
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class BTreeTraversal {

    /**
     * Inorder Traversal of the BTree; prints the frequency and key information from the nodes.
     *
     * @param node The BTree node to traverse and write information.
     * @param k The subsequence length.
     * @param writer The PrintWriter
     * @throws IOException
     */
    public static void inorderDebugPrinter(BTree bTree, BTree.BTreeNode node, int k, PrintWriter writer) throws IOException {
        if (node != null) {
            for (int i = 0; i < node.numChildren; i++) {
                // Temporarily store the child node
                BTree.BTreeNode temp = bTree.readNode(node.children[i]);
                // If node is not leaf go to child
                if (!node.isLeaf) {
                    // Recursive call to traverse
                    inorderDebugPrinter(bTree, temp, k, writer);
                }
                // Loops through objects until all keys and their frequencies are printed
                for (int j = 0; j < node.numKeys; j++) {
                    // Writes frequency and key to file then inserts a new line
                    writer.print(node.keys[i].getFreq() + " ");
                    writer.print(GeneConvert.longToSubsequence(node.keys[i].getKey(), k));
                    writer.println();
                }
            }
        }
    }
}
