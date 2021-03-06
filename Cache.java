import java.util.LinkedList;

/**
 * BTree class for implementing a BTree that stores GeneBank data.
 *
 * @author DFlores
 * Date: May 4, 2018
 *
 * Class: CS 321 - Data Structures
 * Spring 2018 - Steven Cutchin
 */
public class Cache {
        int ref = 0;
        int refL1 = 0;
        int hits = 0;
        int hitsL1 = 0;
        double hitRatio = 0;
        double hitRatioL1 = 0;
        int option;
        int totalHits = 0;
        int firstCacheSize = 0;


        LinkedList<Object> listL1 = new LinkedList<Object>();
        //LinkedList<Object> listL2 = new LinkedList<Object>();

        //constructor for 1 level caceh
        public Cache(int size1) {
            firstCacheSize = size1;
        }


        public void addObject(Object aword) {
            if(getObject(aword)) {
                return;
            }
            else {
                if(listL1.size() == firstCacheSize) {
                    listL1.removeLast();
                }
                listL1.add(0, aword);
            }
        }

        // getting object from level 1 or level 2
        public boolean getObject(Object aword) {
            if(firstCacheSize > 0) {
                refL1++;
                if(listL1.contains(aword)) {
                    hitsL1++;
                    listL1.remove(listL1.indexOf(aword));
                    listL1.add(0, aword);
                    return true;
                }
            }
            return false;
        }

        public void clearCache(int list) {
            if(list == 1) {
                listL1.clear();
            }
            else{
                System.out.println("Clear correct cache");
            }
        }

        public void removeObject(Object aword) {
            if(listL1.contains(aword)) {
                listL1.remove(aword);
            }
            else {
                System.out.println(aword + " is not in list 1");
            }
        }
    }
