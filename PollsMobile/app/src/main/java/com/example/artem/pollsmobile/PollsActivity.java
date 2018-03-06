package com.example.artem.pollsmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Artem on 04.03.2018.
 */

public class PollsActivity extends AppCompatActivity implements View.OnClickListener{

    //message about completion of the polls
    private String messageAboutCompPolls = "Вы завалили тест! :(";

    // create radio button
    private RadioGroup radioGroup;
    private RadioButton radio_elem1;
    private RadioButton radio_elem2;
    private RadioButton radio_elem3;


    private Button nextPollButton;
    private TextView currentQuestion;

    private SharedPreferences preferences;


    // count poll element

    private int index = 0;
    private int wrong_aswers = 0;

    // created obj poll

    public Polls poll1 = new Polls("Что пьют на работе android разработчики?",
            "Ананасовый смузи",
            "RedBull",
            "Водка \"Первак\"",
            "Водка \"Первак\"");

    public Polls poll2 = new Polls("Во что преобразуется код java после компиляции?",
            "Байт-код",
            "Машинный код",
            "межпространственное-генетический код",
            "Байт-код");

    public Polls poll3 = new Polls("Что происходит с байт-кодом далее?",
            "Java скармливает его демону-бездны",
            "Java скармливает его виртуальной машине",
            "Java повторно запускает компиляцию",
            "Java скармливает его виртуальной машине");

    public Polls poll4 = new Polls("Какого типа данных нет в java",
            "int",
            "long",
            "stroka",
            "stroka");

    public Polls poll5 = new Polls("Что такое полиморфизм?",
            "Способность обьекта использовать методы производного класса",
            "Способность объекта уничтожать методы произвольного класса",
            "Обычно полиморфизм ко мне приходит после пьянки",
            "Способность обьекта использовать методы производного класса");

    public Polls[] arr_polls = {
            poll1,
            poll2,
            poll3,
            poll4,
            poll5
    };



    // CREATED VIEW
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polls_activity);

        currentQuestion = (TextView) findViewById(R.id.currentQuestion);

        // init RadioButton
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radio_elem1 = (RadioButton) findViewById(R.id.radio_elem1);
        radio_elem2 = (RadioButton) findViewById(R.id.radio_elem2);
        radio_elem3 = (RadioButton) findViewById(R.id.radio_elem3);


        currentQuestion.setText(arr_polls[index].getQuestion());
        radio_elem1.setText(arr_polls[index].getPossible1());
        radio_elem2.setText(arr_polls[index].getPossible2());
        radio_elem3.setText(arr_polls[index].getPossible3());


        Button nextPollButton = (Button)findViewById(R.id.nextPollButton);

        nextPollButton.setOnClickListener(this);

        Intent intent = getIntent();

    }



    // CREATED MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // create menu from folder "menu"

        getMenuInflater().inflate(R.menu.polls_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // processing menu items

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        Intent intent = null;

        switch(id){
            case R.id.aboutApp:
                // open page About App
                intent = new Intent(this, AboutAppActivity.class);
                startActivity(intent);
                break;
            case R.id.exitButton:
                // exit App and clear cache
                preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("is_auth", 0);
                editor.apply();

                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onClick(View v) {




        // get current click radio button
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        // if current click radio != current property obj --> update wrong_answer
        if(rb.getText() != arr_polls[index].getRightAnswer()){
            ++wrong_aswers;
        }


        ++index;

        // if this click last, show result page
        if(index == arr_polls.length){
            // this crutch. WARNING! This crutch in order to arr don't show exception
            --index;



            Intent intent = new Intent(this, ResultActivity.class);

            if(wrong_aswers <= 3) messageAboutCompPolls = "Поздравляем! Вы успешно прошли тест! :)";

            intent.putExtra("messageAboutCompPolls", messageAboutCompPolls);
            intent.putExtra("rightAnswerMessage", (arr_polls.length - wrong_aswers) + "");
            intent.putExtra("wrongAnswerMessage", wrong_aswers + "");
            startActivity(intent);
        }


        //Toast.makeText(this, String.valueOf(wrong_aswers), Toast.LENGTH_LONG).show();




        currentQuestion.setText(arr_polls[index].getQuestion());
        radio_elem1.setText(arr_polls[index].getPossible1());
        radio_elem2.setText(arr_polls[index].getPossible2());
        radio_elem3.setText(arr_polls[index].getPossible3());

    }
}
