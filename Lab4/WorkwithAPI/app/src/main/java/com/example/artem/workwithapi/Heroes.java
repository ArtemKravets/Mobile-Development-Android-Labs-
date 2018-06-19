package com.example.artem.workwithapi;

import android.graphics.drawable.Drawable;

/**
 * Created by Artem on 19.06.2018.
 */

public class Heroes {

    public String avatarHero;
    public String nameHero;
    public String descriptionHero;

    public Heroes(){

    }

    public Heroes(String avatarHero, String nameHero, String descriptionHero) {
        this.avatarHero = avatarHero;
        this.nameHero = nameHero;
        this.descriptionHero = descriptionHero;
    }
}
