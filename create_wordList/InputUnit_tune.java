package create_wordList;

public class InputUnit_tune {
    String text;
    String tune;
    String weight;

    public InputUnit_tune(String text, String tune, String weight) {
        this.text = text;
        this.tune = tune;
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTune() {
        return tune;
    }

    public void setTune(String tune) {
        this.tune = tune;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
