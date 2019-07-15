package create_wordList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Merge_Aso {
    static public void encode(List<InputUnit_luna> entries_luna, List<InputUnit_tune> entries_tune, String filename) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (InputUnit_luna entry_luna : entries_luna) {
                    for (InputUnit_tune entry_tune : entries_tune) {
                        if (entry_luna.getText().equals(entry_tune.getText())) {
                            bw.write(entry_luna.getText()+" "+entry_luna.getSpell1());
                            bw.write(entry_tune.getTune());
                            bw.write(entry_luna.getWeight());
                            bw.newLine();
                            System.out.println(entry_tune.getTune());
                        }
                    }
//                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            DictFileReader_luna dfl = new DictFileReader_luna();
            dfl.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\pinyin_simp.dict.yaml");
            List<InputUnit_luna> entries_luna = dfl.getEntries();
            DictFileReader_tune dfh = new DictFileReader_tune();
            dfh.readDictFile("D:\\cubor-tones.dict.yaml");
            List<InputUnit_tune> entries_tune = dfh.getEntries();
            Merge_Aso.encode(entries_luna,entries_tune, "D:\\qpAddSo.dict.yaml");

            xiaoHeEncode_change.encode(entries_luna);//加上这个就是双拼+声调，去掉就是全拼+声调
            Merge_Aso.encode(entries_luna,entries_tune, "D:\\dpAddSo.dict.yaml");
    }
}
