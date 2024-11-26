package songpack;

import java.io.IOException; 
import java.util.*;

public class Program7 {
	public static void main(String[] args) {
		String filePath = args[0];
		String genre = args[1];
		
		 try {
	         
	            long avlBuildStartTime = System.nanoTime();
	            AVLTree avlTree = new AVLTree();
	            MyDataReader.readFileToBST(filePath, genre).inOrderTraversal().forEach(avlTree::insert);
	            long avlBuildEndTime = System.nanoTime();
	            System.out.println(avlTree.getLeftRotationCount() + " left rotations");
	            System.out.println(avlTree.getRightRotationCount() + " right rotations");
	            System.out.println(avlTree.getLeftRightRotationCount() + " left-right rotations");
	            System.out.println(avlTree.getRightLeftRotationCount() + " right-left rotations");
	            System.out.println("The height of the AVL tree is: " + avlTree.height(avlTree.root));
	            System.out.println((avlBuildEndTime - avlBuildStartTime) / 1_000_000 + " milliseconds to build the AVL tree for " + genre + " songs");

	            //performSearches(avlTree);
	            measureSearchTimes(avlTree);

	            // Build Binary Search Tree
	            long bstBuildStartTime = System.nanoTime();
	            BinarySearchTree bstTree = MyDataReader.readFileToBST(filePath, genre);
	            long bstBuildEndTime = System.nanoTime();

	            System.out.println("The height of the Binary Search Tree is: " + bstTree.calculateHeight(bstTree.root));
	            System.out.println((bstBuildEndTime - bstBuildStartTime) / 1_000_000 + " milliseconds to build the BST for " + genre + " songs");

	            // Perform Searches on BST
	            //performSearches(bstTree);
	            measureSearchTimes(bstTree);

	        } catch (IOException e) {
	            System.err.println("Error reading the file: " + e.getMessage());
	        }
	    }
		
	    /**
	     * @param tree the tree to perform a search man
	     * Looks for the example views and 
	     */
	/*
	    private static void performSearches(BinarySearchTree tree) {
	        int[] views = {-2, 12345, 2, 5000, 1000000}; 

	        for (int i = 0; i < views.length; i++) {
	            long searchStartTime = System.nanoTime();
	            tree.search(views[i]);
	            long searchEndTime = System.nanoTime();
	            long searchTime = (searchEndTime - searchStartTime) / 1_000; // Convert to microseconds
	            System.out.println(searchTime + " microseconds for search " + (i + 1));
	        }
	    
	}
	    */
	private static void measureSearchTimes(BinarySearchTree tree) {
		int[] searchValues = {-1, 500, 10000, 500000, 1000000};
		long totalTime = 0; 
		
		for(int value : searchValues) {
			long start = System.nanoTime();
			tree.search(value);
			long end = System.nanoTime();
			long searchTime = (end - start)/1000;
			System.out.println("Search for " + value + ": " + searchTime + " microseconds");
			totalTime += searchTime;
			
		}
		
		long avgSearchTime = totalTime / searchValues.length;
		System.out.println("Average Search Time: " + avgSearchTime + " microseconds");
	}
	
}
