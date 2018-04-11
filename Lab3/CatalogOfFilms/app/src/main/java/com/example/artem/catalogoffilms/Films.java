package com.example.artem.catalogoffilms;

import android.graphics.drawable.Drawable;

/**
 * Created by Artem on 10.04.2018.
 */

public class Films {
    public Drawable poster;
    public String title;
    public String country;
    public String rating;
    public String description;

    public Films(){

    }

    public Films(Drawable poster, String title, String country, String rating, String description) {
        this.poster = poster;
        this.title = title;
        this.country = country;
        this.rating = rating;
        this.description = description;
    }
}
