import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input {

	//This method loads an index with documents as lines each from the dataset file.
	public static Index LoadIndex() {
		Index tmpIndex = new Index();


		try (BufferedReader reader = new BufferedReader(new FileReader("dataset.txt"))) {
			String line;
			int docID = 0;

			while ((line = reader.readLine()) != null) {
				Document tmpDoc = new Document(docID++, line);
				tmpIndex.insert(tmpDoc);
			}

			reader.close();
		} catch (IOException ex) {
			System.out.println("Data set file was not found. Could not input dataset.");
		}
		return tmpIndex;
	}

	//This method loads the inverted index with words and links every word with the documents it appears in.
	public static InvertedIndex LoadInvertedIndex() {
		int vocab = 0;
		int uniqueWords = 0;
		InvertedIndex invIndex = new InvertedIndex();
		LinkedList<String> words = new LinkedList<>();
		LinkedList<Document> docs = new LinkedList<>();

		// Load documents from the dataset file
		try (BufferedReader reader = new BufferedReader(new FileReader("dataset.txt"))) {
			String line;
			int docID = 0;
			while ((line = reader.readLine()) != null) {
				Document tmpDoc = new Document(docID++, line);
				docs.insert(tmpDoc);
			}
		} catch (IOException ex) {
			System.out.println("Dataset file was not found. Could not input dataset.");
			return invIndex;
		}
		//List of words
		docs.findFirst();
		while (docs.retrieve() != null) {
			LinkedList<String> tokens = new LinkedList<>();
			tokens=Document.tokenize(docs.retrieve().getContent());
			tokens.findFirst();
			while (tokens.retrieve() != null) {
				if (!words.contains(tokens.retrieve())) {
					words.insert(tokens.retrieve());
					uniqueWords++;
				}
				vocab++;
				tokens.findNext();
			}
			docs.findNext();
		}

		//build the invertedIndex
		words.findFirst();
		Results r;

		while (words.retrieve() != null) {
			LinkedList<Results> tmpDocs = new LinkedList<>();
			docs.findFirst();
			while (docs.retrieve() != null) {
				int score=docs.retrieve().searchDoc(words.retrieve());
				if (score != 0) {
					r = new Results(docs.retrieve(), score);
					tmpDocs.insert(r);
				}
				docs.findNext();
			}

			invIndex.insert(words.retrieve(),tmpDocs);
			tmpDocs=null;
			words.findNext();
		}
		invIndex.setVocab(vocab);
		invIndex.setUniqueWords(uniqueWords);
		return invIndex;
	}
	
	//Similar to the invertedIndex, just for the InvertedIndexBST this time.
	public static InvertedIndexBST LoadInvertedIndexBST() {
		InvertedIndexBST bstindex = new InvertedIndexBST();
		LinkedList<Document> docs = new LinkedList<>();
		LinkedList<String> words = new LinkedList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader("dataset.txt"))) {
			String line;
			int docID = 0;
			while ((line = reader.readLine()) != null) {
				Document tmpDoc = new Document(docID++, line);
				docs.insert(tmpDoc);
			}
		} catch (IOException ex) {
			System.out.println("Dataset file was not found. Could not input dataset.");
			return bstindex;
		}
		
		docs.findFirst();
		while (docs.retrieve() != null) {
			LinkedList<String> tokens = new LinkedList<>();
			tokens=Document.tokenize(docs.retrieve().getContent());
			tokens.findFirst();
			while (tokens.retrieve() != null) {
				if (!words.contains(tokens.retrieve())) {
					words.insert(tokens.retrieve());
				}
				tokens.findNext();
			}
			docs.findNext();
		}
		words.findFirst();
		Results r;

		while (words.retrieve() != null) {
			LinkedList<Results> tmpDocs = new LinkedList<>();
			docs.findFirst();
			while (docs.retrieve() != null) {
				int score=docs.retrieve().searchDoc(words.retrieve());
				if (score != 0) {
					r = new Results(docs.retrieve(), score);
					tmpDocs.insert(r);
				}
				docs.findNext();
			}

			bstindex.insert(words.retrieve(),tmpDocs);
			tmpDocs=null;
			words.findNext();
		}


		return bstindex;
	}
}
