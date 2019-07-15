package sqc.cubor_squirrel.WriteLib;

import sqc.cubor_squirrel.UnitLib.PhraseUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class CodeBuilder extends RWBase {

    private Map<String, String> shapeMap = new TreeMap<>();
    private Map<String, String> singleMap = new TreeMap<>();

    CodeBuilder(String readPath, String writePath) {
        super(readPath, writePath);
    }

    private void getMap(Map<String, String> map, String readPath) {
        generateMap(map, readPath);
    }

    private static void generateMap(Map<String, String> map, String readPath) {
        try(FileReader fr = new FileReader(new File(readPath));
            BufferedReader br1 = new BufferedReader(fr);
        ) {
            String line;
            // 从形码字典当中构建 Map
            line=br1.readLine();
            while (line != null) {     // 排除读入可能的失误、排除注释
                if (line.length() > 0 && !line.contains("#")){
                    String[] lineUnits = line.split("\t", 2);
                    map.put(lineUnits[0], lineUnits[1]);
                }

                line = br1.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() {
        this.getSingleMap().getShapeMap().buildDict("1234");
    }

    CodeBuilder getSingleMap() {
        this.getMap(singleMap, "/Users/macbook/Library/Rime/cubor/cubor-single.dict.yaml");  return this;  // 获取单字小鹤双拼码字典
    }

    CodeBuilder getShapeMap() {
        this.getMap(shapeMap, "/Users/macbook/Library/Rime/cubor/cubor-shape.dict.yaml");  return this;  // 获取形码字典
    }

    private String[] bSingleEncode(String word, String code) {
        String[] SandSS = new String[3];
        if (singleMap.get(code) != null) {  // 能找到对应的双拼单字码
            code = singleMap.get(code);
        } else {
            code = new DpEncoder().encode(code);
        }
        SandSS[0] = code.replaceAll("\t", "");

        if (shapeMap.get(word) != null) {   // 能找到对应的单字形码
            SandSS[1] = shapeMap.get(word);
            SandSS[2] = SandSS[0] + SandSS[1];
        } else {
            SandSS[1] = "";
            SandSS[2] = SandSS[0];  // 找不到形码就放弃在该字上使用形码
        }

        return SandSS;
    }

    void buildDict(String mode) {
        String line;

        // 避免写入目标不存在，做如下处理：
        String writeDir = writePath.substring(0, writePath.lastIndexOf('/'));
        File dir = new File(writeDir);
        if(!dir.exists())
            if (!dir.mkdirs())
                System.out.println("[ERROR] - 写入目标地址不存在，尝试创建仍然失败！");

        try(FileReader fr = new FileReader(new File(readPath));
            BufferedReader br2 = new BufferedReader(fr);
            FileWriter fw = new FileWriter(writePath);
            BufferedWriter bw = new BufferedWriter(fw)
        ) {
            while ((line=br2.readLine()) != null) {    // 排除读入失误、排除注释
                if (line.contains("\t") && !(line.contains("#") || line.startsWith("-"))){
                    String[] heyxUnits = line.split("\t", 2);
                    //1. heyxUnits[0] -> 词的文字     heyxUnits[1] -> 词的打字编码
                    PhraseUnit punit = new PhraseUnit(heyxUnits[0], heyxUnits[1]);

                    //2. 由于 pinyin_simp 文件 带有词频统计 词频统计数前一定有一个 \t
                    //   所以 将此时混在 punit code部分的 编码和权重 分开
                    if (punit.getCode().contains("\t")) {
                        String[] CandW = punit.getCode().split("\t", 2);
                        punit.setCode(CandW[0]);    punit.setWeight(CandW[1]);
                    }

                    //3. 准备写入:
                    //   * BaseDict 特别说明：由于 pinyin_simp 文件里的 单字带词频 更加好用，所以我们可以暂时放弃用之前做的 cubor.single.dict.yaml


                    // 保留词组的全拼方案
                    String weightText;
                    if (punit.getWeight().equals("")) weightText = punit.getWeight();
                    else weightText = String.format("%.0f", ( Integer.parseInt(punit.getWeight()+0)
                            * Math.log10(Integer.parseInt(punit.getWeight()+1))));// +1 预防 log0 错误...

                    String fullPinyin = punit.getCode();
//                            .replaceAll(" ", "");    // 并记录下全拼(尽量不要省去用于分词的空格)
                    if (mode.contains("1")){
                        String wline = punit.getWord() + "\t" + fullPinyin.strip() + "\t" + weightText;
                        bw.write(wline);
                        bw.newLine();
                    }

                    // 如果是全拼，我们可以用 singleMap 将它处理双拼
                    if (punit.getWord().length()==1) {
                        String[] SandSS = bSingleEncode(punit.getWord(), punit.getCode());

                        punit.setCode(SandSS[0]);  // bEncode 返回的SandSS是 sound双拼声码、shape型码 和 soundShape 声型码
                        // 我们简单在punit中先保留双拼声码

                        if (!fullPinyin.equals(SandSS[2])) {

                            if (SandSS[1].length() != 0) {     // 如果双拼声码和 添加了型码之后的声型码一样 那说明没有找到型码.. 故就不重复写一行声型码了
                                // 能进这里的都是有型码的：
                                bw.write(punit.getWord() + "\t" + SandSS[2] + "\t" + punit.getWeight() + "\n");
                                if (!fullPinyin.equals(SandSS[0])) {   // 即 全拼和双拼不一样（不只有两个编码字符）(避免重复)：
                                    bw.write(punit.getWord() + "\t" + punit.getCode() + "\t" + punit.getWeight() + "\n");
                                    bw.write(punit.getWord() + "\t" + fullPinyin + SandSS[1] + "\t" + punit.getWeight() + "\n");
                                }
                                // 这样我们就在单字上有了 双拼 全拼声码 和 双拼声型码 几种模式
                            }
                        }

                    }
                    else {
                        // 说明是词组
                        String[] SaSS;
                        String[] chars = punit.getWord().split("");
                        String[] pinyins = punit.getCode().split(" ");
                        ArrayList<String[]> SaSSs = new ArrayList<>(3);
                        for(int i=0; i<pinyins.length; i++){    // 对于词组中每一个字的拼音,都会生成一个 SandSS 声-型-声型
                            // 还需要构建词组的:
                            // \w(\w+) -> 双拼(00)(00)(00)$sound -> 声型$sound(000000型):
                            SaSS = bSingleEncode(chars[i], pinyins[i]);
                            SaSSs.add(SaSS);
                        }

                        StringBuilder fullDP = new StringBuilder();     // full Double Pinyin
                        StringBuilder OnlySo = new StringBuilder();     // Only Sound (sound-声母 rime-韵母）
                        StringBuilder OnlySh = new StringBuilder();     // Only Shape
                        for (String[] saSS : SaSSs) {
                            // 3.1 构建双拼(完整的)声码：
                            fullDP.append(saSS[0]);
                            // 3.2 构建声母简写 拼音码（无所谓双拼全拼了）：
                            OnlySo.append(saSS[0].charAt(0));
                            // 3.3 构建声母简写的 声型码：因为形码是添加在声码之后，而上面 3.2 已经取好了简写的双拼声码
                            //     所以这里只需要取形码 然后和 OnlyS 相拼即可
                            if (saSS[1].length() > 0) {
                                OnlySh.append(saSS[1]); // 如果形码不为空字符串才记录
                            }
                        }

                        bw.write(punit.getWord() + "\t" + fullDP.toString() + "\t" + punit.getWeight() );   // 双拼码
                        bw.newLine();
                        bw.write(punit.getWord() + "\t" + OnlySo.toString() + "\t" + punit.getWeight() );   // 声母简拼码
                        bw.newLine();

                        // 3.4 构建带形码的：(5位以上的词语就不要带 全拼+型码了)
                        // 如果 OnlySh 形码存在 -> 形码都是2位 故 OnlySh 的长度为 2*SaSSs （SaSSs的size是词的字数）
                        if (OnlySh.length()/2 == SaSSs.size()){   // 如果不等则说明有字的形码 找不到、未记录
                            // 必须要是能找到形码的才可以添加 全拼型码 和 声型码
                            if (punit.getWord().length() < 2) {
                                String fullpyAndShape = fullPinyin + " " + OnlySh.toString();
                                bw.write(punit.getWord() + "\t" + fullpyAndShape + "\t" + punit.getWeight());   // 全拼 + 形码
                                bw.newLine();
                            }

                            String soundAndShape = OnlySo.toString() + " " + OnlySh.toString();
                            bw.write(punit.getWord() + "\t" + soundAndShape + "\t" + punit.getWeight());   // 双拼 + 形码
                            bw.newLine();
                        }
                    }


                }
                else {
                    bw.write(line);
                    bw.newLine();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
