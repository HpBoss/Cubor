package sqc.cubor_squirrel.WriteLib;

import java.io.*;
import java.util.ArrayList;

class CellRebuild extends CodeBuilder {

    CellRebuild(String readPath, String writePath) {
        super(readPath, writePath);
    }

    void run() {
        String dict_table_name = writePath.replaceAll(basePath, "")
                .replaceAll("\\.dict\\.yaml", "")
                .replaceAll("/cells/", "");
        System.out.println("[INFO] - 重新构建细胞词库 < " + dict_table_name + " >");
        this.getSingleMap().getShapeMap().buildDict("1234");
        System.out.println("[INFO] - 重构完成！");
    }

}
