package com.example.nbhung94.takeapictureandroi8;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by paduy on 11/8/2017.
 */

public interface API {
    /**
     * Login api
     */
    @FormUrlEncoded
    @POST("api/login")
    Call<String> login(@Field("email") String email, @Field("password") String password);

    /**
     * update image
     * */
    @Multipart
    @POST("api/fileUpload")
    Call<BaseResponse> updateAvatar(@Part MultipartBody.Part file);
}
