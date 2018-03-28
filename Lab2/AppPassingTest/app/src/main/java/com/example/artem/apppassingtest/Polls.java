package com.example.artem.apppassingtest;

import java.io.Serializable;

/**
 * Created by Artem on 25.03.2018.
 */

public class Polls implements Serializable {
    private String question;
    private String possible1;
    private String possible2;
    private String possible3;
    private String rightAnswer;

    public Polls(){}

    public Polls(String question, String possible1, String possible2, String possible3, String rightAnswer){
        this.question = question;
        this.possible1 = possible1;
        this.possible2 = possible2;
        this.possible3 = possible3;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getPossible1() {
        return possible1;
    }

    public String getPossible2() {
        return possible2;
    }

    public String getPossible3() {
        return possible3;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setPossible1(String possible1) {
        this.possible1 = possible1;
    }

    public void setPossible2(String possible2) {
        this.possible2 = possible2;
    }

    public void setPossible3(String possible3) {
        this.possible3 = possible3;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
