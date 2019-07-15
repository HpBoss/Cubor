package sqc.cubor_squirrel.UnitLib;

class Unit {
    private String word = "";
    private String code = "";
    private String weight = "";

    Unit() {
    }

    Unit(String word, String code) {
        this.word = word;
        this.code = code;
    }

    Unit(String word, String code, String weight) {
        this.word = word;
        this.code = code;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
