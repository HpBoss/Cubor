package Investigator_interface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dict_list {
    private List<InputUnit> entries;
    public Dict_list(){
        entries = new ArrayList<InputUnit>();
    }
    public String readDictFile(String filename,String character){
        try(FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr)){
            String line = null;
            while ((line=br.readLine())!=null){
                if (line.startsWith("#")||line.length()==0||line.contains("---")
                        ||line.contains("...")||line.contains(":")){
                    continue;
                }else {
                    Pattern reg=Pattern.compile("^(([\\u4e00-\\u9fa5])+)\\s+([a-z]+)\\s+(\\d+)$");
                    Matcher matcher = reg.matcher(line);
                    if (matcher.find()){
                        String text = matcher.group(1);
                        String spell = matcher.group(3);
                        String weight = matcher.group(4);
                        InputUnit iu = new InputUnit(text,spell,weight);
                        //System.out.println(text+"\t"+spell+"\t"+weight);
                        entries.add(iu);
                    }
                }
            }
            StringBuilder results = new StringBuilder();
            for (InputUnit entry:entries){
                if (entry.getText().equals(character)){
                    results.append(entry.getSpell()).append(" ");
                }
            }
            return results.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setEntries(List<InputUnit> entries) {
        this.entries = entries;
    }
    public List<InputUnit> getEntries() {
        return entries;
    }

}
