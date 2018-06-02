package com.tma.stockmarket.di.component;

import com.tma.stockmarket.di.component.Scope.CustomScope;
import com.tma.stockmarket.di.module.MainViewModule;
import com.tma.stockmarket.ui.main.fragment.ExchangeRateFragment;

import dagger.Component;


@CustomScope
@Component(dependencies = {NetComponent.class}, modules = {MainViewModule.class})
public interface ExchangeRateFragnentComponent {
    void inject(ExchangeRateFragment exchangeRateFragment);
}
