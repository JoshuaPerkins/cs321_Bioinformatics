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
   + BTreeNode.java - Node class for BTree
   + Cache.java - Cache class for BTree
   + GeneBankCreateBTree.java - Main class for creating BTree from .gbk file
   + GeneBankSearch.java - Main class for finding query sequences in a BTree
   + GeneConvert.java - Conversion class to convert between Strings and longs
   + ParseFile.java - Parser class to parse Strings from input files
   + TreeObject.java - Object class for BTree
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
   + Cache Size 100: ***--__FINISH__--***
   + Cache Size 500: ***--__FINISH__--***
 
  * GeneBankSearch:
    + Cache Size 100: ***--__FINISH__--***
    + Cache Size 500: ***--__FINISH__--***
  
 There are no known bugs/issues with the code or project.

DISCUSSION:
 
 The GeneBankCreateBTree and GeneBankSearch programs were designed to be used 
 with the cache class implemented; so the program arguments shown in Section 5
 of the project write-up were used. 
 
  **-- * -- THIS IS A TEMPLATE THAT NEEDS REPLACING -- * --**
 
 The random aspects of each processes process time and priority level made the 
 task of testing the simulations functionality more difficult; but by stepping
 through the output based upon the report of when the processes were made and
 finished helped prove the correctness of the program. 
 
 As discussed in class; changing the update function to not use MaxHeapifyUp
 inside of the for loop and instead use a compare method walking from the end 
 of the array list to the beginning saved time. In order to see this I ran the 
 CPU scheduling simulation for 100,000 units of simulation time. Adding a 
 stopwatch (System.currentTimeMillis()) counter to the beginning and end of
 CPUScheduling.java allowed me to measure the total run time of the program. 
 It was seen that comparing the children to the parent at the end of the update
 method, using MaxHeapifyCompare (instead of MaxHeapifyUp inside of the for
 loop) saved an average of 1.2 seconds of runtime over 5 runs of the program.
