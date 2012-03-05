package lockfree.stack;

import java.util.concurrent.atomic.*;

class Node<T> {
	Node<T> next;
	T value;
	
	public Node(T value, Node<T> next) {
		this.next = next;
		this.value = value;
	}
}

public class Stack<T> {
	AtomicReference<Node<T>> top = new AtomicReference<Node<T>>();
	
	public void push(T value) {
		boolean sucessful = false;
		while (!sucessful) {
			Node<T> oldTop = top.get();
			Node<T> newTop = new Node<T>(value, oldTop);
			sucessful = top.compareAndSet(oldTop, newTop);
		};
	}
	
	public T peek() {
		return top.get().value;
	}
	
	public T pop() {
		boolean sucessful = false;
		Node<T> newTop = null;
		Node<T> oldTop = null;
		while (!sucessful) {
			oldTop = top.get();
			newTop = oldTop.next;
			sucessful = top.compareAndSet(oldTop, newTop);
		}
		return oldTop.value;
	}
	
	public void print() {
		for (Node<T> node = top.get(); node != null; node = node.next) {
			System.out.println(node.value);
		}
	}
}
