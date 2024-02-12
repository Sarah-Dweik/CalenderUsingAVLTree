package Components;

public class mNode implements Comparable<mNode> {
	private String month;
	public AVL<dNode> dayTree = new AVL<dNode>();

	public mNode(String month) {
		this.month = month;
	}

	public void traverseMonth() {

		dayTree.traverseInOrder(dayTree.root);
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	// insert into the dayTree
	public void insertDay(dNode node) {
		dayTree.insert(node);
	}

	// insert a month
	public void insertDay(int month) {
		dNode newnode = new dNode(month);
		dayTree.insert(newnode);
	}

	@Override
	public String toString() {
		return "[month=" + month + "]";
	}

	@Override
	public int compareTo(mNode o) {
		if (month.compareTo(o.month) > 0) {
			return 1;
		}
		if (month.compareTo(o.month) < 0) {
			return -1;
		}
		return 0;
	}
	
//	@Override
//	public int compareTo(mNode o) {
//	    if (month == null && o.month == null) {
//	        return 0; // Both months are null, consider them equal
//	    }
//	    if (month == null) {
//	        return -1; // Null is considered smaller than any non-null value
//	    }
//	    if (o.month == null) {
//	        return 1; // Non-null is considered larger than null
//	    }
//	    
//	    return month.compareTo(o.month);
//	}

}
