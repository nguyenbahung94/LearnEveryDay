package com.ekakashi.greenhouse.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Account", daoClass = AccountDao.class)

public class Account implements Parcelable {
//    @DatabaseField(generatedId = true)
//    private int id;

    @DatabaseField(columnName = "first_name")
    @SerializedName("firstName")
    public String firstName;

    @DatabaseField(columnName = "last_name")
    @SerializedName("lastName")
    public String lastName;

    @DatabaseField(columnName = "user_name")
    @SerializedName("username")
    public String userName;

    @DatabaseField(id = true, columnName = "email", unique = true)
    @SerializedName("email")
    public String email;

    @DatabaseField(columnName = "user_id")
    @SerializedName("id")
    public int id;

    @DatabaseField(columnName = "name")
    @SerializedName("name")
    public String name;

    @DatabaseField(columnName = "nick_name")
    @SerializedName("nickName")
    public String nickName;

    @DatabaseField(columnName = "image")
    @SerializedName("image")
    public String image;

    @DatabaseField(columnName = "user_web")
    @SerializedName("userWeb")
    public String userWeb;

    public Account() {
    }

    public Account(int id, String firstName, String lastName, String userName, String email, String name, String nickName, String image, String userWeb) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.image = image;
        this.userWeb = userWeb;
    }

    public Account(String firstName, String fullName, String lastName, String userName, String email, int id, String name, String nickName, String image, String userWeb) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.image = image;
        this.userWeb = userWeb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserWeb() {
        return userWeb;
    }

    public void setUserWeb(String userWeb) {
        this.userWeb = userWeb;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nickName);
        dest.writeString(this.image);
        dest.writeString(this.userWeb);
    }

    protected Account(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.nickName = in.readString();
        this.image = in.readString();
        this.userWeb = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
