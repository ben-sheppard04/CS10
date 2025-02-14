import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * Generic binary tree, storing data of generic type in each node
 *
 * @author Tim Pierson, Winter 2024, based on binary tree from prior terms
 */

public class BinaryTree<E> {
	private BinaryTree<E> left, right;	// children; can be null. Like in linked list which stores type Element
	E data; //Generic -- using E for element

	/**
	 * Constructor leaf node -- left and right are null
	 */
	public BinaryTree(E data) { //for leaf node - end of tree
		this.data = data; this.left = null; this.right = null;
	}

	/**
	 * Constructor inner node
	 */
	public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) { //creates an interior node
		this.data = data; this.left = left; this.right = right;
	}


	/**
	 * helper methods
	 */
	public boolean isInner() {
		return left != null || right != null;
	}
	public boolean isLeaf() {
		return left == null && right == null;
	}
	public boolean isFull() { return left != null && right !=null;}
	public boolean hasLeft() {
		return left != null;
	}
	public boolean hasRight() {
		return right != null;
	}

	public BinaryTree<E> getLeft() {
		return left;
	}
	public BinaryTree<E> getRight() {
		return right;
	}
	public E getData() {
		return data;
	}

	public void setData(E data) {this.data = data;}
	public void setLeft(BinaryTree<E> child) {
		left = child;
	}
	public void setRight(BinaryTree<E> child) {
		right = child;
	}

	/**
	 * Number of nodes (inner and leaf) in tree, theta(n)
	 */
	public int size() { //call it on some root node.
		int num = 1; //counts the node it was called on.
		if (hasLeft()) num += left.size(); //recursively call size(), sets num = 1 within that call of size
		if (hasRight()) num += right.size();
		return num;
	}

	/**
	 * Longest length to a leaf node from here
	 */
	public int height() {
		if (isLeaf()) return 0;
		int h = 0;
		if (hasLeft()) h = Math.max(h, left.height()); //Do you need the max here?????? (No)
		if (hasRight()) h = Math.max(h, right.height());
		return h+1;		// inner: one higher than highest child - counts itself
	}

	/**
	 * Same structure and data
	 * @param t2 compare with this tree
	 * @return true if this tree and t2 have the have structure and same data in each node, false otherwise
	 */
	public boolean equals(BinaryTree<E> t2) {
		if (hasLeft() != t2.hasLeft() || hasRight() != t2.hasRight()) return false; //not equal if dif. number of children.
		if (!data.equals(t2.data)) return false; //not equal if data is not equal at curr. node
		if (hasLeft() && !left.equals(t2.left)) return false; //as each child to compare itself
		if (hasRight() && !right.equals(t2.right)) return false;
		return true;
	}

	/**
	 * Check to see if value is in the tree
	 * @param value value to seek
	 * @return true if value in tree, false otherwise
	 */
	public boolean contains(E value) {
		//see if this node's data is equal to value
		if (data.equals(value)) {
			return true;
		}
		else {
			//see if left or right child has value
			boolean leftResult = false;
			boolean rightResult = false;
			if (hasLeft()) leftResult = left.contains(value);
			if (hasRight()) rightResult = right.contains(value);
			return leftResult || rightResult; //true if left or right is true
		}
	}

	/**
	 * Leaves, in order from left to right
	 */
	public List<E> fringe() {
		List<E> f = new ArrayList<E>();
		addToFringe(f); //passing empty list to a recursive helper function
		return f;
	}

	/**
	 * Helper for fringe, adding fringe data to the list
	 */
	private void addToFringe(List<E> fringe) { //takes initially empty list, builds it up recursively. Note: it always checks
		//the left path first and then the right, so it will be in order from left to right/
		//Why is there a helper??? - it would make a new f list every time. Does it exist something where it runs only
		//on the first recursion?
		if (isLeaf()) {
			fringe.add(data);
		}
		else {
			if (hasLeft()) left.addToFringe(fringe);
			if (hasRight()) right.addToFringe(fringe);
		}
	}

	/**
	 * Returns a string representation of the tree
	 */
	public String toString() {
		return toStringHelper("", "");
	} //Have helper so that you can pass
	//in a parameter (not allowed for toString override)

	/**
	 * Recursively constructs a String representation of the tree from this node, 
	 * starting with the given indentation and indenting further going down the tree
	 * formatting inspired by: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
	 */
	public String toStringHelper(String indent, String childIndent) {
		String res = indent + data + "\n";
		if (hasLeft()) res += left.toStringHelper(childIndent + "├── ", childIndent + "│   ");
		if (hasRight()) res += right.toStringHelper(childIndent + "└── ", childIndent + "    ");
		return res;
	}

	/**
	 * Tree traversals
	 */
	public void preOrderTraversal() {
		System.out.println(data);
		if (hasLeft()) left.preOrderTraversal();
		if (hasRight()) right.preOrderTraversal();
	}

	public void inOrderTraversal() {
		if (hasLeft()) left.preOrderTraversal();
		System.out.println(data);
		if (hasRight()) right.preOrderTraversal();
	}

	public void postOrderTraversal() {
		if (hasLeft()) left.preOrderTraversal();
		if (hasRight()) right.preOrderTraversal();
		System.out.println(data);
	}

	public BinaryTree<E> copyToDepth(int d){
		if(d == 0){
			return new BinaryTree<E>(data);
		}
		else if(d > 0){
			BinaryTree<E> leftT = new BinaryTree<E>(null);
			BinaryTree<E> rightT = new BinaryTree<E>(null);
			if(hasLeft()) leftT = left.copyToDepth(d - 1);
			if(hasRight()) rightT = right.copyToDepth(d - 1);
			return new BinaryTree<E>(data, leftT, rightT);
		}
		else{
			return new BinaryTree<E>(null);
		}
	}

	public void invertTreeBen() {
		Stack<BinaryTree<E>> treeStack = new Stack<>();
		if (hasLeft()) {
			treeStack.push(left);
			left.invertTreeBen();
		}
		if (hasRight()) {
			treeStack.push(right);
			right.invertTreeBen();
		}
		if (treeStack.size() == 2) {
			left = treeStack.pop();
			right = treeStack.pop();
		}
		else if (hasLeft()){
			right = treeStack.pop();
			left = null;
		}
		else if (hasRight()){
			right = null;
			left = treeStack.pop();
		}
	}
	public void invertTreeKey(){
		BinaryTree<E> leftTemp = left;
		BinaryTree<E> rightTemp = right;
		if (leftTemp != null) {
			leftTemp.invertTreeKey();
		}
		if(rightTemp != null){
			rightTemp.invertTreeKey();
		}
		left = rightTemp;
		right = leftTemp;
	}

	public static void main(String[] args) throws Exception {
		//manually build a tree
		BinaryTree<String> root = new BinaryTree<String>("G");
		root.left = new BinaryTree<String>("B");
		root.right = new BinaryTree<String>("F");
		BinaryTree<String>temp = root.left; //Need this line because if you did root = root.left, G and F would be garbage collected
		//could do root.left.left instead of temp.left
		temp.left = new BinaryTree<String>("A");
		temp.right = new BinaryTree<String>("C");
		temp = root.right;
		temp.left = new BinaryTree<String>("D");
		temp.right = new BinaryTree<String>("E");
		System.out.println("root");
		System.out.println(root); //calls toString,
		System.out.println();
		root.invertTreeBen();
		System.out.println(root);



		System.out.println(root.copyToDepth(1));
		
		System.out.println("Fringe");
		System.out.println(root.fringe());

		System.out.println("Contains A: " + root.contains("A"));
		System.out.println("Contains Z: " + root.contains("Z"));

		//build an identical second tree
		BinaryTree<String> root2 = new BinaryTree<String>("G");
		root2.left = new BinaryTree<String>("B");
		root2.right = new BinaryTree<String>("F");
		BinaryTree<String>temp2 = root2.left;
		temp2.left = new BinaryTree<String>("A");
		temp2.right = new BinaryTree<String>("C");
		temp2 = root2.right;
		temp2.left = new BinaryTree<String>("D");
		temp2.right = new BinaryTree<String>("E");
		System.out.println("root");
		System.out.println(root2);

		//check equals
		System.out.println("root and root2 equal: " + root.equals(root2));
		System.out.println("Updating root2 to have data Z");
		root2.setData("Z");
		System.out.println(root2);
		System.out.println("root and root2 equal: " + root.equals(root2));



	}
}
