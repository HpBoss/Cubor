package create_wordList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictFileReader_heyx {
    private List<InputUnit_heyx> entries;
    public  DictFileReader_heyx(){
        entries = new ArrayList<InputUnit_heyx>();
    }
    public void readDictFile(String filename){
        try(FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("D:\\xingma.dict.yaml");
            BufferedWriter bw = new BufferedWriter(fw);){
            String line = null;
            while ((line=br.readLine())!=null){
                if (line.startsWith("#")||line.length()==0||line.contains("---")
                        ||line.contains("...")||line.contains(":")){
                    continue;
                }else if (line.contains("#以下爲詞組")){
                    break;
                }else {
                    Pattern reg1=Pattern.compile("^(([\\u4e00-\\u9fa5]){1})\t([a-z]{2})([a-z]{2})$");
                    Pattern reg2=Pattern.compile("^(([\\u4e00-\\u9fa5]){1})\t([a-z]{2})([a-z]{1})$");
                    Matcher matcher1 = reg1.matcher(line);
                    Matcher matcher2 = reg2.matcher(line);
                    System.out.println(line);
                    if (matcher1.find()){
                        String text = matcher1.group(1);
                        String sCode = matcher1.group(3);
                        String yCode = matcher1.group(4);
                        bw.write(text+"\t"+sCode+" "+yCode);
                        bw.write("\n");
                        //System.out.println(text+" "+sCode+" "+yCode);
                        InputUnit_heyx iu = new InputUnit_heyx(text,sCode,yCode);
                        entries.add(iu);
                    }
                    else if (matcher2.find()){
                        String text = matcher2.group(1);
                        String sCode = matcher2.group(3);
                        String yCode = matcher2.group(4);
                        bw.write(text+"\t"+sCode+" "+yCode);
                        bw.write("\n");
                        //System.out.println(text+" "+sCode+" "+yCode);
                        InputUnit_heyx iu = new InputUnit_heyx(text,sCode,yCode);
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

//    public static void main(String[] args) {
//        DictFileReader_heyx dfh = new DictFileReader_heyx();
//        dfh.readDictFile("C:\\Users\\Daniel James\\AppData\\Roaming\\Rime\\heyx.dict.yaml");
//    }
    public void setEntries(List<InputUnit_heyx> entries) {
        this.entries = entries;
    }

    public List<InputUnit_heyx> getEntries() {
        return entries;
    }
}