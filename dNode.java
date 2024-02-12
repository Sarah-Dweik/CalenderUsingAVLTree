package Components;

public class dNode implements Comparable<dNode> {
	private int day;
	public AVL recordTree = new AVL(); // it takes binary and integers

	public dNode(int day) {
		this.day = day;
	}

	
	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public void traverseDay() {
		// Traverse the day node
		// System.out.println(this);
		recordTree.traverseInOrder(recordTree.root);
	}

	public int getYear() {
		return day;
	}

	public void setYear(int year) {
		this.day = year;
	}

	// if already exist but for example we want to update it
	public void insertRecord1(String recordName, double record) {
		rNode newrecord = new rNode(recordName, record);
		recordTree.insert((Comparable) newrecord);
	}

	// if record does not exist
	public void insertRecord2(rNode node) {

	}

	@Override
	public int compareTo(dNode o) {
		if (day > o.day) {
			return 1;
		}
		if (day < o.day) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "[day=" + day + "]";
	}

}
