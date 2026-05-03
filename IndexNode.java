
public class IndexNode {
	
	public Document doc;
	public IndexNode next;
	
	public IndexNode() {
		this.doc = null;
		this.next = null;
	}
	
	public IndexNode(Document d) {
		this.doc = d;
		this.next = null;
	}
}