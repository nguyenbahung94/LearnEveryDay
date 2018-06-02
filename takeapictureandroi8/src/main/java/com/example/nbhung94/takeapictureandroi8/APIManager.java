package com.example.nbhung94.takeapictureandroi8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class APIManager {
    private static final String API_BASE_URL = "http://dayzerodev.tma.com.vn/";
    private static OkHttpClient client;
    private static Retrofit mRetrofit;
    private static Gson mGson = new GsonBuilder().create();

    public static OkHttpClient getHttpClientBuilder() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // Request customization: add request headers
                            Request request = chain.request().newBuilder()
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .build();

                            return chain.proceed(request);
                        }
                    }).build();
        }
        return client;
    }

    public static API getAPI() {
        if (mRetrofit == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(mGson));
            mRetrofit = builder.client(getHttpClientBuilder()).build();
        }
        return mRetrofit.create(API.class);
    }

    public static Call<String> login(String email, String password, Callback<String> listener) {
        Call<String> call = getAPI().login(email, password);
        call.enqueue(listener);
        return call;
    }

    public static Call<BaseResponse> updateImage(MultipartBody.Part mFile, Callback<BaseResponse> listener) {
        Call<BaseResponse> call = getAPI().updateAvatar(mFile);
        call.enqueue(listener);
        return call;
    }
}
