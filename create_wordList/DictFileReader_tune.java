package create_wordList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictFileReader_tune {
    private List<InputUnit_tune> entries;
    public  DictFileReader_tune(){
        entries = new ArrayList<InputUnit_tune>();
    }
    public void readDictFile(String filename){
        try(FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr)){
            String line = null;
            while ((line=br.readLine())!=null){
                if (line.startsWith("#")||line.length()==0||line.contains("---")
                        ||line.contains("...")||line.contains(":")){
                    continue;
                }else {
                    Pattern reg1=Pattern.compile("^(([\\u4e00-\\u9fa5]){1})\t([a-z]+)(\\s(\\d+))$");
                    Matcher matcher1 = reg1.matcher(line);
                    if (matcher1.find()){
                        String text = matcher1.group(1);
                        String spell1 = matcher1.group(3);
                        String weight = matcher1.group(4);
                        InputUnit_tune iu = new InputUnit_tune(text,spell1,weight);
                        //System.out.println(text+"\t"+spell1);
                        entries.add(iu);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEntries(List<InputUnit_tune> entries) {
        this.entries = entries;
    }

    public List<InputUnit_tune> getEntries() {
        return entries;
    }

    public static void main(String[] args) {
        DictFileReader_tune df = new DictFileReader_tune();
        df.readDictFile("D:\\cubor-tones.dict.yaml");
    }
}
