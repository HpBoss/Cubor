package sqc.cubor_squirrel.WriteLib;


import sqc.cubor_squirrel.UnitLib.WordUnit;

import java.io.*;

public class WriteShapeCode extends RWBase {

    private WriteShapeCode(String readPath, String writePath) {
        super(readPath, writePath);
    }

    private void writeShapeCode() {
        String line;

        try(FileReader fr = new FileReader(new File(readPath));
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(writePath);
            BufferedWriter bw = new BufferedWriter(fw)
        ) {
            // 写入文件头部 说明信息：
            bw.write("# ---- Cubor 输入法 形码分析YAML文档 ----\n\n");

            while ((line = br.readLine()) != null){
                if (line.length() > 0) {    // 保证不是空行
                    String[] units = line.split("\t", 2);
                    // heyx.dict.yaml 文件( forked from https://raw.githubusercontent.com/liming2013/rime-flypy-xhup/master/heyx.dict.yaml )
                    // 没有权重比例，所以可以直接分割成两个部分
                    WordUnit unit = new WordUnit();
                    if (units[0].length() == 1 && units[1].length() >= 3) {
                        unit.setWord(units[0]); unit.setCode(units[1]);
                        unit.setSoundCode(unit.getCode().substring(0,2));
                        unit.setShapeCode(unit.getCode().substring(2,unit.getCode().length()));    // 提取单字形码

                        bw.write(unit.getWord() + "\t" + unit.getShapeCode() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 提取形码
    public static void main(String[] args) {

        // heyx 里有单字的形码
        WriteShapeCode fsc = new WriteShapeCode("/assets/heyx.dict.yaml", "/cubor/cubor-shape.dict.yaml");
        fsc.writeShapeCode();

    }
}
