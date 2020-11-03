import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.Table;

import java.util.Random;

public class BattelSequens {
    PApplet p;
    Table questions;
    String questionsText;
    AlmindeligKnap btnAnswerOne, btnAnswerTwo, btnAnswerThree, btnAnswerFour, btnNext ;
    boolean answerd = false;
    int ran;
    boolean visibal = false;
    String[] answers = {"","","",""};
    PVector rbg = new PVector(0,0,0);

    public BattelSequens(PApplet p, Table questions) {
        this.p = p;
        this.questions = questions;
       // ran = (int)p.random(1,questions.getRowCount());
        btnAnswerOne = new AlmindeligKnap(p,100,100,300,50, "1");
        btnAnswerTwo = new AlmindeligKnap(p,100,200,300,50, "2");
        btnAnswerThree = new AlmindeligKnap(p,100,300,300,50, "3");
        btnAnswerFour = new AlmindeligKnap(p,100,400,300,50, "4");
        btnNext = new AlmindeligKnap(p,100,450,300,50, "next");

        newQustion();

    }

    public void drawBattel(){
        erClikked(btnAnswerFour);
        erClikked(btnAnswerThree);
        erClikked(btnAnswerTwo);
        erClikked(btnAnswerOne);
        p.fill(rbg.x,rbg.y,rbg.z);
        p.text(questionsText,100,50);
        btnAnswerFour.tegnKnap();
        btnAnswerThree.tegnKnap();
        btnAnswerTwo.tegnKnap();
        btnAnswerOne.tegnKnap();

        if(answerd){
            btnNext.tegnKnap();
            if(btnNext.klikket){
                answerd = false;
                newQustion();
                btnNext.registrerRelease();
            }
        }

    }

    void clicked(float mouseX, float mouseY){
        if(visibal) {
            btnAnswerOne.registrerKlik(mouseX, mouseY);
            btnAnswerTwo.registrerKlik(mouseX, mouseY);
            btnAnswerThree.registrerKlik(mouseX, mouseY);
            btnAnswerFour.registrerKlik(mouseX, mouseY);
        }

        if(answerd){
            btnNext.registrerKlik(mouseX,mouseY);
        }
    }

    void  erClikked(AlmindeligKnap btn){
        if(btn.klikket && btn.text.equals(questionsText = questions.getString(ran,1)) && !answerd){
            rbg = new PVector(0,200,0);
            btn.registrerRelease();
            answerd = true;
        } else if(btn.klikket && !btn.text.equals(questionsText = questions.getString(ran,1))&& !answerd){
            rbg = new PVector(200,0,0);
            btn.registrerRelease();
            answerd = true;
        }
    }

    void newQustion(){
        rbg = new PVector(0,0,0);
        ran = (int)(p.random(1,questions.getRowCount()));
        questionsText = questions.getString(ran,0);
        for(int i = 0; i < answers.length ; ++i){
            int ranAns = (int)(p.random(1,5));
            System.out.println(ranAns);
            if (!questions.getString(ran,ranAns).equals(answers[i])){
                answers[i] = questions.getString(ran,ranAns);
                System.out.println(answers[i]);
            }
        }
        btnAnswerOne.text = answers[0];
        btnAnswerTwo.text = answers[1];
        btnAnswerThree.text = answers[2];
        btnAnswerFour.text = answers[3];
    }
}
