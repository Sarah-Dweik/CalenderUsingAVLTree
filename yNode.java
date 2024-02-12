package Components;

public class yNode implements Comparable <yNode> {
	private int year;
	public  AVL<mNode> monthTree = new AVL<mNode>();

	public yNode(int year) {
		this.year = year;
	}
	
	 public void traverseYear() {
	        // Traverse the year node
	        System.out.println(this);

	        // Traverse the month tree
	        monthTree.traverseInOrder(monthTree.root);
	    }

	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void insertMonth(mNode node) {
		monthTree.insert(node);
	}
	public void insertMonth(String month) {
		mNode newnode = new mNode(month);
		monthTree.insert(newnode);
	}

	@Override
	public int compareTo(yNode o) {
		if(year> o.year) {
			return 1;
		}
		if(year <o.year) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "[year=" + year + "]";
	}
	

}
