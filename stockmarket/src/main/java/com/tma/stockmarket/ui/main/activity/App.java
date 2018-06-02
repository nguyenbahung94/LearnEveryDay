package com.tma.stockmarket.ui.main.activity;

import android.app.Application;

import com.tma.stockmarket.di.component.DaggerNetComponent;
import com.tma.stockmarket.di.component.NetComponent;
import com.tma.stockmarket.di.module.AppModule;
import com.tma.stockmarket.di.module.NetModule;


public class App extends Application {
  private static  NetComponent mNetcomponent;

  @Override public void onCreate() {
    super.onCreate();
    mNetcomponent = DaggerNetComponent.builder()
        .netModule(new NetModule())
        .appModule(new AppModule(this))
        .build();
  }

  public NetComponent getmNetcomponent() {
    return mNetcomponent;
  }
}
