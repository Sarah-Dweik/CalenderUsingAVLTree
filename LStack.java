package Components;


public class LStack <T extends Comparable<T>> implements Stackable <T> {

	LinkedList<T> list = new LinkedList();

	@Override
	public void push(T data) {
		list.insertFirst(data);
	}
	@Override
	public T pop() {
		if(!isEmpty()) {
			T top =  (T) list.dummy.getNext().getData();
			list.delete(top);
			return top;
		}
		return null;
	}

	
	@Override
	public T peek() {
		if(!isEmpty()) {
			T top =  (T) list.dummy.getNext().getData();
			list.delete(top);
			return top;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		if(list.dummy.getNext()==null) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		list.dummy.setNext(null);
	}

}

class FineStackArray{
	
}