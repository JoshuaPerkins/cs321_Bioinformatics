/*
Cache creation
 */

import java.util.LinkedList;

public class Cache {

        int ref = 0;
        int refL1 = 0;
        int refL2 = 0;
        int hits = 0;
        int hitsL1 = 0;
        int hitsL2 = 0;
        double hitRatio = 0;
        double hitRatioL1 = 0;
        double hitRatioL2 = 0;
        int option;
        int totalHits = 0;
        int firstCacheSize = 0;
        int secondCacheSize = 0;

        LinkedList<Object> listL1 = new LinkedList<Object>();
        LinkedList<Object> listL2 = new LinkedList<Object>();

        //constructor for 1 level caceh
        public Cache(int size1) {
            firstCacheSize = size1;
        }

        // constructor for 2 level cache
        public Cache(int size1, int size2) {
            firstCacheSize = size1;
            secondCacheSize = size2;
        }

        public void addObject(Object aword) {
            if(getObject(aword)) {
                return;
            }
            else {
                if(listL1.size() == firstCacheSize) {
                    listL1.removeLast();
                }
                if(listL2.size() == secondCacheSize) {
                    listL2.removeLast();
                }
                listL1.add(0, aword);
                listL2.add(0, aword);
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
                    if(secondCacheSize > 0) {
                        listL2.remove(listL2.indexOf(aword));
                        listL2.add(0, aword);
                    }
                    return true;
                }
                if(secondCacheSize > 0) {
                    refL2++;
                    if(listL2.contains(aword)) {
                        hitsL2++;
                        listL2.remove(listL2.indexOf(aword));
                        listL2.add(0, aword);
                        if(listL1.size() == firstCacheSize) {
                            listL1.removeLast();
                        }
                        listL1.add(0, aword);
                        return true;
                    }
                }
            }
            return false;
        }

        public void clearCache(int list) {
            if(list == 1) {
                listL1.clear();
            }
            else if(list == 2) {
                listL2.clear();
            }
        }

        public void removeObject(Object aword) {
            if(listL1.contains(aword)) {
                listL1.remove(aword);
            }
            if(listL2.contains(aword)) {
                listL2.remove(aword);
            }
            else {
                System.out.println(aword + " is not in list 1 or list 2");
            }
        }
    }
