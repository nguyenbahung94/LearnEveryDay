package com.tma.stockmarket.ui.model;

import java.util.Arrays;

/**
 * Created by nbhung on 8/2/2017.
 */

public class User {
    private String id;
    private String name;
    private String password;
    private int sex;
    private byte[] image;
    private String phone;

    public User(String id, String name, String password, int sex, byte[] image, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.image = image;
        this.phone = phone;
    }

    public User(String name, String password, byte[] image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", image=" + Arrays.toString(image) +
                ", phone='" + phone + '\'' +
                '}';
    }
}
