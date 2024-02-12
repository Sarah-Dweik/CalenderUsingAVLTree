package Components;

public class AVL<T extends Comparable<T>> {
	public Tnode <T>root;
	
	//Rotations methods 
	
	// when left branch is causing the unbalanced node
	public Tnode rightSingleRotation(Tnode pNode) {
		Tnode unbNode = pNode.getLeft();// this is the unbalanced Node

		pNode.setLeft(unbNode.getRight());
		unbNode.setRight(pNode);

		return unbNode;
	}

	// when right branch is causing the unbalanced node
	public Tnode leftSingleRotation(Tnode pNode) {
		Tnode unbNode = pNode.getRight();// this is the unbalanced Node

		pNode.setRight(unbNode.getLeft());
		unbNode.setLeft(pNode);
		return unbNode;
	}

	// double rotation when addition is left right
	public Tnode doubleRotRightLeftAddition(Tnode pNode) {
		Tnode unbNode = pNode.getRight();

		Tnode firstNewBranch = rightSingleRotation(unbNode);
		pNode.setRight(firstNewBranch);

		Tnode secondNewBranch = leftSingleRotation(pNode);

		return secondNewBranch;

	}

	// double rotation when addition is right left
	public Tnode doubleRotLeftRightAddition(Tnode pNode) {
		Tnode unbNode = pNode.getLeft();

		Tnode firstNewBranch = leftSingleRotation(unbNode);
		pNode.setLeft(firstNewBranch);

		Tnode secondNewBranch = rightSingleRotation(pNode);

		return secondNewBranch;
	}
	

	public int height() {
		return (height(root));
	}

	public int height(Tnode node) {
		if (node == null)
			return 0;

		if (node.isLeaf())
			return 1;

		int left = 0;
		int right = 0;

		if (node.hasLeft())
			left = height(node.getLeft());

		if (node.hasRight())
			right = height(node.getRight());

		if (left > right) {
			return left + 1;
		} else {
			return right + 1;
		}
	}

	// Return the height difference for a given node
	public int heightDiff(Tnode node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 0; // Return 0 for a leaf node
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.getLeft());
		if (node.hasRight())
			right = height(node.getRight());
		return left - right; // Return the height difference (left - right)
	}

	public Tnode rebalance(Tnode pNode) {
		int diff = heightDiff(pNode);
		if (diff > 1) { // left branch weight more
			if (heightDiff(pNode.getLeft()) > 0) { // left is casuing the unbalnce
				pNode = rightSingleRotation(pNode);
			} else { // right is causing the unbalance
				pNode = doubleRotLeftRightAddition(pNode);
			}
		}

		else if (diff < -1) { // right branch weight more
			if (heightDiff(pNode.getRight()) < 0) { // left is casuing the unbalnce
				pNode = leftSingleRotation(pNode);
			} else { // right is causing the unbalance
				pNode = doubleRotRightLeftAddition(pNode);
			}
		}
		return pNode;
	}

	public void insert(T data) {
		if (root == null) {
			Tnode newnode = new Tnode(data);
			root = newnode;
		} else {
			Tnode rootNode = root;
			addEntry(data, rootNode);
			root = rebalance(rootNode);
		}
	}

	public void addEntry(T data, Tnode rootNode) {
		if (data.compareTo((T) rootNode.getData()) < 0) {
			if (rootNode.hasLeft()) {
				Tnode leftChild = rootNode.getLeft();
				addEntry(data, leftChild);
				rootNode.setLeft(rebalance(leftChild));
			} else {
				Tnode newnode = new Tnode(data);
				rootNode.setLeft(newnode);
			}
		}

		else {
			if (rootNode.hasRight()) {
				Tnode rightChild = rootNode.getRight();
				addEntry(data, rightChild);
				rootNode.setRight(rebalance(rightChild));

			} else {
				Tnode newnode = new Tnode(data);
				rootNode.setRight(newnode);
			}
		}
	}

	//make it string to append it to the printwriter
	public String traverseInOrder(Tnode node) {
		String str = "";
		if (node != null) {
			str+= traverseInOrder(node.getLeft());
			//System.out.println(node + " ");
			str+= node +"";
			str+= traverseInOrder(node.getRight());
		}
		return str;
	}

