package com.ekakashi.greenhouse.database.dao;

/**
 * Created by nbhung on 12/25/2017.
 */

public class FieldObject {
    private String name;
    private String id;

    public FieldObject() {
    }

    public FieldObject(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
