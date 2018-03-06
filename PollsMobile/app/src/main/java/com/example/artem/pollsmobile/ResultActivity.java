package com.example.artem.pollsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Artem on 06.03.2018.
 */

public class ResultActivity extends AppCompatActivity {

    private TextView messageAboutCompletion;
    private TextView rightAnswerMessage;
    private TextView wrongAnswerMessage;

    // CREATED VIEW
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        messageAboutCompletion = (TextView) findViewById(R.id.messageAboutCompletion);
        rightAnswerMessage = (TextView) findViewById(R.id.rightAnswerMessage);
        wrongAnswerMessage = (TextView) findViewById(R.id.wrongAnswerMessage);

        Intent intent = getIntent();

        messageAboutCompletion.setText(intent.getStringExtra("messageAboutCompPolls"));
        rightAnswerMessage.setText("Количество правильных ответов: " + intent.getStringExtra("rightAnswerMessage"));
        wrongAnswerMessage.setText("Количество неправльных ответов: " + intent.getStringExtra("wrongAnswerMessage"));


    }
}
