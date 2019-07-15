package create_wordList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class xiaoHeEncode_change {
    private static Map<String,String> directReplace = new HashMap<>();
    private static  Map<String,String> replace1 = new HashMap<>();
    private static  Map<String,String> replaceNotEqual = new HashMap<>();
    static {
        //零声母：韵母首字母
        //双声母：zh、sh、ch，根据键盘上对应位置转化
        //单字母韵母：零声母+韵母所在键 a 0 i u v
        //双字母韵母：零声母+韵母末字母
        //三字母韵母：零声母+韵母所在键
        directReplace.put("a","aa");//单子韵母
        directReplace.put("o","oo");//单子韵母
        directReplace.put("e","ee");//单子韵母
        directReplace.put("ang","ah");//三字韵母
        directReplace.put("eng","eg");
        //双声母
        replace1.put("zh","v");
        replace1.put("ch","i");
        replace1.put("sh","u");
        // 以这些韵母为结尾的
        // 可能是之前的 i->ch u->sh 已经改成了例如 'uang'->上 这样的形式
        // 在replace1已经把声母部分换成了 单声母
        // 所以 正则只需要判断  韵母部分之前必须有一位
        replaceNotEqual.put("eng", "g");
        replaceNotEqual.put("ong", "s");
        replaceNotEqual.put("ang", "h");
        replaceNotEqual.put("ou", "z");
        replaceNotEqual.put("ia", "x");
        replaceNotEqual.put("ie", "p");
        replaceNotEqual.put("iu", "q");
        replaceNotEqual.put("in", "b");
        replaceNotEqual.put("ua", "x");
        replaceNotEqual.put("uo", "o");
        replaceNotEqual.put("ue", "t");
        replaceNotEqual.put("ve", "t");
        replaceNotEqual.put("un", "y");
        replaceNotEqual.put("ui", "v");
        replaceNotEqual.put("iong", "s");
        replaceNotEqual.put("iang", "l");
        replaceNotEqual.put("uang", "l");
        replaceNotEqual.put("uan", "r");
        replaceNotEqual.put("ing", "k");
        replaceNotEqual.put("uai", "k");
        replaceNotEqual.put("ian", "m");
        replaceNotEqual.put("iao", "n");
        replaceNotEqual.put("an", "j");
        replaceNotEqual.put("ai", "d");
        replaceNotEqual.put("ao", "c");
        replaceNotEqual.put("en", "f");
        replaceNotEqual.put("ei", "w");

        replaceNotEqual.put("a","a");
        replaceNotEqual.put("e","e");
        replaceNotEqual.put("i","i");
        replaceNotEqual.put("u","u");
    }
    static public void encode(List<InputUnit_luna> entries) {
        int Tlength=0,count=0;
        for (InputUnit_luna entry:entries){
            String  pattern="[\\u4e00-\\u9fa5]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(entry.getText());
            if (m.find()) {
                int i = 1;
                while (r.matcher((entry.getText().substring(i))).find()) {
                    i++;
                }
                Tlength=i;
            }
            if (Tlength==1){
                changeWordGraph1(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1());
            }else if (Tlength==2){
                changeWordGraph1(entry);
                changeWordGraph2(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1()+entry.getSpell2());
            }else if (Tlength==3){
                changeWordGraph1(entry);
                changeWordGraph2(entry);
                changeWordGraph3(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1()+entry.getSpell2()+entry.getSpell3());
            }else if (Tlength==4){
                changeWordGraph1(entry);
                changeWordGraph2(entry);
                changeWordGraph3(entry);
                changeWordGraph4(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1()+entry.getSpell2()+entry.getSpell3()+entry.getSpell4());
            }else if (Tlength==5){
                changeWordGraph1(entry);
                changeWordGraph2(entry);
                changeWordGraph3(entry);
                changeWordGraph4(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1()+entry.getSpell2()+entry.getSpell3()+entry.getSpell4()+entry.getSpell5());
            }else if (Tlength==6){
                changeWordGraph1(entry);
                changeWordGraph2(entry);
                changeWordGraph3(entry);
                changeWordGraph4(entry);
                //System.out.println(entry.getText()+" "+entry.getSpell1()+entry.getSpell2()+entry.getSpell3()+entry.getSpell4()+entry.getSpell5()+entry.getSpell6());
            }
        }
    }
    /*
    修改第一个拼音
     */
    private static void changeWordGraph1(InputUnit_luna entry) {
        String code = entry.getSpell1();
        boolean found = false;
        Set<Map.Entry<String,String>> drEntries =directReplace.entrySet();
        for (Map.Entry<String,String> drEntry:drEntries){
            if (code.equals(drEntry.getKey())){
                entry.setSpell1(drEntry.getValue());
                found = true;
                //System.out.println(entry.getText()+" "+entry.getSpell1());
                break;
            }
        }
        if (found){
            return ;
        }
        Set<Map.Entry<String,String>> r1Entries = replace1.entrySet();
        for (Map.Entry<String,String> r1Entry:r1Entries){
            if (code.startsWith(r1Entry.getKey())){
                code=code.replaceAll(r1Entry.getKey(),r1Entry.getValue());
            }
        }
        Set<Map.Entry<String,String>> rneEntries = replaceNotEqual.entrySet();
        for (Map.Entry<String,String> rneEntry:rneEntries){
            if (code.substring(1).equals(rneEntry.getKey())&&!code.equals(rneEntry.getKey())){
                entry.setSpell1(code.replaceAll(rneEntry.getKey(),rneEntry.getValue()));
                //System.out.println(entry.getText()+" "+entry.getSpell1());
                break;
            }
        }
    }
    /*
    修改第二个拼音
     */
    private static void changeWordGraph2(InputUnit_luna entry) {
        String code = entry.getSpell2();
        boolean found = false;
        Set<Map.Entry<String,String>> drEntries =directReplace.entrySet();
        for (Map.Entry<String,String> drEntry:drEntries){
            if (code.equals(drEntry.getKey())){
                entry.setSpell2(drEntry.getValue());
                found = true;
                //System.out.println(entry.getText()+" "+entry.getSpell2());
                break;
            }
        }
        if (found){
            return;
        }
        Set<Map.Entry<String,String>> r1Entries = replace1.entrySet();
        for (Map.Entry<String,String> r1Entry:r1Entries){
            if (code.startsWith(r1Entry.getKey())){
                code=code.replaceAll(r1Entry.getKey(),r1Entry.getValue());
            }
        }
        Set<Map.Entry<String,String>> rneEntries = replaceNotEqual.entrySet();
        for (Map.Entry<String,String> rneEntry:rneEntries){
            if (code.substring(1).equals(rneEntry.getKey())&&!code.equals(rneEntry.getKey())){
                entry.setSpell2(code.replaceAll(rneEntry.getKey(),rneEntry.getValue()));
                //System.out.println(entry.getText()+" "+entry.getSpell2());
                break;
            }
        }
    }
    /*
    修改第三个拼音
     */
    private static void changeWordGraph3(InputUnit_luna entry) {
        String code = entry.getSpell3();
        boolean found = false;
        Set<Map.Entry<String,String>> drEntries =directReplace.entrySet();
        for (Map.Entry<String,String> drEntry:drEntries){
            if (code.equals(drEntry.getKey())){
                entry.setSpell3(drEntry.getValue());
                found = true;
                //System.out.println(entry.getText()+" "+entry.getSpell3());
                break;
            }
        }
        if (found){
            return;
        }
        Set<Map.Entry<String,String>> r1Entries = replace1.entrySet();
        for (Map.Entry<String,String> r1Entry:r1Entries){
            if (code.startsWith(r1Entry.getKey())){
                code=code.replaceAll(r1Entry.getKey(),r1Entry.getValue());
            }
        }
        Set<Map.Entry<String,String>> rneEntries = replaceNotEqual.entrySet();
        for (Map.Entry<String,String> rneEntry:rneEntries){
            if (code.substring(1).equals(rneEntry.getKey())&&!code.equals(rneEntry.getKey())){
                entry.setSpell3(code.replaceAll(rneEntry.getKey(),rneEntry.getValue()));
                //System.out.println(entry.getText()+" "+entry.getSpell3());
                break;
            }
        }
    }
    /*
    修改第四个拼音
     */
    private static void changeWordGraph4(InputUnit_luna entry) {
        String code = entry.getSpell4();
        boolean found = false;
        Set<Map.Entry<String,String>> drEntries =directReplace.entrySet();
        for (Map.Entry<String,String> drEntry:drEntries){
            if (code.equals(drEntry.getKey())){
                entry.setSpell4(drEntry.getValue());
                found = true;
                //System.out.println(entry.getText()+" "+entry.getSpell4());
                break;
            }
        }
        if (found){
            return;
        }
        Set<Map.Entry<String,String>> r1Entries = replace1.entrySet();
        for (Map.Entry<String,String> r1Entry:r1Entries){
            if (code.startsWith(r1Entry.getKey())){
                code=code.replaceAll(r1Entry.getKey(),r1Entry.getValue());
            }
        }
        Set<Map.Entry<String,String>> rneEntries = replaceNotEqual.entrySet();
        for (Map.Entry<String,String> rneEntry:rneEntries){
            if (code.substring(1).equals(rneEntry.getKey())&&!code.equals(rneEntry.getKey())){
                entry.setSpell4(code.replaceAll(rneEntry.getKey(),rneEntry.getValue()));
                //System.out.println(entry.getText()+" "+entry.getSpell4());
                break;
            }
        }
    }
}
