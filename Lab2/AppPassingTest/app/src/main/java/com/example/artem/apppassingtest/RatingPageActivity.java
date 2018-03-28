package com.example.artem.apppassingtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Artem on 27.03.2018.
 */

public class RatingPageActivity extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<User>();
    AdapterListView adapterListView;

    // field of class DBHelper
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_rating_page);

        dbHelper = new DBHelper(this);

        adapterListView = new AdapterListView(this, users);

        fillData();
        final ListView listOfRating = (ListView)findViewById(R.id.listOfRating);
        listOfRating.setAdapter(adapterListView);


        listOfRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView currentLogin = (TextView) view.findViewById(R.id.textLogin);

                Intent intent = new Intent(getApplicationContext(), AboutUserPage.class);
                intent.putExtra("currentLoginUser", currentLogin.getText());
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), "selected Item Login is " + v.getText(), Toast.LENGTH_LONG).show();

            }


        });

    }

    void fillData() {

        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_USERS,
                null,
                null,
                null,
                null,
                null,
                DBHelper.KEY_RESULT_TEST + " DESC");

        if(cursor.moveToFirst()){
            int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RESULT_TEST);

            do{
                users.add(new User(null, null, null, cursor.getString(loginIndex),
                        String.valueOf(cursor.getString(ratingIndex) + "%")));
            }while(cursor.moveToNext());
        }else{
            Log.d("ratingListView", "0 rows");
        }

    }
}
