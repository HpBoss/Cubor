package sqc.regextraining;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTraining {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("请输入一个网址：");
        String url = in.nextLine();
        String pattern1 = "((https|http)(://))?((\\d+\\.)+(\\d+)|((\\w\\.)*\\w+))(:[0-9]+)?(/\\w*)*/?";
        Pattern u = Pattern.compile(pattern1);
        Matcher mu = u.matcher(url);
        if (mu.find()){
            System.out.println("这是一个网址");
        } else {
            System.out.println("这不是一个网址");
        }

        System.out.println("请输入一个邮箱地址：");
        String email = in.nextLine();
        String pattern2 = "(\\w+)@(\\w+)(\\.\\w+)";
        Pattern e = Pattern.compile(pattern2);
        Matcher me = e.matcher(email);
        if (me.find()){
            System.out.println("这是一个邮箱");
        } else {
            System.out.println("这不是一个邮箱");
        }
    }
}
