package com.example.artem.roadtovacation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Artem on 20.06.2018.
 */

public class HelperMethods {

    // Ca
    private static SharedPreferences preferences;

    // clear cache (user id and auth id)
    static void ClearCacheUser(Context context){

        preferences = context.getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("is_auth", 0);
        editor.putInt("is_user_id", 0);
        editor.apply();

    }
}
