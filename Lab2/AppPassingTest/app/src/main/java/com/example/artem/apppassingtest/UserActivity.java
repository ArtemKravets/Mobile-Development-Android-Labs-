package com.example.artem.apppassingtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Artem on 25.03.2018.
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonStartTest,
                   buttonRating;

    DBHelper dbHelper;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        buttonStartTest = findViewById(R.id.buttonStartTest);
        buttonRating = findViewById(R.id.buttonRating);

        dbHelper = new DBHelper(this);

        // get pref cache
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        // event click on button
        buttonStartTest.setOnClickListener(this);
        buttonRating.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        // opens the activity depending on the button you press
        switch (v.getId()){
            case R.id.buttonStartTest:

                // object for management DB. Return DB or updatable DB
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                Cursor cursor = database.rawQuery("UPDATE " + DBHelper.TABLE_USERS +
                        " SET " + DBHelper.KEY_ARR_ANSWERS
                        + " = " + " \"\"," + DBHelper.KEY_RESULT_TEST + " = 0 " + "WHERE " + DBHelper.KEY_ID
                        + " = " + "'"+preferences.getInt("is_user_id",0)+"'", null);

                cursor.moveToFirst();
                cursor.close();
                database.close();

                intent = new Intent(this, StartTestActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonRating:
                intent = new Intent(this, RatingPageActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Выходим из аккаунта!", Toast.LENGTH_LONG).show();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("is_auth", 0);
        editor.putInt("is_user_id", 0);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}