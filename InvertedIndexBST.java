public class InvertedIndexBST {

	//BST of lists implementation.
    private BST<LinkedList<Results>> bst;

    public InvertedIndexBST() {
        bst = new BST<>();
    }

    public boolean empty() {
        return bst.empty();
    }

    public boolean full() {
        return bst.full();
    }

    public String retrieve() {
    	return bst.current.key;
    }
    
    public void insert(String word, LinkedList<Results> docs) {
        bst.insert(word, docs);
    }
    
    //Using BSTs reduces the time complexity to O(logn).
    public boolean findWord(String word) {
        return bst.findkey(word);
    }

    public LinkedList<Results> getDocuments() {
        return bst.current.data;
    }

    public void remove() {
        bst.remove_key(bst.current.key);
    }

    public void update(String word, LinkedList<Results> docs) {
        bst.update(word, docs);
    }
}
