package com.example.artem.catalogoffilms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Artem on 10.04.2018.
 */

public class MainUserPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // create preference cache
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // get pref cache
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        loadFragment(new FragmentListOfFilms());

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

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.transition_list_of_films:
                fragment = new FragmentListOfFilms();
                break;
            case R.id.transition_list_of_series:
                fragment = new FragmentListOfSeries();
                break;
            case R.id.transition_profile_user:
                fragment = new FragmentProfileUser();
                break;
        }

        return loadFragment(fragment);
    }


}
