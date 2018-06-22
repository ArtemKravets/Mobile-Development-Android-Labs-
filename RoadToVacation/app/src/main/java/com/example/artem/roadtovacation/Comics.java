package com.example.artem.roadtovacation;

/**
 * Created by Artem on 21.06.2018.
 */

public class Comics {

    public String avatarComic;
    public String titleComic;
    public String descriptionComic;
    public Float priceComic;

    public Comics(){

    }

    public Comics(String avatarComic, String titleComic, String descriptionComic, Float priceComic) {
        this.avatarComic = avatarComic;
        this.titleComic = titleComic;
        this.descriptionComic = descriptionComic;
        this.priceComic = priceComic;
    }
}
