package com.example.artem.roadtovacation.net.request.characters.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Artem on 20.06.2018.
 */

public class CharacterDataContainer implements Serializable {
    @SerializedName("results")
    private ArrayList<Character> results;


    public ArrayList<Character> getResultsCharacter() {
        return results;
    }

    public void setResults(ArrayList<Character> results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "CharacterDataContainer{" +
                "results=" + results +
                '}';
    }
}
