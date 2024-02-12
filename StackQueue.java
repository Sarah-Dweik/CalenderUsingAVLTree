package Components;

public class StackQueue<T extends Comparable<T>> implements XQueueInterface<T> {

	LStack stack = new LStack();

	@Override
	public void enqueue(T data) {
		LStack tempStack = new LStack();

		while (stack.isEmpty() == false)
			tempStack.push((T) stack.pop());

		stack.push(data);
		while (tempStack.isEmpty() == false)
			stack.push((T) tempStack.pop());
	}

	@Override
	public T dequeue() {
		return (T) stack.pop();
	}

	@Override
	public T getFront() {
		return (T) stack.peek();

	}

	@Override
	public T pop() {

		return (T) stack.pop();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void clear() {
		stack.clear();
	}
}
