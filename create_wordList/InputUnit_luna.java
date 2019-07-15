package create_wordList;

public class InputUnit_luna {
    private String text;
    private String spell1;
    private String spell2;
    private String spell3;
    private String spell4;
    private String weight;

    public InputUnit_luna(String text, String spell1, String weight) {
        this.text = text;
        this.spell1 = spell1;
        this.weight = weight;
    }

    public InputUnit_luna(String text, String spell1, String spell2, String weight) {
        this.text = text;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.weight = weight;
    }

    public InputUnit_luna(String text, String spell1, String spell2, String spell3, String weight) {
        this.text = text;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.spell3 = spell3;
        this.weight = weight;
    }

    public InputUnit_luna(String text, String spell1, String spell2, String spell3, String spell4, String weight) {
        this.text = text;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.spell3 = spell3;
        this.spell4 = spell4;
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public String getSpell1() {
        return spell1;
    }

    public String getSpell2() {
        return spell2;
    }

    public String getSpell3() {
        return spell3;
    }

    public String getSpell4() {
        return spell4;
    }

    public String getWeight() {
        return weight;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSpell1(String spell1) {
        this.spell1 = spell1;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
    }

    public void setSpell3(String spell3) {
        this.spell3 = spell3;
    }

    public void setSpell4(String spell4) {
        this.spell4 = spell4;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
