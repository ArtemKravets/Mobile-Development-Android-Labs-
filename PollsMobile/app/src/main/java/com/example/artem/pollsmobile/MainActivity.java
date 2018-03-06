package com.example.artem.pollsmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// realized interface click
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // create fields from view

    private EditText editPass;
    private Button enterButton;

    // create preference cache
    private SharedPreferences preferences;

    // constant

    private final String login = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init view

        editPass = (EditText) findViewById(R.id.editPass);
        enterButton = (Button) findViewById(R.id.enterButton);

        // event click on button

        enterButton.setOnClickListener(this);

        // get pref cache
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);


        // get value from cache
        int is_auth = preferences.getInt("is_auth",0);


        // if the user already logged in
        if(is_auth == 1){
            // open polls now
            Intent intent = new Intent(this,PollsActivity.class);
            startActivity(intent);
        }

    }


    @Override
    public void onClick(View v) {
        // get values from input pass

        String editVarPass = editPass.getText().toString().trim();


        // check pass
        if(editVarPass.equalsIgnoreCase(login)){

            // create editor and add new value. Remember the user

            SharedPreferences.Editor editor = preferences.edit();

            editor.putInt("is_auth", 1);
            editor.apply();

            // open new page
            Intent intent = new Intent(this, PollsActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show();
        }
    }
}
