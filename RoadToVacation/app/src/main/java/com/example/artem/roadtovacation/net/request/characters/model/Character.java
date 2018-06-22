package com.example.artem.roadtovacation.net.request.characters.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Artem on 20.06.2018.
 */

public class Character implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private Image thumbnail;
    @SerializedName("description")
    private String description;
    @SerializedName("comics")
    private ComicList comics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ComicList getComics() {
        return comics;
    }

    public void setComics(ComicList comics) {
        this.comics = comics;
    }

    public String getDescription(){return description ;}

    public void setDescription(String stories) {
        this.description  = description ;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
