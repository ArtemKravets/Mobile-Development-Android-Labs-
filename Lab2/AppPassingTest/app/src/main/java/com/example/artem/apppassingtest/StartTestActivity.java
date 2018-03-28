package com.example.artem.apppassingtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Artem on 24.03.2018.
 */

public class StartTestActivity extends AppCompatActivity implements View.OnClickListener {

    // create radio button
    private RadioGroup radioGroup;
    private RadioButton radio_elem1, radio_elem2, radio_elem3;

    private Button nextQuestionButton;
    private TextView currentQuestion;

    // count polls element
    private int index = 0;
    private int wrong_answers = 0;

    public String messageAboutCompPolls = "Вы завалили тест! :(";

    private SharedPreferences preferences;

    // field of class DBHelper
    DBHelper dbHelper;


    public Polls question1 = new Polls("Что пьют на работе android разработчики?",
            "Ананасовый смузи",
            "RedBull",
            "Водка \"Первак\"",
            "Водка \"Первак\"");

    public Polls question2 = new Polls("Во что преобразуется код java после компиляции?",
            "Байт-код",
            "Машинный код",
            "межпространственное-генетический код",
            "Байт-код");

    public Polls question3 = new Polls("Что происходит с байт-кодом далее?",
            "Java скармливает его демону-бездны",
            "Java скармливает его виртуальной машине",
            "Java повторно запускает компиляцию",
            "Java скармливает его виртуальной машине");

    public Polls question4 = new Polls("Какого типа данных нет в java?",
            "int",
            "long",
            "stroka",
            "stroka");

    public Polls question5 = new Polls("Что такое полиморфизм?",
            "Способность обьекта использовать методы производного класса",
            "Способность объекта уничтожать методы произвольного класса",
            "Обычно полиморфизм ко мне приходит после пьянки",
            "Способность обьекта использовать методы производного класса");

    public Polls question6 = new Polls("Какой вопрос НЕ мучает программиста?",
            "Почему это работает?",
            "Почему это не работает?",
            "Каким цетом покрасить ноготочки?",
            "Каким цетом покрасить ноготочки?");

    public Polls question7 = new Polls("При работе с базой данной, какие классы мы используем?",
            "DbHelper и SQLiteDatabase",
            "DbHelper и WorkWithDatabase",
            "HelpMe и WorkHardWithDatabase",
            "DbHelper и SQLiteDatabase");

    public Polls[] arr_questions = {
            question1,
            question2,
            question3,
            question4,
            question5,
            question6,
            question7
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        nextQuestionButton = findViewById(R.id.nextQuestionButton);

        currentQuestion = findViewById(R.id.currentQuestion);

        radioGroup = findViewById(R.id.radioGroup);
        radio_elem1 = findViewById(R.id.radio_elem1);
        radio_elem2 = findViewById(R.id.radio_elem2);
        radio_elem3 =  findViewById(R.id.radio_elem3);

        // init elements on view
        currentQuestion.setText(arr_questions[index].getQuestion());
        radio_elem1.setText(arr_questions[index].getPossible1());
        radio_elem2.setText(arr_questions[index].getPossible2());
        radio_elem3.setText(arr_questions[index].getPossible3());

        dbHelper = new DBHelper(this);

        nextQuestionButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        // get current click radio button
        RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // get pref cache
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        // get user id from cache
        int is_user_id = preferences.getInt("is_user_id",0);

        // update field arr_answers new value
        Cursor cursorAnswer = database.rawQuery("UPDATE " + DBHelper.TABLE_USERS +
                " SET " + DBHelper.KEY_ARR_ANSWERS
                + " = " + DBHelper.KEY_ARR_ANSWERS + "|| '"+rb.getText()+"|' WHERE " + DBHelper.KEY_ID
                + " = " + "'"+is_user_id+"'", null);


        cursorAnswer.moveToFirst();
        cursorAnswer.close();

        // if current click radio != current property obj --> update wrong_answer
        if(rb.getText() != arr_questions[index].getRightAnswer()){
            ++wrong_answers;
        }

        // next question
        ++index;

        // if this click last, show result page
        if(index == arr_questions.length){
            // this crutch. WARNING! This crutch in order to arr don't show exception
            --index;

            if(wrong_answers < 4) messageAboutCompPolls = "Поздравляем! Вы успешно прошли тест! :)";

            Intent intent = new Intent(this, ResultActivity.class);

            // test result as a percentage
            int resultInPercentage = Math.round(((arr_questions.length - wrong_answers) / (float)arr_questions.length) * 100);


            // update field result_test new value
            Cursor cursor = database.rawQuery("UPDATE " + DBHelper.TABLE_USERS +
                    " SET " + DBHelper.KEY_RESULT_TEST
                    + " = '"+resultInPercentage+"' WHERE " + DBHelper.KEY_ID
                    + " = " + "'"+is_user_id+"'", null);

            // you need to call for updates table !!
            cursor.moveToFirst();

            cursor.close();
            dbHelper.close();

            // open result page and result of test data
            intent.putExtra("messageAboutCompPolls", messageAboutCompPolls);
            intent.putExtra("resultMessage", resultInPercentage + "");
            startActivity(intent);
        }

        // init current question new values
        currentQuestion.setText(arr_questions[index].getQuestion());
        radio_elem1.setText(arr_questions[index].getPossible1());
        radio_elem2.setText(arr_questions[index].getPossible2());
        radio_elem3.setText(arr_questions[index].getPossible3());

    }
}
