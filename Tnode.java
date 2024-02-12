package Components;



public class Tnode<T extends Comparable<T>> implements Comparable {

	private Tnode<T> right;
	private Tnode<T> left;
	private T data;

	public Tnode(T data) {
		this.data = data;
	}
	
	public boolean hasLeft() {
		if(left==null) {
			return false;
		}
		return true;
	}
	
	public boolean hasRight() {
		if(right==null) {
			return false;
		}
		return true;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Tnode getRight() {
		return right;
	}

	public void setRight(Tnode right) {
		this.right = right;
	}

	public Tnode getLeft() {
		return left;
	}

	public void setLeft(Tnode left) {
		this.left = left;
	}
	public boolean isLeaf() {
		if(right==null && left==null) {
			return true;
		}
		return false;
	}

//	@Override
//	public String toString() {
//		return "[data=" + data + "]";
//	}
	
	@Override
	public String toString() {
	    return String.valueOf(data);
	}

@Override
public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return 0;
}

}
