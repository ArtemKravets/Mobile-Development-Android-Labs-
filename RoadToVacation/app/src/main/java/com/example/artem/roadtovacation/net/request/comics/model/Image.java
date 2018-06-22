package com.example.artem.roadtovacation.net.request.comics.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artem on 20.06.2018.
 */

public class Image {

    @SerializedName("path")
    private String path;
    @SerializedName("extension")
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
