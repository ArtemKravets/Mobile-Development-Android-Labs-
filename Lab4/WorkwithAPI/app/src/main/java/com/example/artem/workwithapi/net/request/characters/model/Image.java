package com.example.artem.workwithapi.net.request.characters.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Artem on 19.06.2018.
 */

public class Image implements Serializable {
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
