package create_wordList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merge_dpAsh {
    static public void encode(List<InputUnit_luna> entries_luna,List<InputUnit_heyx> entries_heyx){
        try(FileWriter fw = new FileWriter("D:\\dpAddSh.dict.yaml");
            BufferedWriter bw = new BufferedWriter(fw)){
            for (InputUnit_luna entry_luna:entries_luna){
                int Tlength=0;
                String  pattern="[\\u4e00-\\u9fa5]";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(entry_luna.getText());
                if (m.find()) {
                    int i = 1;
                    while (r.matcher((entry_luna.getText().substring(i))).find()) {
                        i++;
                    }
                    Tlength=i;
                }
                switch (Tlength) {
                    case 1:
                        bw.write(entry_luna.getText()+"\t"+entry_luna.getSpell1());
                        System.out.println(entry_luna.getText()+" "+entry_luna.getSpell1());
                        break;
                    case 2:
                        bw.write(entry_luna.getText()+"\t"+entry_luna.getSpell1()+entry_luna.getSpell2());
                        System.out.println(entry_luna.getText()+" "+entry_luna.getSpell1()+entry_luna.getSpell2());
                        break;
                    case 3:
                        bw.write(entry_luna.getText()+"\t"+entry_luna.getSpell1()+entry_luna.getSpell2()+entry_luna.getSpell3());
                        System.out.println(entry_luna.getText()+" "+entry_luna.getSpell1()+entry_luna.getSpell2()+entry_luna.getSpell3());
                        break;
                    case 4:
                        bw.write(entry_luna.getText()+"\t"+entry_luna.getSpell1()+entry_luna.getSpell2()+entry_luna.getSpell3()+entry_luna.getSpell4());
                        System.out.println(entry_luna.getText()+" "+entry_luna.getSpell1()+entry_luna.getSpell2()+entry_luna.getSpell3()+entry_luna.getSpell4());
                        break;
                }
                for (int j=0;j<entry_luna.getText().length();j++){
                    for (InputUnit_heyx entry_heyx:entries_heyx){
                        if (entry_luna.getText().substring(j,j+1).equals(entry_heyx.getText())){
                            bw.write(entry_heyx.getyCode());
                            System.out.println(entry_heyx.getyCode());
                            break;
                        }
                    }
                }
                bw.write(entry_luna.getWeight());
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DictFileReader_luna dfl = new DictFileReader_luna();
        dfl.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\pinyin_simp.dict.yaml");
        List<InputUnit_luna> entries_luna = dfl.getEntries();
        xiaoHeEncode_change.encode(entries_luna);

        DictFileReader_heyx dfh = new DictFileReader_heyx();
        dfh.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\heyx.dict.yaml");
        List<InputUnit_heyx> entries_heyx = dfh.getEntries();
        Merge_dpAsh.encode(entries_luna,entries_heyx);
    }
}
