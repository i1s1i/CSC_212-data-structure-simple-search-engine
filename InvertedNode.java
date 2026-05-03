
public class InvertedNode {
    String word;
    LinkedList<Results> docs;
    InvertedNode next;

    public InvertedNode(){
        word=null;
        docs=null;
        next=null;
    }

    public InvertedNode(String word, LinkedList<Results> docs) {
        this.word = word;
        this.docs = docs;
        this.next = null;
    }

   }