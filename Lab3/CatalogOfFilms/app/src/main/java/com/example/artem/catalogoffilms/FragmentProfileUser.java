package com.example.artem.catalogoffilms;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Artem on 10.04.2018.
 */

public class FragmentProfileUser extends Fragment implements View.OnClickListener {

    private TextView userFIO, userDateBirth, userLogin;
    private Button exitButton;

    // field of class DBHelper
    DBHelper dbHelper;

    // create preference cache
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout. fragment_profile_user, container,  false);

        userFIO = view.findViewById(R.id.userFIO);
        userDateBirth = view.findViewById(R.id.userDateBirth);
        userLogin = view.findViewById(R.id.userLogin);

        exitButton = view.findViewById(R.id.exitButton);

        exitButton.setOnClickListener(this);

        preferences = view.getContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        // get user id
        int is_user_id = preferences.getInt("is_user_id",0);

        dbHelper = new DBHelper(view.getContext());

        // object for management DB. Return DB or updatable DB
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_USERS
                + " WHERE " + DBHelper.KEY_ID + " = '"+is_user_id+"'",null);

        cursor.moveToNext();

        int indexFIO = cursor.getColumnIndex(DBHelper.KEY_FULL_NAME);
        int indexDateBirth = cursor.getColumnIndex(DBHelper.KEY_DATE_BIRTH);
        int indexLogin = cursor.getColumnIndex(DBHelper.KEY_LOGIN);

        userFIO.setText(cursor.getString(indexFIO));
        userDateBirth.setText(cursor.getString(indexDateBirth));
        userLogin.setText(cursor.getString(indexLogin));


        cursor.close();
        dbHelper.close();


        return view;
    }

    @Override
    public void onClick(View v) {

        SharedPreferences.Editor editor = preferences.edit();


        editor.putInt("is_auth", 0);
        editor.putInt("is_user_id", 0);
        editor.apply();

        Intent intent = null;

        intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
