package sqc.cubor_squirrel.WriteLib;

import java.util.Map;
import java.util.TreeMap;

public class BuildCuborBaseDict extends CodeBuilder {

    private Map<String, String> shapeMap = new TreeMap<>();
    private Map<String, String> singleMap = new TreeMap<>();

    BuildCuborBaseDict(String readPath, String writePath) {
        super(readPath, writePath);
    }

    void run() {
        System.out.println("[INFO] - 写入库珀基础字典文件中....");
        this.getSingleMap().getShapeMap().buildDict("1234");
        System.out.println("[INFO] - 写入完成！");
    }

    // 给简体字的词组添加上形码：
    public static void main(String[] args) {
        BuildCuborBaseDict b = new BuildCuborBaseDict(
                "assets/pinyin_simp.dict.yaml",
                "cells/cubor-base.dict.yaml");
    }
}
