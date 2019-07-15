package sqc.cubor_squirrel.WriteLib;


import sqc.cubor_squirrel.UnitLib.WordUnit;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DpEncoder extends RWBase {
    private HashMap<String, String> directReplace = new HashMap<>();
    private HashMap<String, String> replace1 = new HashMap<>();
    private HashMap<String, String> replace2 = new HashMap<>();
    private HashMap<String, String> replaceNotEqual = new HashMap<>();

    public DpEncoder() {
        this.initRefMap();
    }

    private DpEncoder(String readPath, String writePath) {
        super(readPath, writePath);
        this.initRefMap();
    }

    private void initRefMap(){
        // 初始化 双拼键盘映射表
        //  Map< src, encoded >
        directReplace.put("a", "aa");       // 直接替换掉
        directReplace.put("o", "oo");
        directReplace.put("e", "ee");
        directReplace.put("ang", "ah");

        // 替换代码中的卷舌声母(以这些开头的)
        replace1.put("zh", "v");
        replace1.put("ch", "i");
        replace1.put("sh", "u");

        // 以这些韵母为结尾的
        // 可能是之前的 i->ch u->sh 已经改成了例如 'uang'->上 这样的形式
        // 在replace1已经把声母部分换成了 单声母
        // 所以 正则只需要判断  韵母部分之前必须有一位
        replace2.put("eng", "g");
        replace2.put("ong", "s");
        replace2.put("ang", "h");
        replace2.put("ou", "z");
        replace2.put("ia", "x");
        replace2.put("ie", "p");
        replace2.put("iu", "q");
        replace2.put("in", "b");
        replace2.put("ua", "x");
        replace2.put("uo", "o");
        replace2.put("ue", "t");
        replace2.put("ve", "t");
        replace2.put("un", "y");
        replace2.put("ui", "v");
        replace2.put("iong", "s");
        replace2.put("iang", "l");
        replace2.put("uang", "l");
        replace2.put("uan", "r");
        replace2.put("ing", "k");
        replace2.put("uai", "k");
        replace2.put("ian", "m");
        replace2.put("iao", "n");
        replace2.put("an", "j");
        replace2.put("ai", "d");
        replace2.put("ao", "c");
        replace2.put("en", "f");
        replace2.put("ei", "w");
    }

    public String encode(String code) {

        if (code.length() == 2) return code;
        for (String src : directReplace.keySet())
            if (code.equals(src))
                code = code.replaceAll(src, directReplace.get(src));

        if (code.length() == 2) return code;
        for (String src : replace1.keySet())
            if (Pattern.compile("^"+src).matcher(code).find())
                code = code.replaceAll(src, replace1.get(src));

        if (code.length() == 2) return code;
        for (String src : replace2.keySet())
            if (Pattern.compile("^\\w"+src+"$").matcher(code).find() && !code.equals(src))
                code = code.replaceAll(src, replace2.get(src));

        return code;
    }

    private void handle() {
        String line;

        try(FileReader fr = new FileReader(new File(readPath));
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(writePath);
            BufferedWriter bwDP = new BufferedWriter(fw)
        ) {

            while ((line = br.readLine()) != null) {
                if(line.length()!=0) {
                    String[] units = line.split("\t", 2);
                    if (units.length > 1 && units[0].length() <= 1 && !units[0].equals("#")){
                        // 到此证明是有用的行，把单字的词键 用小鹤的双拼编码 进行转换：
                        WordUnit unit = new WordUnit(units[0], units[1]);

                        // 处理 词键中也有百分比的情况：
                        if (unit.getCode().contains("\t")) {
                            String[] CandW = unit.getCode().split("\t");
                            unit.setCode(CandW[0]);
                            unit.setWeight(CandW[1]);
                        }

                        if (unit.getCode().length() <= 2) {
                            // 如果他已经是两码了就可以不用改了，小鹤拼音部分的核心目的是只需要敲两下键盘就能出来字
                            bwDP.write(unit.getWord() + "\t" + unit.getCode() + "\t" + unit.getWeight() + "\n");
                        } else {
                            // 对该行记录的 词键进行 小鹤编码转换
                            String sc_str = "[\\u4E00-\\u9FA5]+$";
                            Pattern sc_pattern = Pattern.compile(sc_str);
                            Matcher m = sc_pattern.matcher(unit.getWord());
                            if (m.find()){  // 说明是简体
                                unit.setCode(encode(unit.getCode()));

                                String w;
                                if (unit.getWeight().length() == 0) w = "";
                                else w = (Double.parseDouble(unit.getWeight())==0.0)?"0":unit.getWeight();

                                bwDP.write(unit.getWord() + "\t" + unit.getCode() + "\t" + w + "\n");
                            } // else 繁体就跳过
                        }
                    } else {
                        // 词组 注释和文档说明都不再写入...
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Just for Test
    public static void main(String[] args) {
        // 书写不带音调的单字双拼声码
        new DpEncoder("/assets/pinyin_simp.dict.yaml", "cubor/cubor-single.dict.yaml").handle();
    }
}
