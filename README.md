# Songs
This song project manages a dataset of songs in a TSV file. Project focuses on trees such as AVL and BinarySearch. Optimized for large datasets. 


Song.java - Creates attirbutes such as title, tag, artist, years, views, and lyrics. Uses comparable interface to compare songs based on view counts

MyDataReader.java - Processes song data from a TSV file, it can parse individual songs into song objects. Also has specific methods to load songs into data structures such as BST and ArrayList

AVLTree.java - Implements a self balancing binary search tree. Performs rotations during insertions to keep O(Log n) complexity for search/insertion/delete methods
- rotations include Left, right, leftright, rightleft
- Can insert objects after balancing to maintain AVL tree
- retrieves songs with specific view counts
- counts rotations of each type

BinarySearchTree.Java - makes a custom binary tree that is designed to manage objects of the song class. Organized songs based on view counts.
-Each node stores song object and adds pointers to its left/right children
- can insert based on view count, search songs based on if its views are greater or equal to a number. Also calculates the height of a subtree or the entire tree.
- Tree is in a nin-order transveral
- Clones the tree in methods for finding artists of specific genre's etc.
- removes nodes based on view coutns

MySearchEngine.Java calculates the relevancy score of the songs based on the BM25 ranking formula. Uses term frequency and inverse document frequency for the avg song length to rank songs.
- TF calculates term freq of each songs lyrics. IDF computes inverse document frequency of each unique word. Also calculates the avg length of all song lyrics
- Retrieves and ranks top 5 most relevant songs for the entire tree or a given genre.

Program1 - Reads song of a specified genre from a TSV file and then puts them into a binary search tree using the BST class. Finds the top 10 songs by views, clones the tree and filters. 

Program 6 - Uses MySearchEngire to process songs based on relevancy

Program 7 - Compares AVL and BinarySearchTree by building each tree from song data of specified genres and performing searches on them. Tracks tree height, rotations and search times. 
