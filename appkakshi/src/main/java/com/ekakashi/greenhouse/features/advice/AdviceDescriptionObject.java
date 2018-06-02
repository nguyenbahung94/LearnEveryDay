package com.ekakashi.greenhouse.features.advice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 4/17/2018.
 */

public class AdviceDescriptionObject {

    @SerializedName("title")
    public String title;
    @SerializedName("url")
    public String url;
    @SerializedName("images")
    public String images;
    @SerializedName("content")
    public String content;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImages() {
        return images;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
