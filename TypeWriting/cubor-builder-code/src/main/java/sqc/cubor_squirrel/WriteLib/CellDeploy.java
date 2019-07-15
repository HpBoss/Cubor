package sqc.cubor_squirrel.WriteLib;

import java.io.*;
import java.util.ArrayList;

public class CellDeploy {
    private void run() {
        BuildCuborBaseDict b = new BuildCuborBaseDict(
                "assets/pinyin_simp.dict.yaml",
                "cells/cubor-base.dict.yaml");
        b.run();

        String baseCellDir = "/Users/macbook/Library/Rime/cells/";
        ArrayList<String> cellFiles = CellDump.getFiles(baseCellDir);
        for (String cell_name : cellFiles) {
            cell_name = cell_name.replaceAll(CellRebuild.basePath, "");

            if (cell_name.contains("base")||
                    cell_name.contains("egg")) continue;  // 跳过基础词典、彩蛋词典
            CellRebuild c = new CellRebuild(cell_name, "temp-"+cell_name);
            c.run();
        }

        try {
            copyFolder(new File(CellRebuild.basePath+"temp-cells"), new File(CellRebuild.basePath+"cells"));
        } catch (IOException ie) {
            System.out.println("[ERROR] - 拷贝重构后的细胞词库的过程中 IOException ！");
        }

        DeleteDir(new File(CellRebuild.basePath+"temp-cells"));
        new CellDump().dump();
    }

    /**
     * 重新编码搜狗细胞词库过后，将复制 temp-cells 目录内所有文件到 cells 目录
     * @param src : 源文件夹 temp-cells
     * @param dest: 目标文件夹 cells
     * @throws IOException
     */
    private static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                if (!dest.mkdir()){
                    System.out.println("[ERROR] - 找不到库珀细胞词库 cells 文件夹！");
                }
            }
            String[] files = src.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    // 递归复制
                    copyFolder(srcFile, destFile);
                }
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) out.write(buffer, 0, length);
            in.close();     out.close();
        }
    }

    /** @param file: 需要递归删除的文件夹. */
    private static void DeleteDir(File file) {

        if(file.exists()) {
            @SuppressWarnings("unused")
            File[] f = file.listFiles((dir, name) -> {
                File currFile = new File(dir,name);
                if(currFile.isDirectory()) {
                    DeleteDir(currFile);
                }else {
                    System.out.println("[INFO] - 因重构完成, 即将删除："+currFile.getName());
                    if (!currFile.delete())
                        System.out.println("[ERROR] - 文件"+ currFile.getName() +"删除失败");
                }
                return false;
            });
        }
        if (!file.delete()) System.out.println("[ERROR] - temp-cells 文件夹删除失败");
    }

    public static void main(String[] args) {
        new CellDeploy().run();
    }

}
