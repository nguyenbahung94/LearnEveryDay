package com.example.nbhung.demotwitter.di.module;

import android.content.Context;
import android.util.Log;

import com.example.nbhung.demotwitter.App;
import com.example.nbhung.demotwitter.common.Utils;
import com.example.nbhung.demotwitter.datalayer.NetWorkService;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private final String TAG = NetworkModule.class.getSimpleName();
    private static final String CACHE_CONTROL = "twitter_cache";
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final String HTTP_CACHE = "billy";
    private Context context;
    private static final int EXPIRE_TIME_MINS = 2;
    private static final int OFFLINE_EXPIRE_TIME_DAY = 7;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Singleton
    private Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(NetWorkService.BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache());
        return builder.build();
    }

    @Singleton
    private Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), HTTP_CACHE), CACHE_SIZE);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Cache!");
        }
        return cache;
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(EXPIRE_TIME_MINS, TimeUnit.MINUTES)
                    .build();
            return response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();
            if (Utils.isConnectToNetwork()) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(OFFLINE_EXPIRE_TIME_DAY, TimeUnit.DAYS)
                        .build();
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public NetWorkService providesNetWorkService() {
        return getRetrofitInstance().create(NetWorkService.class);
    }

    @Provides
    @Singleton
    public TwitterAppDataRepository providesTwitterAppDataRepository() {
        return new TwitterAppDataRepository(providesNetWorkService());
    }
}