// delete Node recursively
	public Tnode delete(T data) {
		root = deleteNode(root, data);
		return root;
	}

	private Tnode deleteNode(Tnode pNode, Comparable comparable) {
		if (pNode == null) {
			return pNode; // tree is empty or node not found
		}

		int compareResult = comparable.compareTo((T) pNode.getData());

		if (compareResult < 0) {
			pNode.setLeft(deleteNode(pNode.getLeft(), comparable));
		} else if (compareResult > 0) {
			pNode.setRight(deleteNode(pNode.getRight(), comparable));
		} else {
			// Node with only one child or no child
			if (pNode.getLeft() == null || pNode.getRight() == null) {
				Tnode temp = (pNode.getLeft() != null) ? pNode.getLeft() : pNode.getRight();

				// No child case
				if (temp == null) {
					temp = pNode;
					pNode = null;
				} else { // One child case
					pNode = temp; // Copy the contents of the non-empty child
				}

				temp = null;
			}

			else {
				// Node with two children, get the in-order successor (smallest
				// in the right subtree)
				Tnode successor = getSuccessor(pNode);

				// Copy the in-order successor's data to this node
				pNode.setData(successor.getData());

				// Delete the in-order successor
				pNode.setRight(deleteNode(pNode.getRight(), successor.getData()));
			}
		}

		// If the tree had only one node, then return
		if (pNode == null) {
			return pNode;
		}
		return rebalance(pNode);
	}

// successor
	public Tnode getSuccessor(Tnode node) {
		Tnode parentSucc = node;
		Tnode succ = node;
		Tnode curr = node.getRight();
		while (curr != null) {
			parentSucc = succ;
			succ = curr;
			curr = curr.getLeft();
		}
		if (succ != node.getRight()) {
			parentSucc.setLeft(succ.getRight());
			succ.setRight(node.getRight());
		}
		return succ;
	}

	// check if AVL is BST: check if all left less than node and all right greater than node
	public boolean isBinary() {
		return isBinary(root);
	}

	public boolean isBinary(Tnode node) {
		if(node==null) {
			return true; 
		}
		if(node.hasLeft()&& node.getLeft().getData().compareTo(node.getData())<0) {
			isBinary(node.getLeft());
			return true;
		}
		if(node.hasRight()&& node.getRight().getData().compareTo(node.getData())>0) {
			isBinary(node.getRight());
			return true;
		}
		return false;
	}
	
	public T find(T data) {
		if (root != null) {
			return find(root, data);
		}
		return null; // tree is not created
	}

	public T find(Tnode node, T data) {
		if (node != null) {
			if (node.getData().compareTo(data) == 0) {
				return (T) node.getData(); // Return the data when found
			} else if (node.getData().compareTo(data) > 0 && node.hasLeft()) {
				return find(node.getLeft(), data);
			} else if (node.getData().compareTo(data) < 0 && node.hasRight()) {
				return find(node.getRight(), data);
			}
		}
		return null; // was not found
	}
	
	public void levelTraversal() {
	    if (root != null) {
	        levelTraversal(root);
	    }
	}

	public void levelTraversal(Tnode root) {
	    StackQueue queue = new StackQueue();
	    StackQueue nextLevelQueue = new StackQueue(); // I want it to print each level on seperate line

	    queue.enqueue(root); // Enqueue the root node

	    while (!queue.isEmpty()) {
	        Tnode currentNode = (Tnode) queue.dequeue(); // Dequeue a node

	        System.out.print(currentNode.getData() + " ");

	        // Enqueue the left and right children to the next level if they exist
	        if (currentNode.hasLeft()) {
	            nextLevelQueue.enqueue(currentNode.getLeft());
	        }
	        if (currentNode.hasRight()) {
	            nextLevelQueue.enqueue(currentNode.getRight());
	        }

	        // Check if we have finished processing the current level
	        if (queue.isEmpty()) {
	            System.out.println(); // Move to the next line after each level
	            // Swap the queues for the next level
	            StackQueue temp = queue;
	            queue = nextLevelQueue;
	            nextLevelQueue = temp;
	        }
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
}
