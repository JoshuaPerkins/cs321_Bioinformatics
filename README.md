****************
* Project: Bioinformatics
* Class: CS 321 - Steven Cutchin
* Date: May 4, 2018
* Authors: Lane Dirkes, David Flores, & Joshua Perkins
**************** 

OVERVIEW:

 A program that parses DNA sequences from GeneBank files (.gbk) and stores the
 data into a BTree. The BTree will be stored on disk and is created to user input
 specifications. Once the BTree is created query files are able to be used to 
 search a BTree for specific DNA subsequences.

INCLUDED FILES:
 
 * Created project files:
   + BTree.java - Project implementation of BTree data structure
   + BTreeNode class - Node class for BTree
   + Cache.java - Cache class for BTree
   + GeneBankCreateBTree.java - Main class for creating BTree from .gbk file
   + GeneBankSearch.java - Main class for finding query sequences in a BTree
   + GeneConvert.java - Conversion class to convert between Strings and longs
   + ParseFile.java - Parser class to parse Strings from input files
   + TreeObject class - Object class for BTree
   + README - this file
   
 * Unit test project files:
   + ParseTestGbk.java - Main class to test .gbk file & (sub)sequence parsing
   + ParseTestQuery.java - Main class to test query file & BTree information parsing

PROGRAM DESIGN AND IMPORTANT CONCEPTS:
 
 The GeneBankCreateBTree class is designed to create a BTree that contains 
 DNA subsequences that have been parsed from a GeneBank (.gbk) file. The 
 created BTree's degree and subsequence length will be set to values that 
 are passed in by the user. If '0' is passed into the program as the degree 
 argument an optimum degree is set based on a disk block size of 4096 bytes. 
 
 The GeneBankSearch class is designed to process a query file and search 
 for the contained subsequence inside of an already built BTree. A summary
 of results is printed showing the subsequence and its frequency count in 
 the BTree.

PROGRAM USAGE:

 When creating a BTree the user will pass in arguments, into GeneBankCreateBTree,
 that determine the file locations, BTree attributes, and if the program will 
 use a cache. The BTree attributes are saved in Metadata, as well as the BTree 
 file name. The BTree will contain keys that are subsequences, of user specified 
 length, built from the GeneBank (.gbk) DNA sequence files.
 
 When searching for keys in a BTree the user will pass in arguments, into 
 GeneBankSearch, that set the file locations and determine if the program will
 use a cache. The search will use the subsequences listed in the query file and
 display the resulting frequency count of the item as it is found in the BTree.

 When running the GeneBankCreateBTree or GeneBankSearch classes the user will 
 specify the process characteristics to run the program. After compiling all 
 the java files (with javac *.java) the program can be run using the following 
 argument list.

 Command line arguments to create a BTree from a GeneBank file:
 
 	$java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> <cache size> [<debug level>]
 	
 Command line arguments to run a query on an existing BTree:
 	
 	$java GeneBankSearch <cache> <btree file> <query file> <cache size> [<debug level>]

TESTING:

 Unit tests were made to make sure some of the basic functionality of the main
 classes was implemented correctly. ParseTestGbk was used to show the correct
 implementation of the DNA sequence and subsequence parsing, as well as the key
 conversions from Strings to longs and vice versa. 
 
 The main programs were tested using cache sizes of 100 and 500. Their respective
 run-times, in seconds, are as follows:
 
 * GeneBankCreateBTree:
   + Cache Size 100: N/A
   + Cache Size 500: N/A
 
  * GeneBankSearch:
    + Cache Size 100: N/A
    + Cache Size 500: N/A
  
 The currently known bugs are:
 * Incorrectly creates BTree
   + Offset may be incorrect but after checking it seems to work 
   + Losing root node
     + Without resetting the root node after inserting keys or more nodes the root is lost sometimes
     + When traversing the inorder print out for debug level one is able to print the root node and
     nothing more; almost as if children are not able to be seen
     + Frequency data is lost when trying to print the node data as it is traversed; inserting checks
     show that the frequency is being updated though; most likely a pointer/offset issue
 * Cannot search through created BTree because it is not created correctly
   + Seems to be an offset issue again
   + Correctly parses query strings but returns none found
   
 The parsing of keys and substrings is working correctly per the Test classes that were made.
 The cache functionality is set up to be used but was not implemented because the BTree functionality
 did not work yet. As it stands now the project is almost fully implemented with bugs involving the BTree 
 creation that make the rest of the support code not work.

DISCUSSION:
 
 The GeneBankCreateBTree and GeneBankSearch programs were designed to be used 
 with the cache class implemented; so the program arguments shown in Section 5
 of the project write-up were used. 
 
 APPENDIX:
 
 General binary specifications:
 + Btree = giant array
 + First 4096 bytes used for BTree metadata'
 + Offset zero in the file will be the pointer to root
 + All data will be 64 bit longs
 + Max sub sequence size = 31 characters
 + No matter the sub sequence size, write 64 bits to array
 + Node size = 4096 bytes
 + Node Metadata
 	1. parent_pointer - 4 bytes
 	2. number_of_children - 4 bytes
 	3. number_of_keys - 4 bytes
 	4. fileOffset - 4 bytes
 	3. isLeaf - 1 bytes
 	- 24 word aligned
 + Key size = 64 bits = 8 bytes
 + Meta-data of single node + size of pointer (to parent) + size of key*(2t - 1) + size of pointer (to children) * 2t = 4096
 	- 24 + 12(2t-1) + 4*2t = 4096
 	- 32t + 12 = 4096
 	- t = 127
 + Node Structure
 	1. Metadata (24 Bytes, offset = 0)
 	2. 253 Keys(Tree Objects) (3036 Bytes, offset = 24)
 	3. 254 Children Pointers (1016 Bytes, offset = 3064)
 
 
 A = 00
 
 T = 11
 
 C = 01
 
 G = 10
 
 If the name of the GeneBank file is xyz.gbk, the sequence length is k, the BTree degree is t,
 then the name of the btree file should be xyz.gbk.btree.data.k.t.