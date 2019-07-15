package sqc.cubor_squirrel.WriteLib;

public class RWBase {

    static String basePath = "/Users/macbook/Library/Rime/";    // Rime 用户配置文件夹
    protected String readPath;
    protected String writePath;

    protected RWBase() {
    }

    public RWBase(String readPath, String writePath) {
        this.readPath = basePath + readPath;
        this.writePath = basePath + writePath;
    }

}
