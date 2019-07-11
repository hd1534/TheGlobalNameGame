package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FindJap {
    public static void main(String[] args) throws Exception{
        find("안녕");
    }
    // 인터넷 사전에서 이 단어가 있는지를 확인한다.
    public static boolean find(String word) throws Exception {
        Document doc = Jsoup.connect("https://www.weblio.jp/content/" + word).get();
        if (doc.html().contains("に一致する見出し語は見つかりませんでした。")) {
            System.out.println("없는 단어 입니다.");
            return false;
        }
        else {
            System.out.println("존재하는 단어 입니다.");
            return true;
        }
    }
}
