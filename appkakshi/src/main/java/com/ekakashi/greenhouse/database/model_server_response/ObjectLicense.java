package com.ekakashi.greenhouse.database.model_server_response;

/**
 * Created by ptloc on 3/26/2018.
 */

public class ObjectLicense {
    private int id;
    private String title;
    private String content;

    public ObjectLicense(String title, String content) {
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
}
