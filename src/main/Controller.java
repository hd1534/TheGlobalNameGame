package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {

    //시작 단어들
    String[] startKoDic = {"과자", "자동차", "호랑이"};
    String[] startEnDic = {"snack", "car", "tiger"};
    String[] startChiDic = {"糕点", "汽车", "虎"};
    String[] startJapDic = {"かし", "じどうしゃ", "とら"};

    // 사용된 단어 기록을 위한 List
    List<String> usedWords = new ArrayList<>();

    String language;
    String word, nextword;

    Random random = new Random();

    @FXML private Label title;
    @FXML private Label conf_label;

    @FXML private Button btn_start;
    @FXML private Button btn_config;
    @FXML private Button btn_send;
    @FXML private Button goto_main;

    @FXML private CheckBox conf_kor;
    @FXML private CheckBox conf_eng;
    @FXML private CheckBox conf_chi;
    @FXML private CheckBox conf_jap;

    @FXML private TextField txtInput;

    @FXML private ImageView back_ground;

    @FXML // 설정 페이지로
    public void configAction(ActionEvent event) {
        conf_label.setVisible(true);
        conf_kor.setVisible(true);
        conf_eng.setVisible(true);
        conf_chi.setVisible(true);
        conf_jap.setVisible(true);
        goto_main.setVisible(true);
        btn_start.setVisible(false);
        btn_config.setVisible(false);
        title.setVisible(false);
    }

    // 설정화면에서 메인으로 돌아가는 버튼을 눌렀을때 실행
    @FXML
    public void gotoMainAction(ActionEvent event) {

        // 언어가 2개 이상 선택이 되었는지 확인하는 로직
        int count = 0;
        if (conf_kor.isSelected())
            count ++;
        if (conf_eng.isSelected())
            count ++;
        if (conf_chi.isSelected())
            count ++;
        if (conf_jap.isSelected())
            count ++;

        // 언어가 2개이상 선택되지 않으면 전환 x
        if (count < 2)
        {
            conf_label.setText("최소한 두개 이상\n  선택해 주세요");
            return;
        }

        // 처음에는 새로운 화면으로 스테이지 전환을 했었으나...
        // 컨트롤러가 잘 되지 않았고...
        // 함수를 conf_kor.isSelected()를 다른 함수에서도 써서
        // visible을 설정하는 프로그램이 ㅠㅠ
        conf_label.setVisible(false);
        conf_kor.setVisible(false);
        conf_eng.setVisible(false);
        conf_chi.setVisible(false);
        conf_jap.setVisible(false);
        goto_main.setVisible(false);
        btn_start.setVisible(true);
        btn_config.setVisible(true);
        title.setVisible(true);
        conf_label.setText("언어 설정");
        System.out.println(count);
    }

    @FXML
    public void startAction(ActionEvent event) throws Exception {
        btn_start.setVisible(false);
        btn_config.setVisible(false);
        btn_send.setVisible(true);
        txtInput.setVisible(true);
        //처음에 이부분을 넣었는데, 화면 전환이 한 함수가 전부 진행되고 바뀐다고 하여 ㅠ
//        title.setText("준비");
//
//        Thread.sleep(700);
//
//        title.setText("시작!");
//        Thread.sleep(300);


        // 초기 시작 언어에 맞게 단어 시작을 랜덤하게
        switch (random()) {
            case 0:
                language = "ko";
                word = startKoDic[random.nextInt(startKoDic.length)];
                break;

            case 1:
                language = "en";
                word = startEnDic[random.nextInt(startEnDic.length)];
                break;

            case 2:
                language = "zh-CN";
                word = startChiDic[random.nextInt(startChiDic.length)];
                break;

            case 3:
                language = "ja";
                word = startJapDic[random.nextInt(startJapDic.length)];
                break;
        }
        title.setText(language + " : " + word);
        usedWords.add(word);
    }

    @FXML // 글을 입력 했을때
    public void enterAction(ActionEvent event) throws Exception {
        boolean check;
        nextword = txtInput.getText();
        if (nextword.length() < 2) {
            txtInput.setText("두글자 이상 입력해 주세요");
            return;
        }
        if (compare(word, nextword) == false) {
            txtInput.setText("다시 입력해 주세요");
        }
        else {
            System.out.println("이미 사용한 단어인가? : " + usedWords.contains(nextword));
            if (usedWords.contains(nextword)) {
                txtInput.setText("이미 사용된 단어입니다.");
            }
            else {
                try {
                    switch (language) {
                        case "ko":
                            check = FindKor.find(nextword);
                            break;

                        case "en":
                            check = FindEng.find(nextword);
                            break;

                        case "ja":
                            check = FindJap.find(nextword);
                            break;

                        case "zh-CN":
                            check = FindChi.find(nextword);
                            break;
                        default:
                            // 혹시 모르니
                            check = false;
                    }
                    if (check == false)
                        txtInput.setText("없는 단어 입니다.");
                    else {
                        word = NaverTranslator.run(nextword, language, randomLan());
                        usedWords.add(nextword);
                        title.setText(language + " : " + word);
                    }
                }
                catch (java.net.UnknownHostException jnuhe) {
                    // 인터넷이 없을경우 Find함수들이 위의 에러를 throw 한다!
                    txtInput.setText("인터넷이 필요합니다!");
                }
            }
        }
    }

    // 끝말 잇기가 되는지 확인
    private Boolean compare(String preword, String nextword) {
        if (preword.charAt(preword.length() - 1) == nextword.charAt(0) )
            return true;
        return false;
    }

    // 언어를 랜덤으로 설정하고, 화면에 보여주는 단어를 바꾼다.
    private String randomLan () {
        String[] lan = {"ko", "en", "zh-CN", "ja"};
        String nextLanguage;
        do {
            nextLanguage = lan[random()];
        } while(nextLanguage == language);
        language = nextLanguage;
        return language;
    }

    // 선택된 언어중에서만 랜덤하게 하기 위해서
    private int random () {
        while (true) {
            switch (random.nextInt(4)) {
                case 0:
                    if (conf_kor.isSelected())
                        return 0;
                case 1:
                    if (conf_eng.isSelected())
                        return 1;
                case 2:
                    if (conf_chi.isSelected())
                        return 2;
                case 3:
                    if (conf_jap.isSelected())
                        return 3;
            }
        }
    }


}