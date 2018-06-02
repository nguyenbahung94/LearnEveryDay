package com.ekakashi.greenhouse.features.weather.period;

/**
 * Created by nquochuy on 3/13/2018.
 */

public class Period {
    private String title;
    private String content;
    private boolean isSelected;

    public Period(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
