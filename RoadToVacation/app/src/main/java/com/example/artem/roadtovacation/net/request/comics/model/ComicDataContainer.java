package com.example.artem.roadtovacation.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Artem on 21.06.2018.
 */

public class ComicDataContainer implements Serializable {

    @SerializedName("results")
    private ArrayList<Comic> results ;


    public ArrayList<Comic> getResultsComic() {
        return results;
    }


    public void setComic(ArrayList<Comic> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ComicDataContainer{" +
                "comics=" + results +
                '}';
    }
}
