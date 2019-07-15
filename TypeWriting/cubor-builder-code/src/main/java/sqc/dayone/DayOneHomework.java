package sqc.dayone;

import java.io.*;
import java.util.regex.Pattern;

public class DayOneHomework {

    private static boolean Useful(String _line) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fcc]");
        return p.matcher(_line).find();
    }

    public static void main(String[] args) {
        try(FileReader fr = new FileReader(new File("/Users/macbook/Library/Rime/sqc.dict.yaml"));
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("/Users/macbook/Library/Rime/cubor.dict.yaml");
            BufferedWriter bw = new BufferedWriter(fw)
        ) {
            String line;
            while((line = br.readLine()) != null) {
                StringBuilder writeLine = new StringBuilder();
                if(Useful(line) || line.length()==0 ) {
                    if (line.length()==0) writeLine = new StringBuilder();
                    else {
                        int round = 0;
                        for (String unit : line.split("\t", 2)){
                            if(round==0) writeLine.insert(0, unit);
                            else writeLine.insert(0, unit + "\t");
                            round ++;
                        }
                    }
                    bw.write(writeLine.toString());
                    bw.write("\n");
                }
            }

            System.out.println("写入完成...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
