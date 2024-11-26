package songpack;
import java.util.ArrayList;
import java.util.List;

import songpack.BinarySearchTree.Node;

/*
 * @author Jakec
 * @Version 10/23/2024
 * 
 * Binary search tree that manages objects of the Song class
 * songs will be managed by their view counts 
 */
public class BinarySearchTree {

    /**
     * Nodes for the binary tree
     * nodes store songs with pointers to left and right children
     */
    public class Node {
        Song data;
        Node left;
        Node right;
        int height;

        public Node(Song data) {
            this.data = data;
            left = right = null;
            height = 0;
        }
    }

     Node root;
     
     /*
      * Returns height of a node
      */
     protected int height(Node N) {
    	 if(N == null)
    		 return 0;
    	 return N.height;
     }
     
     /*
      * @param node the root
      * @return the height of the subtree
      * created to find height of subtree
      */
     public int calculateHeight(Node node) {
    	 if(node == null) {
    		 return -1;
    	 }
    	 int leftHeight = calculateHeight(node.left);
    	 int rightHeight = calculateHeight(node.right);
    	 return 1 + Math.max(leftHeight, rightHeight);
     }

    public BinarySearchTree() {
        root = null;
    }
    
    /**
     * inserts a song into the tree based on view count
     * if lower than the current node, it is inserted
     * to the left, if greater or equal to, inserted
     * to the right
     * @param item the song to add to tree
     */
    public void insert(Song item) {
    	root = insert(root, item);
    }
    
    /**
     * helper method for insert that uses recursion
     * to find the right spot to insert a song
     * 
     * @param localRoot root of the subtree to be checked
     * @param item song
     * @return
     */
    private Node insert(Node localRoot, Song item) {
    	if(localRoot == null) {
    		return new Node(item);
    	}
    	
    	int compare = Integer.compare(item.getViews(), localRoot.data.getViews());
    	if (compare < 0) {
    		localRoot.left = insert(localRoot.left, item);
    	} else {
    		localRoot.right = insert(localRoot.right, item);
    	}
    	return localRoot;
    }
    
    /**
     * Finds all songs that have a specified num of views
     * @param views the min view count
     * @return list of songs with views >= the specified views
     */
    public ArrayList<Song> search(int views){
    	ArrayList<Song> result = new ArrayList<>();
    	search(root, views, result);
    	return result;
    }
    
    /**
     * helper method using recursion to find songs
     * with a view count >= the specified views
     * @param localRoot the root of the subtree
     * @param views the min view count
     * @param result the list of songs that match criteria
     */
    protected void search(Node localRoot, int views, ArrayList<Song> result) {
    	if(localRoot == null) {
    		return;
    	}
    	if(localRoot.data.getViews() >= views) {
    		result.add(localRoot.data);
    	}
    	search(localRoot.left, views, result);
    	search(localRoot.right, views, result);
    }
    
    /**
     * Checks if the tree is a valid binary search tree
     * @return True if valid
     */
    public boolean isBST() {
    	return isValidBST(root);
    }

    /**
     * Uses recursion to check if the subtree is valid BST
     * @param root the root of the subtree
     * @return True if the tree is valid
     */
    private boolean isValidBST(Node root) {
        if (root == null) {
            return true;
        }
        if(root.left == null && root.right == null) {
        return true;
        }
        if(root.left != null && root.data.compareTo(root.left.data)<=0) {
        return false;
        }
       
        boolean bl = isValidBST(root.left);
        boolean br = isValidBST(root.right);
        return br&&bl;
    }
   
   
    /**
     * converts bst into a sorted list of songs
     * Uses in order traversal for the sorting
     * @return arraylist of sorted songs by view
     */
    public ArrayList<Song> toSortedArrayList(){
    	ArrayList<Song> sorted = new ArrayList<>();
    	inOrderTraversal(root,sorted);
    	return sorted;
   
   
    }
    
    public List<Song> inOrderTraversal() {
        ArrayList<Song> songs = new ArrayList<>();
        inOrderTraversal(root, songs);  // Start traversal from the root
        return songs;
    }

   
    private void inOrderTraversal(Node root, ArrayList<Song> list) {
        if (root != null) {
            inOrderTraversal(root.left, list);
            list.add(root.data); 
            inOrderTraversal(root.right, list);
        }
    }
    
    
    /**
     * clones the bst
     * 
     * @return a new yet identical bst
     */
    public BinarySearchTree clone() {
    	BinarySearchTree clonedTree = new BinarySearchTree();
    	clonedTree.root = cloneHelper(root);
    	return clonedTree;
    }
    
    /**
     * recursively clones each node
     * @param localRoot
     * @return the root of the cloned subtree
     */
    private Node cloneHelper(Node localRoot) {
    	if(localRoot == null) {
    		return null;
    	}
    	Node clonedNode = new Node(localRoot.data);
    	clonedNode.left = cloneHelper(localRoot.left);
    	clonedNode.right = cloneHelper(localRoot.right);
    	return clonedNode;
    }
    
    /**
     * Finds artists with the most viewed songs
     * @return artist names who have highest views
     */
    public ArrayList<String> popularArtists(){
    	ArrayList<String> artists = new ArrayList<>();
    	int maxViews = getMaxViews(root);
    	collectArtistsWithViews(root, maxViews, artists);
    	return artists;
    }
    
    /**
     * Finds the highest view count in the tree
     * @param localRoot the root of the subtree being checked
     * @return highest view count found
     */
    private int getMaxViews(Node localRoot) {
    	if(localRoot == null) {
    		return Integer.MIN_VALUE;
    	}
    	int maxLeft = getMaxViews(localRoot.left);
    	int maxRight = getMaxViews(localRoot.right);
    	return Math.max(localRoot.data.getViews(), Math.max(maxLeft, maxRight));
    	
    }
    
    /**
     * Finds artists with a specified view count
     * @param localRoot the root being checked
     * @param views the specified views
     * @param artists the list storing artist names
     */
    private void collectArtistsWithViews(Node localRoot, int views, ArrayList<String> artists) {
    	if(localRoot == null) {
    		return;
    	}
    	if(localRoot.data.getViews() == views) {
    		artists.add(localRoot.data.getArtist());
    	}
    	collectArtistsWithViews(localRoot.left, views, artists);
    	collectArtistsWithViews(localRoot.right, views, artists);
    }
    
    /**
     * Keeps only nodes within a range of view counts
     * @param minView the min num of views
     * @param maxView the max num of views
     * @return root of the new tree
     */
    public Node filterByView(int minView, int maxView) {
    	return filterByView(root, minView, maxView);
    }
    
    /**
     * recursively filters out nodes that aren't within range
     * @param localRoot current root being checked
     * @param minView min num of views
     * @param maxView max num of views
     * @return updated node after the filter
     */
    private Node filterByView(Node localRoot, int minView, int maxView) {
    	if(localRoot == null) {
    		return null;
    	}
    	
    	if(localRoot.data.getViews() < minView) {
    		return filterByView(localRoot.right, minView, maxView);
    	} else if (localRoot.data.getViews() > maxView) {
    		return filterByView(localRoot.left, minView, maxView);
    	}
    	
    	localRoot.left = filterByView(localRoot.left, minView, maxView);
    	localRoot.right = filterByView(localRoot.right, minView, maxView);
    	return localRoot;
    }
}