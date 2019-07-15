package sqc;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Frequency {

    private void CheckWordLine() {
        try(FileReader fr = new FileReader(new File("/Users/macbook/Library/Rime/luna_pinyin.dict.yaml"));
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("/Users/macbook/Library/Rime/frequency-analysis.yaml");
            BufferedWriter bw = new BufferedWriter(fw)
        ) {
            String line;    // 用于记录字典的单行
            Map<String, Integer> analysis = new TreeMap<>(Comparator.naturalOrder());


            while((line = br.readLine()) != null) {
                if(line.length()!=0) {
                    String[] units = line.split("\t", 2);

                    if (units.length > 1 && units[0].length() <= 1 && !units[0].equals("#")){
                        // 从词频Map中获取可能记录过的词键
                        int unit_frequency;

                        // 处理 词键中也有百分比的情况：
                        if (units[1].contains("\t")) {
                            units[1] = units[1].split("\t")[0];
                        }

                        if (analysis.get(units[1]) == null)     // 若没有记录过该输入词键，则初始化频率为0.0
                            unit_frequency = 0;
                        else
                            unit_frequency = analysis.get(units[1]);
                        analysis.put(units[1], ++unit_frequency);
                    }
                }
            }

            // 统计总数 为计算词频做准备：
            long sum = 0;
            for (String unit_key : analysis.keySet()) {
                sum += analysis.get(unit_key);
            }

            bw.write("---- Luna Dict Single Key-word Analysis---\n    明月词典单字词键索引统计\n\n");
            for (String unit_key : analysis.keySet()) {
                bw.write(unit_key + (unit_key.length()<4?"\t":"")
                        + "   - 出现次数：" + analysis.get(unit_key)
                        + "\t - 频率：" + String.format("%.3f", ((float)analysis.get(unit_key)/sum)*100) + "%" + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Just for Test
    public static void main(String[] args) {
        new Frequency().CheckWordLine();
    }
}
