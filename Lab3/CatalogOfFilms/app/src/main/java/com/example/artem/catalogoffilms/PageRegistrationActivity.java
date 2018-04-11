package com.example.artem.catalogoffilms;

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
 * Created by Artem on 10.04.2018.
 */

public class PageRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputNewLogin, inputFirstPass, inputRepeatPass, inputFullName, inputDateBirth;
    private Button createNewUserButton;

    // field of class DBHelper
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);

        inputNewLogin = findViewById(R.id.inputNewLogin);
        inputFirstPass = findViewById(R.id.inputFirstPass);
        inputRepeatPass = findViewById(R.id.inputRepeatPass);
        inputFullName = findViewById(R.id.inputFullName);
        inputDateBirth = findViewById(R.id.inputDateBirth);

        createNewUserButton = findViewById(R.id.createNewUserButton);

        // event click button reg new user
        createNewUserButton.setOnClickListener(this);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        // get strings from all inputs
        String NewLogin = inputNewLogin.getText().toString().trim();
        String newFirstPass = inputFirstPass.getText().toString().trim();
        String newRepeatPass = inputRepeatPass.getText().toString().trim();
        String fullName = inputFullName.getText().toString().trim();
        String dateBirth = inputDateBirth.getText().toString().trim();

        // check that all the fields are filled and the passwords are the same
        if(!(newFirstPass.equalsIgnoreCase(newRepeatPass))){
            Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG).show();
        }else if(NewLogin.length() == 0
                || newFirstPass.length() == 0
                || newRepeatPass.length() == 0
                || fullName.length() == 0
                || dateBirth.length() == 0){

            Toast.makeText(this, "Вы не заполнили все поля!", Toast.LENGTH_LONG).show();

        // if all ok that create new user
        }else{

            // object for management DB. Return DB or updatable DB
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            // for add new row. first row: {name: value, lastName: value}, second row {name: value, lastName: value} ...
            ContentValues contentValues = new ContentValues();

            // fill the row new date (create new user)
            contentValues.put(DBHelper.KEY_LOGIN, NewLogin);
            contentValues.put(DBHelper.KEY_PASS, newFirstPass);
            contentValues.put(DBHelper.KEY_FULL_NAME, fullName);
            contentValues.put(DBHelper.KEY_DATE_BIRTH, dateBirth);

            // insert all values in table
            database.insert(DBHelper.TABLE_USERS, null, contentValues);

            dbHelper.close();

            // open login page and transfer new login and pass
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login", NewLogin);
            intent.putExtra("pass", newFirstPass);
            startActivity(intent);
        }
    }
}
