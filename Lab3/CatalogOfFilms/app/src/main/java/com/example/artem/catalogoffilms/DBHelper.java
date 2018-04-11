package com.example.artem.catalogoffilms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Artem on 10.04.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    // constant for update DB
    public static final int DATABESE_VERSION = 1;
    public static final String DATABASE_NAME = "CatalogOfFilms";
    public static final String TABLE_USERS = "users";

    // create field of table
    public static final String KEY_ID = "_id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASS = "pass";
    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_DATE_BIRTH = "date_birth";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABESE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + "("
                + KEY_ID + " integer primary key,"
                + KEY_LOGIN + " text,"
                + KEY_PASS + " text,"
                + KEY_FULL_NAME + " text,"
                + KEY_DATE_BIRTH + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);

        onCreate(db);
    }
}
