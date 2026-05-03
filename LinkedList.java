
public class LinkedList<T>{


	private Node<T> head;
	private Node<T> current;

	public LinkedList() {
		head = current = null;
	}

	public boolean empty() {
		return head == null;
	}

	public boolean last() {
		return current.next == null;
	}

	public boolean full() {
		return false;
	}

	public void findFirst() {
		current = head;
	}

	public void findNext() {
		if (current == null) {
			return;
		}
		current = current.next;
	}

	public T retrieve() {
		if (current == null) {
			return null;
		}
		return current.data;
	}

	public void update(T e) {
		current.data = e;
	}

	public void insert(T e) {
		Node<T> newNode = new Node<T>(e);
		if (empty()) {
			head = newNode;
		} else {
			Node<T> tmp = head;
			while (tmp.next != null) {
				tmp = tmp.next;
			}
			tmp.next = newNode;
		}
	}
	
	

	public void remove() {
		if (current == head) {
			head = head.next;
		} else {
			Node<T> tmp = head;
			while (tmp.next != current) {
				tmp = tmp.next;
			}
			tmp.next = current.next;
		}
		if (current.next == null) {
			current = head;
		} else {
			current = current.next;
		}
	}
	
	public boolean contains(T e) {
		current = head;
		while (current != null) {
			if (current.data.equals(e))
				return true;
			current = current.next;
		}

		current = head;
		return false;
	}

	public boolean find(T e) {
		Node<T> temp = head;
    	
    	while (temp != null) {
    		if (current.data == e) {
    			current = temp;
    			return true;
    		}
    		temp = temp.next;
    	}
    	return false;
	}
	
	//this method is customized to help ranking the documents
	public void Rank() {
			if (head == null) return;
			
			Node<T> p, q;
			T temp;

			// Bubble Sort
			for (p = head; p.next != null; p = p.next) {
				for (q = p.next; q != null; q = q.next) {
					Results r1 = (Results) p.data;
					Results r2 = (Results) q.data;

					if (r1.getScore() < r2.getScore()) {
						temp = p.data;
						p.data = q.data;
						q.data = temp;
					}
				}
			}

	}
}