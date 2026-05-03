
public class QueryProcessor {
	private Index index; 
	private InvertedIndex invIndex; 
	private InvertedIndexBST invIndexBST; 

	//Takes selection from SearchEngine and loads each index into the Query Processor.
	public boolean LoadStructure(int structure) {
		switch (structure) {
		case 1:
			index = Input.LoadIndex();
			return true;
		case 2:
			invIndex = Input.LoadInvertedIndex();
			return true;
		 case 3:
		 invIndexBST = Input.LoadInvertedIndexBST();
		 return true;
		default:
			return false;
		}
	}
	
	//Returns the number of documents in an index.
	public int countDocs() {
		index = Input.LoadIndex();
		int count = 0;
		
		index.findFirst();
		while (index.retrieve() != null) {
			count++;
			index.findNext();
		}
		index.findFirst();
		return count;
	}
	//Returns the number of unique words in the dataset.
	public String wordCount() {
		invIndex = Input.LoadInvertedIndex();
		
		return "Unique Words: " + invIndex.getUniqueWords() + "\nTokens: " + invIndex.getVocab();
	}
		
	//This is the method used by QueryProcessing in order to find the documents a word is in.
	private LinkedList<Results> searchWord (String word, int indexingType) {
		LinkedList<Results> results = new LinkedList<>();
		//Searches through index.
		if (indexingType == 1) {
			index.findFirst();
			Document tmpDoc;
			
			while ((tmpDoc = index.retrieve()) != null) {
				int wordScore = tmpDoc.searchDoc(word);
				if (wordScore != 0) {
					Results r = new Results(tmpDoc, wordScore);
					results.insert(r);
				}
				index.findNext();
			}
		} else if (indexingType == 2) {
			if (invIndex.findWord(word)) 
				results = invIndex.getDocuments();
		}
		
            else if (indexingType == 3) {
                if(invIndexBST.findWord(word))
					results = invIndexBST.getDocuments();
            }
		return results;
		
	}

    //Compares two lists using "AND," then returns the result.
    private LinkedList<Results> calculateAND(LinkedList<Results> list1, LinkedList<Results> list2) {
        LinkedList<Results> finalResult = new LinkedList<Results>();
        list1.findFirst();
        //Iterates through list1
        while (list1.retrieve() != null) {
            Results result1 = list1.retrieve();
            
            //Iterates through list2 to compare the current element.
            list2.findFirst();
            while (list2.retrieve() != null) {
                Results result2 = list2.retrieve();
                
                if (result1.getID() == result2.getID()) {
                    int combinedScore = result1.getScore() + result2.getScore();
                    finalResult.insert(new Results(result1.getDoc(), combinedScore));
                    //If it finds the same result it stops checking for this element.
                    break;
                }
                list2.findNext();
            }
            list1.findNext();
        }
        return finalResult;
    }

    //Similar to the previous method, just using "OR."
    private LinkedList<Results> calculateOR(LinkedList<Results> list1, LinkedList<Results> list2) {
        LinkedList<Results> finalResult = new LinkedList<Results>();
        
        //First we add everything from list1.
        list1.findFirst();
        while (list1.retrieve() != null) {
            finalResult.insert(list1.retrieve());
            list1.findNext();
        }
        
        //Then we add from list2, but this time checking for duplicates.
        list2.findFirst();
        while (list2.retrieve() != null) {
            Results result2 = list2.retrieve();
            boolean exists = false;
            
            //We start from the beginning of the first list and compare every element.
            finalResult.findFirst();
            while (finalResult.retrieve() != null) {
                Results existingResult = finalResult.retrieve();
                
                if (existingResult.getID() == result2.getID()) {
                    exists = true;
                    //Adds to the existing score if found.
                    existingResult.addScore(result2.getScore());
                    break;
                }
                finalResult.findNext();
            }
            if (!exists) {
                finalResult.insert(result2);
            }
            list2.findNext();
        }
        return finalResult;
    }


    //The core of the Query Processor. This method intakes queries from SearchEngine and outputs a list of results.
    public LinkedList<Results> QueryProcessing(String query, int indexingType, String operationType) {
        LinkedList<Results> resultList = new LinkedList<Results>();

        //Tokenizing the query
        String[] tokens = query.split("\\s+");
        
        if (operationType.equalsIgnoreCase("Boolean")) {
        //Using postfix we can process multiple boolean expressions in a single query.
        //Queue is used for its FIFO structure, which helps order expressions in postfix. A stack is used for processing the actual expression.
        LinkedQueue<String> postfixQuery = infixToPostfix(tokens);
        
        LinkedStack<LinkedList<Results>> resultStack = new LinkedStack<>();

        while (postfixQuery.length() > 0) {
            String token = postfixQuery.serve();
            //Remember that the first word of a query is never an operator.
            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                //Assign each operand its list of documents.
                LinkedList<Results> second = resultStack.pop();
                LinkedList<Results> first = resultStack.pop();
                LinkedList<Results> combined;

                //Then perform the boolean operations on the lists, which effectively act as operands here.
                if (token.equalsIgnoreCase("AND"))
                    combined = calculateAND(first, second);
                else
                    combined = calculateOR(first, second);
                //Push the result of the boolean expression back into the stack.
                resultStack.push(combined);
            } else {
                //Search for a list of documents the current word is in, based on indexing type.
                LinkedList<Results> wordResults = searchWord(token, indexingType);
                //Push the list into the aforementioned stack that we'll be using to process complex expressions.
                resultStack.push(wordResults);
            }
        }
        //After performing the operations, only 1 list of results remains.
        resultList = resultStack.pop();
    }
        //Ranked search is simple by comparison.
        else if (operationType.equalsIgnoreCase("Ranked")) {
        	
        	for (int i = 0; i < tokens.length; i++) {
        		LinkedList<Results> tmpResults = searchWord(tokens[i], indexingType);
        		resultList = calculateOR(tmpResults, resultList);
        	}
            resultList.Rank();
        }
        return resultList;
    }

    //This method is used to convert infix expressions to postfix form in order to aid QueryProcessor.
	private LinkedQueue<String> infixToPostfix(String[] expression) { 
		LinkedQueue<String> finalExpression = new LinkedQueue<>();
		LinkedStack<String> operatorStack = new LinkedStack<>();

        for (int i = 0; i < expression.length; i++) {
            String token = expression[i];
            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                while (!operatorStack.empty() && precedence(operatorStack.retrieve()) >= precedence(token)) {
                    finalExpression.enqueue(operatorStack.pop());
                }
                operatorStack.push(token);
            } else {
                finalExpression.enqueue(token);
            }
        }
        while (!operatorStack.empty()) {
            finalExpression.enqueue(operatorStack.pop());
        }
		
		return finalExpression;
	}//This is just a helper for infixToPostfix
	private int precedence(String operator) {
		if (operator.equalsIgnoreCase("AND")) {
			return 2;
		} else if (operator.equalsIgnoreCase("OR")) {
			return 1;
		} else {
			return 0;
		}
	}
}
