package Investigator_interface;

public class InputUnit {
    private String text;
    private String spell;
    private String weight;

    public InputUnit(String text, String spell, String weight) {
        this.text = text;
        this.spell = spell;
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public String getSpell() {
        return spell;
    }

    public String getWeight() {
        return weight;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
