package com.example.artem.apppassingtest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Artem on 25.03.2018.
 */

public class ResultActivity extends AppCompatActivity {

    private TextView messageAboutCompletion, resultMessage;

    private SharedPreferences preferences;

    // field of class DBHelper
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        messageAboutCompletion = findViewById(R.id.messageAboutCompletion);
        resultMessage = findViewById(R.id.resultMessage);

        // get data about result test
        Intent intent = getIntent();

        dbHelper = new DBHelper(this);

        messageAboutCompletion.setText(intent.getStringExtra("messageAboutCompPolls"));
        resultMessage.setText("Результат: " + intent.getStringExtra("resultMessage") + "%");
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

        // exit App and clear cache (user id and auth id)
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch(id){
            case R.id.buttonRepeatTest:

                Cursor cursorClearingAnswer = database.rawQuery("UPDATE " + DBHelper.TABLE_USERS +
                        " SET " + DBHelper.KEY_ARR_ANSWERS
                        + " = " + " \"\" WHERE " + DBHelper.KEY_ID
                        + " = " + "'"+preferences.getInt("is_user_id",0)+"'", null);

                cursorClearingAnswer.moveToFirst();
                cursorClearingAnswer.close();
                database.close();

                intent = new Intent(this, StartTestActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonAboutApp:
                // open page About App
                intent = new Intent(this, AboutPageActivity.class);
                startActivity(intent);
                break;
            case R.id.exitButton:


                editor.putInt("is_auth", 0);
                editor.putInt("is_user_id", 0);
                editor.apply();

                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
