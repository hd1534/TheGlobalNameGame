package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;


public class NaverTranslator {

    public static void main(String[] args) {
        String clientId = "sT2Au7CvVgKBdZso5Rcb";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "vTiC5WDNI5";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("안녕하세요. 오늘 기분은 어떻습니까?", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            parseJson(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String run(String word, String language, String nextLanguage) throws Exception{
        String clientId = "sT2Au7CvVgKBdZso5Rcb";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "vTiC5WDNI5";//애플리케이션 클라이언트 시크릿값";
        String text = URLEncoder.encode(word, "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        // post request
        String postParams = "source=" + language + "&target=" + nextLanguage + "&text=" + text;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println(response.toString());
        return parseJson(response.toString()).replaceAll("[.]", "");
    }

    private static String parseJson(String json) throws Exception {
        Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);
        Map<String, Object> message = (Map<String, Object>) map.get("message");
        Map<String, String> result = (Map<String, String>) message.get("result");
        return result.get("translatedText");
    }
}