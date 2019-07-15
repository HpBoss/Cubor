package create_wordList;

public class InputUnit_heyx {
    private String text;
    private String sCode;
    private String yCode;
    private String weight;

    public InputUnit_heyx(String text, String sCode, String yCode, String weight) {
        this.text = text;
        this.sCode = sCode;
        this.yCode = yCode;
        this.weight = weight;
    }

    public InputUnit_heyx(String text, String sCode, String yCode) {
        this.text = text;
        this.sCode = sCode;
        this.yCode = yCode;
    }

    public String getText() {
        return text;
    }

    public String getsCode() {
        return sCode;
    }

    public String getyCode() {
        return yCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public void setyCode(String yCode) {
        this.yCode = yCode;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
