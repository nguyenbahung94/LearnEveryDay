package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("refresh_token")
    public String refreshToken;

    @SerializedName("expires_in")
    public Integer expiresIn;

    @SerializedName("scope")
    public String scope;

    @SerializedName("userLogin")
    public EkUser userLogin;

    @SerializedName("jti")
    public String jti;
}
