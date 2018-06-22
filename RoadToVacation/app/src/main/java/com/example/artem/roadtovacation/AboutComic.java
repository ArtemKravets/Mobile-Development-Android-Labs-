package com.example.artem.roadtovacation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Artem on 21.06.2018.
 */

public class AboutComic extends AppCompatActivity {

    private ImageView fullPosterComic;

    private TextView fullTitleComic, fullDescriptionComic, fullPriceComic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_comic);

        fullPosterComic = findViewById(R.id.fullPosterComic);
        fullTitleComic = findViewById(R.id.fullTitleComic);
        fullDescriptionComic = findViewById(R.id.fullDescriptionComic);
        fullPriceComic = findViewById(R.id.fullPriceComic);

        Intent intent = getIntent();

        fullTitleComic.setText(intent.getStringExtra("currentTitleComic"));
        fullDescriptionComic.setText(intent.getStringExtra("currentDescriptionComic"));
        fullPriceComic.setText(intent.getStringExtra("currentPriceComic"));
        Picasso.get().load(intent.getStringExtra("currentUrlPosterComic")).into(fullPosterComic);

    }

}
