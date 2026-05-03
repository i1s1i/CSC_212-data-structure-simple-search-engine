public class Results {
    private Document doc;
    private int score;


    public Results() {
        doc = null;
        score = 0;
    }
    public Results(Document doc, int score) {
        this.doc = doc;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        score = s;
    }

    public void addScore(int s) {
        score += s;
    }

    public Document getDoc() {
        return doc;
    }

    public int getID() {
        return doc.getId();
    }

    public void setID(int i) {
        doc.setId(i);
    }

    public String toString() {
        String s;

        s = "Document ID: " + doc.getId();

        return s;
    }
    public String printWithScore() {
        String s;

        s = "Document ID: " + doc.getId() + " Score: " + score;

        return s;
    }

}