package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FindChi {
    public static void main(String[] args) throws Exception{
        find("안녕");
    }
    public static boolean find(String word) throws Exception {
        // 인터넷 사전에서 이 단어가 있는지를 확인한다.
        Document doc = Jsoup.connect("http://www.mantou.co.kr/index.php?code=word&search=yes&q=" + word).get();
        if (doc.html().contains("의 검색 결과가 없습니다.")) {
            System.out.println("없는 단어 입니다.");
            return false;
        }
        else {
            System.out.println("존재하는 단어 입니다.");
            return true;
        }
    }
}
