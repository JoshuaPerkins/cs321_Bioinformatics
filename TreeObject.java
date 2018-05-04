
/**
 * TreeObject class for ...
 *
 * @author JPerkins
 * Date: April 13, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */

public class TreeObject {

    long key;
    int freq;

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

    // Returns current frequency
    public int getFreq(){
        return this.freq;
    }
}
