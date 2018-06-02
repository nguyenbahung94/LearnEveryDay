package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class LoginRequest {

    @SerializedName("PhoneOrEmail")
    @Expose
    private String PhoneOrEmail;
    @SerializedName("Password")
    @Expose
    private String Password;

    public LoginRequest(String phoneOrEmail, String password) {
        this.PhoneOrEmail = phoneOrEmail;
        this.Password = password;
    }

    public String getPhoneOrEmail() {
        return PhoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.PhoneOrEmail = phoneOrEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
