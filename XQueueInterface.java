package Components;

public interface XQueueInterface<T> {
	
	public void enqueue(T data) ;
	public T dequeue();
	public T getFront();
	public T pop();
	public boolean isEmpty();
	public void clear();

}
