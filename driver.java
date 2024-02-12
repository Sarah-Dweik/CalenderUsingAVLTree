package Components;

public class driver {
	public static void main(String[]args) {
		AVL<Integer> tree = new AVL<>();
		tree.insert(40);
		tree.insert(76);
		tree.insert(30);
		tree.insert(80);
		tree.insert(70);
		tree.insert(12);
		tree.insert(50);
		tree.insert(45);

		tree.levelTraversal();
//		tree.traverseInOrder(tree.root);
//		System.out.println("After delete--->");
//		tree.delete(50);
//		tree.traverseInOrder(tree.root);


	}
}
