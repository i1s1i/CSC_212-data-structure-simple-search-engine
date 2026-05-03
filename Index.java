
//List of Lists implementation.
public class Index { 
	private IndexNode head;
	private IndexNode current;

	public Index() {
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
		current = current.next;
	}

	public boolean find(int id) {
		current = head;
		while (current != null) {
			if (current.doc.getId() == id)
				return true;
			current = current.next;
		}
		current = head;
		return false;
	}

	public Document retrieve() {
		if (current == null)
			return null;
		return current.doc;
	}

	public LinkedList<String> retrieveWords() {

		return current != null ? current.doc.tokens : null;
	}

	public String printInfo() {
		String info = "Document ID: " + current.doc.getId() + "\nKeywords: ";
		String tmp = "";

		current.doc.tokens.findFirst();
		while (!current.doc.tokens.last()) {
			tmp += current.doc.tokens.retrieve() + ", ";
			current.doc.tokens.findNext();
		}
		tmp += current.doc.tokens.retrieve() + ".";
		info += tmp;

		return info;
	}

	public void update(Document d) {
		current.doc = d;
	}

	public void insert(Document d) {
		if (empty()) {
			current = head = new IndexNode(d);
		} else {
			IndexNode tmp = current.next;
			current.next = new IndexNode(d);
			current = current.next;
			current.next = tmp;
		}
	}

	public void remove() {
		if (current == head) {
			head = head.next;
		} else {
			IndexNode tmp = head;
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
}