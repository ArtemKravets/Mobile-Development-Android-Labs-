package com.example.artem.roadtovacation.net.request.comics.model;

import com.example.artem.roadtovacation.net.request.characters.model.Image;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Artem on 21.06.2018.
 */

public class Comic {

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private Image thumbnail;

    @SerializedName("prices")
    private List<ComicPrice> prices;

    @SerializedName("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<ComicPrice> getPrices(){return prices;}

    public void setPrices(List<ComicPrice> prices){this.prices = prices;}

    public String getDescription(){return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }

}
