package com.tma.stockmarket.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.tma.stockmarket.di.module.AppModule;
import com.tma.stockmarket.di.module.NetModule;

import dagger.Component;
import retrofit2.Retrofit;


@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    Retrofit retrofit();

    SharedPreferences sharedPreferences();

    Context getContext();
}
