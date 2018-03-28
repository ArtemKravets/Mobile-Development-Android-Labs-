package com.example.artem.apppassingtest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Artem on 24.03.2018.
 */

    public class PageRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText inputName,
                     inputLastName,
                     inputPatronymic,
                     inputNewLogin,
                     inputNewPassFirst,
                     inputNewPassSecond;

    private Button createNewUserButton;

    // field of class DBHelper
    DBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);

        inputName = findViewById(R.id.inputName);
        inputLastName = findViewById(R.id.inputLastName);
        inputPatronymic = findViewById(R.id.inputPatronymic);
        inputNewLogin = findViewById(R.id.inputNewLogin);
        inputNewPassFirst = findViewById(R.id.inputNewPassFirst);
        inputNewPassSecond = findViewById(R.id.inputNewPassSecond);
        createNewUserButton = findViewById(R.id.createNewUserButton);

        dbHelper = new DBHelper(this);

        // event click on button
        createNewUserButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        // get strings from all inputs
        String name = inputName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String patronymic = inputPatronymic.getText().toString();
        String newLogin = inputNewLogin.getText().toString();
        String newPassFirst = inputNewPassFirst.getText().toString();
        String newPassSecond = inputNewPassSecond.getText().toString();


        // check that all the fields are filled and the passwords are the same
        if(!(newPassFirst.equalsIgnoreCase(newPassSecond))){

            Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG).show();

        }else if(name.length() == 0
                || lastName.length() == 0
                || patronymic.length() == 0
                || newLogin.length() == 0
                || newPassFirst.length() == 0
                || newPassSecond.length() == 0){

            Toast.makeText(this, "Вы не заполнили все поля!", Toast.LENGTH_LONG).show();

        // if all ok that create new user
        }else{

            // object for management DB. Return DB or updatable DB
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            // for add new row. first row: {name: value, lastName: value}, second row {name: value, lastName: value} ...
            ContentValues contentValues = new ContentValues();

            // fill the row new date (create new user)
            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_LAST_NAME, lastName);
            contentValues.put(DBHelper.KEY_PATRONYMIC, patronymic);
            contentValues.put(DBHelper.KEY_LOGIN, newLogin);
            contentValues.put(DBHelper.KEY_PASS, newPassFirst);
            contentValues.put(DBHelper.KEY_RESULT_TEST, 0);
            contentValues.put(DBHelper.KEY_ARR_ANSWERS, "");

            // insert all values in table
            database.insert(DBHelper.TABLE_USERS, null, contentValues);


//             DEBUG
//            Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null,null);
//
//            if(cursor.moveToFirst()){
//                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//                int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
//                int questionIndex = cursor.getColumnIndex(DBHelper.KEY_ARR_QUESTIONS);
//
//                do{
//                    Log.d("mLog","ID = " + cursor.getInt(idIndex) +
//                    ", login = " + cursor.getString(loginIndex) + ", question = " + cursor.getString(questionIndex));
//
//                } while (cursor.moveToNext());
//            }else{
//                Log.d("mLog", "0 rows");
//            }
//
//            cursor.close();

            dbHelper.close();

            // open login page and transfer new login and pass
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login", newLogin);
            intent.putExtra("pass", newPassSecond);
            startActivity(intent);
        }

    }
}
