package main;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FindEng {
    public static void main(String[] args) throws Exception{
        find("hellow");
    }
    // 인터넷 사전에서 이 단어가 있는지를 확인한다.
    public static boolean find(String word) throws Exception{
        try {
            Document doc = Jsoup.connect("https://www.dictionary.com/browse/" + word + "?s=t").get();
            System.out.println("존재하는 단어 입니다.");
            return true;
        }
        catch (HttpStatusException hse){
            System.out.println("없는 단어 입니다.");
            // hse.printStackTrace();
            return false;
        }
    }
}