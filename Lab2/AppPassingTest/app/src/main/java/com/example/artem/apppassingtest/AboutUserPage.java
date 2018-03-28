package com.example.artem.apppassingtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Artem on 27.03.2018.
 */

public class AboutUserPage extends AppCompatActivity {

    private TextView currentUserFIO, currentUserRating;
    private String currentLogin;

    // field of class DBHelper
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);

        currentUserFIO = findViewById(R.id.currentUserFIO);
        currentUserRating = findViewById(R.id.currentUserRating);

        Intent intent = getIntent();

        // get current login selected user
        currentLogin = intent.getStringExtra("currentLoginUser");

//        Toast.makeText(this, String.valueOf(currentLogin), Toast.LENGTH_LONG).show();

        dbHelper = new DBHelper(this);


        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_USERS
                + " WHERE TRIM(" + DBHelper.KEY_LOGIN + ") = '"+currentLogin.trim()+"'",null);

        cursor.moveToNext();

        int indexName = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int indexLastName = cursor.getColumnIndex(DBHelper.KEY_LAST_NAME);
        int indexPatronymic = cursor.getColumnIndex(DBHelper.KEY_PATRONYMIC);
        int indexRating = cursor.getColumnIndex(DBHelper.KEY_RESULT_TEST);
        int indexAnswersString = cursor.getColumnIndex(DBHelper.KEY_ARR_ANSWERS);

        String currentName =  cursor.getString(indexName);
        String currentLastName =  cursor.getString(indexLastName);
        String currentPatronymic =  cursor.getString(indexPatronymic);
        String currentAnswersString =  cursor.getString(indexAnswersString);

        String[] arrQuestion = currentAnswersString.split("\\|");

        int currentRating =  cursor.getInt(indexRating);

//        // DEBUG
//        Cursor cursorTest = database.query(DBHelper.TABLE_USERS, null, null, null, null, null,null);
//
//        if(cursorTest.moveToFirst()){
//            int idIndex = cursorTest.getColumnIndex(DBHelper.KEY_ID);
//            int loginIndex = cursorTest.getColumnIndex(DBHelper.KEY_LOGIN);
//            int questionIndex = cursorTest.getColumnIndex(DBHelper.KEY_ARR_ANSWERS);
//
//            do{
//                Log.d("mLog","ID = " + cursorTest.getInt(idIndex) +
//                        ", login = " + cursorTest.getString(loginIndex) + ", answer = " + cursorTest.getString(questionIndex));
//
//            } while (cursorTest.moveToNext());
//        }else{
//            Log.d("mLog", "0 rows");
//        }
//
//        cursorTest.close();


        currentUserFIO.setText(currentName + " " + currentLastName + " " + currentPatronymic);
        currentUserRating.setText(currentRating + "%");
        
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.about_user_layout);

        if(currentAnswersString.length() != 0){

            LinkedHashMap< String, String > map = new LinkedHashMap< String, String >();

            map.put("Что пьют на работе android разработчики?", arrQuestion[0]);
            map.put("Во что преобразуется код java после компиляции?", arrQuestion[1]);
            map.put("Что происходит с байт-кодом далее?", arrQuestion[2]);
            map.put("Какого типа данных нет в java?", arrQuestion[3]);
            map.put("Что такое полиморфизм?", arrQuestion[4]);
            map.put("Какой вопрос НЕ мучает программиста?", arrQuestion[5]);
            map.put("При работе с базой данной, какие классы мы используем?", arrQuestion[6]);


            for (LinkedHashMap.Entry<String, String> entry : map.entrySet()) {
                System.out.println("Вопрос =  " + entry.getKey() + " Ответ = " + entry.getValue());

                TextView textViewQuestion = new TextView(this);
                textViewQuestion.setText(entry.getKey());
                textViewQuestion.setPadding(0, 0,0,20);
                textViewQuestion.setTypeface(null, Typeface.BOLD);
                textViewQuestion.setTextColor(Color.RED);
                mainLayout.addView(textViewQuestion);

                TextView textViewAnswer = new TextView(this);
                textViewAnswer.setText(entry.getValue());
                textViewAnswer.setPadding(0, 0,0,20);
                mainLayout.addView(textViewAnswer);
            }
        }else{
            Toast.makeText(this, "Пользователь не проходил тест!", Toast.LENGTH_LONG).show();
        }


//        Toast.makeText(this, String.valueOf(currentLogin), Toast.LENGTH_LONG).show();

//        for (int i=0; i < arrQustion.length-1; i++) {
//
//            TextView textView = new TextView(this);
//            textView.setText(arrQustion[i]);
//            textView.setPadding(0, 0,0,20);
//            mainLayout.addView(textView);
//
//        }

        cursor.close();
        dbHelper.close();

    }
}
