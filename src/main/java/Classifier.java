public class Classifier {
    private Model model;

    public Classifier() {
        this.model = new Model();
    }

    public ClassAttributeValue classify(String data) {
        //Here goes the code for comparing the data with the model
        //This is mocked for now
        this.model.classify(data);
        return ClassAttributeValue.MEDIUM_INTENSITY; // mocked for now
    }
}
