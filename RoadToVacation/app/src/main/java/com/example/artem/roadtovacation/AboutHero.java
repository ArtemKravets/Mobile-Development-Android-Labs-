package com.example.artem.roadtovacation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Artem on 20.06.2018.
 */

public class AboutHero extends AppCompatActivity {
    private ImageView fullAvatarHero;

    private TextView fullNameHero, fullDescriptionHero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_hero);

        fullAvatarHero = findViewById(R.id.fullAvatarHero);
        fullNameHero = findViewById(R.id.fullNameHero);
        fullDescriptionHero = findViewById(R.id.fullDescriptionHero);

        Intent intent = getIntent();

        fullNameHero.setText(intent.getStringExtra("currentNameHero"));
        fullDescriptionHero.setText(intent.getStringExtra("currentDescriptionHero"));
        Picasso.get().load(intent.getStringExtra("currentUrlAvatarHero")).into(fullAvatarHero);

    }
}
