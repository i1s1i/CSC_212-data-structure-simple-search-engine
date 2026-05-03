import java.util.InputMismatchException;
import java.util.Scanner;

public class SearchEngine {

    public static int indexingType() {
        System.out.println("Which type of indexing would you like to use?\n" +
                "1) Index\n" +
                "2) Inverted Index\n" +
                "3) Inverted Index using BST");
        System.out.print("Enter the number of your choice: ");

        int indextype = 0;
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            try {
                int index = scanner.nextInt();
                switch (index) {
                    case 1:
                        indextype = 1; //Index
                        done = true;
                        break;
                    case 2:
                        indextype = 2; //Inverted Index
                        done = true;
                        break;
                    case 3:
                        indextype = 3; //BST
                        done = true;
                        break;
                    default:
                        System.out.print("Please enter a valid choice (1-3): ");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a numeric value (1-3): ");
                scanner.nextLine(); 
            }
        }
        return indextype;
    }

    public static void main(String[] args) {
        QueryProcessor queryProcessor = new QueryProcessor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Search Engine");
        int choice = -1;
        int indexing = indexingType(); 
        queryProcessor.LoadStructure(indexing);

        do {
            System.out.println("\n1) Boolean Retrieval\n" +
                    "2) Ranked Retrieval\n" +
                    "3) Indexed Documents\n" +
                    "4) Indexed Tokens\n" +
                    "5) Change Indexing Type\n" +
                    "6) Exit");
            System.out.print("Please enter the number of your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Please enter your query: ");
                        String query = scanner.nextLine().toLowerCase();
                        LinkedList<Results> results = queryProcessor.QueryProcessing(query, indexing, "Boolean");
                        if (results.empty()) {
                        	System.out.println("No such documents were found.");
                        	break;
                        }
                        results.findFirst();
                        while (results.retrieve() != null) {
                            System.out.println(results.retrieve());
                            results.findNext();
                        }
                        break;
                    case 2:
                        System.out.print("Please enter your query: ");
                        String query2 = scanner.nextLine().toLowerCase();
                        LinkedList<Results> resultsRanked = queryProcessor.QueryProcessing(query2, indexing, "Ranked");
                        if (resultsRanked.empty()) {
                        	System.out.println("No such documents were found.");
                        	break;
                        }
                        resultsRanked.findFirst();
                        while (resultsRanked.retrieve() != null) {
                            System.out.println(resultsRanked.retrieve().printWithScore());
                            resultsRanked.findNext();
                        }
                        break;
                    case 3:
                        System.out.println("Numer of documents in index: " + queryProcessor.countDocs());
                        break;
                    case 4:
                    	System.out.println(queryProcessor.wordCount());
                        break;
                    case 5:
                        indexing = indexingType();
                        queryProcessor.LoadStructure(indexing);
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Please enter a valid choice (1-6).");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric value (1-6).");
                scanner.nextLine();
            }
        } while (choice != 6);
        System.out.println("Thank you for using the Search Engine.");
    }
}
