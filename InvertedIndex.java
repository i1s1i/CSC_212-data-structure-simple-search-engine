public class InvertedIndex {

    private InvertedNode head;
    private InvertedNode current;
    private int vocabulary;
    private int uniqueWords;

    public InvertedIndex() {
        head = current = null;
    }
    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current.next==null;
    }

    public boolean full(){
        return false;
    }

    public void findFirst(){
        current = head;
    }

    public void findNext(){
        current = current.next;
    }
    
    public int getVocab() {
    	return vocabulary;
    }
    
    public void setVocab(int number) {
    	vocabulary = number;
    }
    
    public int getUniqueWords() {
    	return uniqueWords;
    }
    
    public void setUniqueWords(int number) {
    	uniqueWords = number;
    }

    public String retrieve(){
        if(current==null){
            return null;
        }
        return current.word;
    }
    public void remove(){
        if (current == head) {
            head = head.next;
        } else {
            InvertedNode tmp = head;
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

    //Add new word and its documents
    public void insert(String word, LinkedList<Results> docs) {
      if(empty()){
          current = head=new InvertedNode(word, docs);
      }
      else{
          InvertedNode tmp = current.next;
          current.next = new InvertedNode(word, docs);
          current = current.next;
          current.next = tmp;
      }
    }
    
    //This method will go through every word, and, if found, will return true and set the pointer on it. otherwise false. O(n).
    public boolean findWord(String word) {
    	InvertedNode temp = head;
    	
    	while (temp != null) {
    		if (temp.word.equalsIgnoreCase(word)) {
    			current = temp;
    			return true;
    		}
    		temp = temp.next;
    	}
    	return false;
    }

    public LinkedList<Results> getDocuments() {
        return current.docs;
    }

}