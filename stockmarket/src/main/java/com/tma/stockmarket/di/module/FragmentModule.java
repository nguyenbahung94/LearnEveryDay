package com.tma.stockmarket.di.module;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.tma.stockmarket.ui.interactor.ExchangeRate.EdtProfileAccountInteractor;
import com.tma.stockmarket.ui.interactor.ExchangeRate.LoginFragmentInteractor;
import com.tma.stockmarket.ui.interactor.ExchangeRate.RegisterFragmentInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    @Provides
    LoginFragmentInteractor proLoginFragmentInteractor() {
        return new LoginFragmentInteractor();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Provides
    RegisterFragmentInteractor proFragmentRegisterInteractor() {
        return new RegisterFragmentInteractor();
    }

    @Provides
    EdtProfileAccountInteractor profileAccountInteractor() {
        return new EdtProfileAccountInteractor();
    }
}
