import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Document {
	private int id;
	private String Content;
	public LinkedList<String> tokens;
	private int vocab = 0;
	
	//Initialize a document.
	public Document(int id, String content) {
		this.id = id;
		this.Content = content;
		this.tokens = Document.tokenize(content);
	}
	
	public Document() {
		this.id = -1;
		this.Content = null;
		this.tokens = null;
	}

	public int getId() {
		return id;
	}
	
	public int getVocab() {
		return vocab;
	}
	
	public void setVocab(int number) {
		vocab = number;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
		this.tokens = Document.tokenize(content);
	}
	
	//This method takes a string and splits it into a list of words without stopwords.
	public static LinkedList<String> tokenize(String content) {
		if (content == null)
			return new LinkedList<String>();
		
		LinkedList<String> tokenizedWords = new LinkedList<>();
		String[] splitWords = content.split("[ ,.;]+"); 
		LinkedList<String> stopWords = new LinkedList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("stop.txt"));
			String tmp;
			while((tmp = reader.readLine()) != null)
				stopWords.insert(tmp.toLowerCase().replace("'", ""));
			reader.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}
		
		for (int i = 0; i < splitWords.length; i++) {
		    String word = splitWords[i].toLowerCase().replace("'", "");
		    if (!stopWords.contains(word)) 
		        tokenizedWords.insert(word);
		}
		
		return tokenizedWords;
	}
	
	//This method searches a document and returns the number of times a word appears in it.
	public int searchDoc(String word) {
		if (tokens.empty()) 
			return 0;
		
		int count = 0;
		tokens.findFirst();
		
		while (tokens.retrieve() != null) {
			if (tokens.retrieve().equalsIgnoreCase(word))
				count++;
				tokens.findNext();
		}
		tokens.findFirst();
		return count;
	}
	
	public String toString() {
		String s;
		s= "Document id: " + id + "\nContent: " + getContent();
		return s;
	}

}
