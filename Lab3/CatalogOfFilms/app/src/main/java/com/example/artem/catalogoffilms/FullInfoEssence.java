package com.example.artem.catalogoffilms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Artem on 10.04.2018.
 */

public class FullInfoEssence extends AppCompatActivity {

    private ImageView posterFull;
    private TextView titleFull, countryFull, ratingFull, descriptionFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_essence);

        posterFull = findViewById(R.id.posterFull);

        titleFull = findViewById(R.id.titleFull);
        countryFull = findViewById(R.id.countryFull);
        ratingFull = findViewById(R.id.ratingFull);
        descriptionFull = findViewById(R.id.descriptionFull);

        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();

        Bitmap bmp = (Bitmap) extras.getParcelable("currentPoster");

        // get all data from FragmentList
        posterFull.setImageBitmap(bmp);

        titleFull.setText(intent.getStringExtra("currentTitle"));
        countryFull.setText(intent.getStringExtra("currentCountry"));
        ratingFull.setText(intent.getStringExtra("currentRating"));
        descriptionFull.setText(intent.getStringExtra("currentDescription"));

        descriptionFull.setMovementMethod(new ScrollingMovementMethod());



    }
}
