package sqc.cubor_squirrel.WriteLib;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class CellDump {

    static ArrayList<String> getFiles(String path) {
        // 列出 cells 文件夹下的所有 细胞词库

        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        if (tempList != null) {
            for (File value : tempList) {
                if (value.isFile()) {
                    files.add(value.toString());
                }
            }
        }
        return files;
    }

    void dump() {
        System.out.println("[INFO] - 正在链接各个细胞词库到 < 主码表文件 > ~");

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
//        Yaml yaml = new Yaml();

        String yamlDir = "/Users/macbook/Library/Rime/cubor.dict.yaml";
        File baseDictFile = new File(yamlDir);

        try(FileReader fr = new FileReader(baseDictFile);
            BufferedReader br = new BufferedReader(fr)
        ) {
            Map result = (Map)yaml.load(br);

            String baseCellDir = "/Users/macbook/Library/Rime/cells/";
            ArrayList<String> cell_files = getFiles(baseCellDir);
            ArrayList<String> cell_names = new ArrayList<>();
            for (String item : cell_files) {
                item = "cells/" + item.replaceAll(baseCellDir, "")
                        .replaceAll(".dict.yaml", "");
                cell_names.add(item);
            }

            result.put("import_tables", cell_names);

            String output = yaml.dump(result);
            FileWriter fw = new FileWriter(yamlDir);
            fw.write(output);
            fw.flush();
            fw.close();

            System.out.println("[INFO] - 细胞词库 -> 链接完毕！~");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CellDump().dump();
    }
}
