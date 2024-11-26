package songpack;

import java.util.ArrayList;

public class AVLTree extends BinarySearchTree{
	
	private int leftRotation;
	private int rightRotation;
	private int leftRightRotation;
	private int rightLeftRotation;
	
	public AVLTree() {
		root = null;
		leftRotation = 0;
		rightRotation = 0;
		leftRightRotation = 0;
		rightLeftRotation = 0;
	}
	
	/*
	 * updates the height of the node, helper method for rotations
	 */
	private void updateHeight(Node n) {
		n.height = 1 + Math.max(height(n.left), height(n.right));
	}
	
	/*
	 * takes node current and takes it down to the left, and brings the right subtree root
	 * node "replace" and makes it the new root. Node replaceLeft goes from right subtree to left 
	 * subtree under the node current.
	 */
	private Node leftRotate(Node current) {
		Node replace = current.right;
		Node replaceLeft = replace.left;
		
		replace.left = current; 
		current.right = replaceLeft;
		
		updateHeight(replace);
		updateHeight(current);
		return replace;
	}
	
	private Node rightRotate(Node current) {
		Node replace = current.left;
		Node replaceRight = replace.right;
		
		replace.right = current;
		current.left = replaceRight;
		
		updateHeight(replace);
		updateHeight(current);
		return replace;
	}
	
	private int balanceFactor(Node N) {
		if (N == null)
			return 0;
		return height(N.right)- height(N.left); 
	}
	
	private Node rebalance(Node node) {
	    updateHeight(node);
	    int bf = balanceFactor(node);

	    // Left Case
	    if (bf < -1 && balanceFactor(node.left) <= 0) {
	        rightRotation += 1;
	        return rightRotate(node);
	    }

	    // Right Case
	    if (bf > 1 && balanceFactor(node.right) >= 0) {
	        leftRotation += 1;
	        return leftRotate(node);
	    }

	    // Left-Right (LR) Case
	    if (bf < -1 && balanceFactor(node.left) > 0) {
	        leftRightRotation += 1;
	        node.left = leftRotate(node.left);
	        return rightRotate(node);
	    }

	    // Right-Left (RL) Case
	    if (bf > 1 && balanceFactor(node.right) < 0) {
	        rightLeftRotation += 1;
	        node.right = rightRotate(node.right);
	        return leftRotate(node);
	    }

	    return node;
	}
	
	/**
	 * inserts and balances the avl tree
	 */
	@Override
	public void insert(Song item) {
		root = insert(root, item);
	}
	
	/**
	 * Recursive method to balance the tree after insertion
	 * @param node The nodes in the tree
	 * @param item the song to be inserted into the node
	 * @return the rebalanced nodes
	 */
	private Node insert(Node node, Song item) {
		if(node == null) {
			return new Node(item);
		}
		int compare = Integer.compare(item.getViews(), node.data.getViews());
		if (compare < 0) {
			node.left = insert(node.left, item);
		}else if(compare > 0) {
			node.right = insert(node.right, item);
		}else{
			return node;
		}
		return rebalance(node);
	}
	
	@Override
	public ArrayList<Song> search(int views){
		ArrayList<Song> result = new ArrayList<>();
		search(root, views, result);
		return result;
	}
	
		public int getLeftRotationCount() {
			return leftRotation;
		}

		public int getRightRotationCount() {
			return rightRotation;
		}

		public int getLeftRightRotationCount() {
			return leftRightRotation;
		}

		public int getRightLeftRotationCount() {
			return rightLeftRotation;
		}
	
}
