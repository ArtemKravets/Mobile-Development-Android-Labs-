package com.example.artem.catalogoffilms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputLogin, inputPass;

    private Button enterButton, openRegButton;

    // create preference cache
    private SharedPreferences preferences;

    // field of class DBHelper
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        inputLogin = findViewById(R.id.inputLogin);
        inputPass = findViewById(R.id.inputPass);

        enterButton = findViewById(R.id.enterButton);
        openRegButton = findViewById(R.id.openRegButton);

        // get date from PageRegistrationActivity (new login and pass)
        Intent intent = getIntent();

        inputLogin.setText(intent.getStringExtra("login"));
        inputPass.setText(intent.getStringExtra("pass"));

        // get pref cache
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        // get value from cache
        int is_auth = preferences.getInt("is_auth",0);


        // if the user already logged in
        if(is_auth == 1){
            // open polls now
            Intent intentOpenUserActivity = new Intent(this, MainUserPage.class);
            startActivity(intentOpenUserActivity);
        }

        // event click on button
        enterButton.setOnClickListener(this);
        openRegButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()){
            case R.id.enterButton:

                // get strings from all inputs
                String login = inputLogin.getText().toString().trim();
                String pass = inputPass.getText().toString().trim();

                // object for management DB. Return DB or updatable DB
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                // check availability user in DB
                Cursor cursor = database.rawQuery("SELECT * FROM "
                        + DBHelper.TABLE_USERS
                        + " WHERE TRIM(" + DBHelper.KEY_LOGIN + ") = '"+login.trim()+"'",null);

                if(cursor.moveToNext()){
                    // if user exists - check entered pass
                    int indexPass = cursor.getColumnIndex(DBHelper.KEY_PASS);
                    // get id for cache
                    int indexID = cursor.getColumnIndex(DBHelper.KEY_ID);

                    // get pass from table field
                    String userPass = cursor.getString(indexPass);

                    // if pass from BD == pass, which the we entered in input - open startTEst page
                    if(userPass.equalsIgnoreCase(pass)){

                        // get id current user
                        int userId = cursor.getInt(indexID);

                        // create editor and add new value. Remember the user
                        SharedPreferences.Editor editor = preferences.edit();

                        // add flag of auth and current user id
                        editor.putInt("is_auth", 1);
                        editor.putInt("is_user_id", userId);
                        editor.apply();

                        cursor.close();
                        dbHelper.close();

                        intent = new Intent(this, MainUserPage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Пароль введен не верно!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this, "Такого пользователя не существует!", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.openRegButton:
                intent = new Intent(this, PageRegistrationActivity.class);
                startActivity(intent);
                break;
        }


    }
}
