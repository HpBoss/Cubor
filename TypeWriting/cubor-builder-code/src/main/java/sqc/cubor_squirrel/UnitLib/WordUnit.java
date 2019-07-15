package sqc.cubor_squirrel.UnitLib;



public class WordUnit extends Unit {

    private String soundCode;
    private String shapeCode;


    public WordUnit() { }

    public WordUnit(String word, String code, String weight) {
        super(word, code, weight);
    }

    public String getSoundCode() {
        return soundCode;
    }

    public void setSoundCode(String soundCode) {
        this.soundCode = soundCode;
    }

    public String getShapeCode() {
        return shapeCode;
    }

    public void setShapeCode(String shapeCode) {
        this.shapeCode = shapeCode;
    }

    public WordUnit(String word, String code) {
        super(word, code);
    }
}
