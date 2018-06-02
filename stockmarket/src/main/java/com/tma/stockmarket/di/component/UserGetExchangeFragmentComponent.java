package com.tma.stockmarket.di.component;

import com.tma.stockmarket.di.component.Scope.CustomScope;
import com.tma.stockmarket.di.module.UserGetExchangeRateModule;
import com.tma.stockmarket.ui.main.fragment.UserGetExchangeRateFragment;

import dagger.Component;

@CustomScope
@Component(dependencies = {NetComponent.class}, modules = {UserGetExchangeRateModule.class})
public interface UserGetExchangeFragmentComponent {
    void inject(UserGetExchangeRateFragment exchangeRateFragment);
}
