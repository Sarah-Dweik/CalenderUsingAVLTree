package Components;


public class Node <T extends Comparable <T>> implements Comparable <Node<T>>{
	private T data ;
	private Node<T> next;

	public Node(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	

	@Override
	public String toString() {
		return " [data=" + data + "]";
	}



	@Override
	public int compareTo(Node <T> newnode) {
		if(newnode.getData()==null && this.data==null) {
			return 0; // equal
		}
		else if(this.data== null) {
			return -1; //always consider the null is smallest value
		}else if (newnode.getData() == null) {
			return 1;   // Any non-null value is considered greater than null
		} else {
			return this.data.compareTo(newnode.getData());
		}
	}



}
