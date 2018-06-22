package com.example.artem.roadtovacation;

import android.content.Intent;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.artem.roadtovacation.Fragments.FragmentAboutMe;
import com.example.artem.roadtovacation.Fragments.FragmentListOfComic;
import com.example.artem.roadtovacation.Fragments.FragmentCurrentListOfHeroes;
import com.example.artem.roadtovacation.Fragments.FragmentFavorites;

/**
 * Created by Artem on 20.06.2018.
 */

public class MainUserPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new FragmentListOfComic());


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
            case R.id.openListOfComic:
                fragment = new FragmentListOfComic();
                break;
            case R.id.currentListHero:
                fragment = new FragmentCurrentListOfHeroes();
                break;
            case R.id.openFavorites:
                fragment = new FragmentFavorites();
                break;
            case R.id.openSettingsUser:
                fragment = new FragmentAboutMe();
                break;
        }

        return loadFragment(fragment);
    }



    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Выходим из аккаунта!", Toast.LENGTH_LONG).show();

        HelperMethods.ClearCacheUser(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
