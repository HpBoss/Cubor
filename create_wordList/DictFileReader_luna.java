package create_wordList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictFileReader_luna {
    private List<InputUnit_luna> entries;
    public  DictFileReader_luna(){
        entries = new ArrayList<InputUnit_luna>();
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
//                    Pattern reg2=Pattern.compile("^(([\\u4e00-\\u9fa5]){2})\t([a-z]+)\\s([a-z]+)(\\s(\\d+))$");
//                    Pattern reg3=Pattern.compile("^(([\\u4e00-\\u9fa5]){3})\t([a-z]+)\\s([a-z]+)\\s([a-z]+)(\\s(\\d+))$");
//                    Pattern reg4=Pattern.compile("^(([\\u4e00-\\u9fa5]){4})\t([a-z]+)\\s([a-z]+)\\s([a-z]+)\\s([a-z]+)(\\s(\\d+))$");
                    Matcher matcher1 = reg1.matcher(line);
//                    Matcher matcher2 = reg2.matcher(line);
//                    Matcher matcher3 = reg3.matcher(line);
//                    Matcher matcher4 = reg4.matcher(line);
                    if (matcher1.find()){
                        String text = matcher1.group(1);
                        String spell1 = matcher1.group(3);
                        String weight = matcher1.group(4);
                        InputUnit_luna iu = new InputUnit_luna(text,spell1,weight);
                        System.out.println(text+"\t"+spell1);
                        entries.add(iu);
                    }
//                    else if (matcher2.find()){
//                        String text = matcher2.group(1);
//                        String spell1 = matcher2.group(3);
//                        String spell2 = matcher2.group(4);
//                        String weight = matcher2.group(5);
//                        InputUnit_luna iu = new InputUnit_luna(text,spell1,spell2,weight);
//                        System.out.println(text+"\t"+spell1+" "+spell2);
//                        entries.add(iu);
//                    }else if (matcher3.find()){
//                        String text = matcher3.group(1);
//                        String spell1 = matcher3.group(3);
//                        String spell2 = matcher3.group(4);
//                        String spell3 = matcher3.group(5);
//                        String weight = matcher3.group(6);
//                        InputUnit_luna iu = new InputUnit_luna(text,spell1,spell2,spell3,weight);
//                        System.out.println(text+"\t"+spell1+" "+spell2+" "+spell3);
//                        entries.add(iu);
//                    }else if (matcher4.find()){
//                        String text = matcher4.group(1);
//                        String spell1 = matcher4.group(3);
//                        String spell2 = matcher4.group(4);
//                        String spell3 = matcher4.group(5);
//                        String spell4 = matcher4.group(6);
//                        String weight = matcher4.group(7);
//                        InputUnit_luna iu = new InputUnit_luna(text,spell1,spell2,spell3,spell4,weight);
//                        System.out.println(text+"\t"+spell1+" "+spell2+" "+spell3+" "+spell4);
//                        entries.add(iu);
//                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEntries(List<InputUnit_luna> entries) {
        this.entries = entries;
    }

    public List<InputUnit_luna> getEntries() {
        return entries;
    }

    public static void main(String[] args) {
        DictFileReader_luna df = new DictFileReader_luna();
        df.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\pinyin_simp.dict.yaml");
    }
}