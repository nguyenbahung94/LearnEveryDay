package com.ekakashi.greenhouse.database.model_server_response;

/**
 * Created by nbhung on 1/31/2018.
 */

public class ObjectRecord {
    private String image;
    private String name;
    private String dateTime;
    private String status;
    private int degree;

    public ObjectRecord(String image, String name, String dateTime, String status, int degree) {
        this.image = image;
        this.name = name;
        this.dateTime = dateTime;
        this.status = status;
        this.degree = degree;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
