package com.tma.stockmarket.di.component;

import com.tma.stockmarket.di.component.Scope.CustomScope;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.main.fragment.EdtProfileAccountFragment;
import com.tma.stockmarket.ui.main.fragment.LoginFragment;
import com.tma.stockmarket.ui.main.activity.RegisterActivity;
import com.tma.stockmarket.ui.main.fragment.RegisterInfoFragment;
import com.tma.stockmarket.ui.main.fragment.RegisterLincenceFragment;
import com.tma.stockmarket.ui.main.fragment.RegisterProfileFragment;

import dagger.Component;


@CustomScope
@Component(dependencies = NetComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(LoginFragment loginFragment);

    void inject(RegisterActivity registerActivity);

    void inject(RegisterInfoFragment registerInfoFragment);

    void inject(RegisterProfileFragment registerProfileFragment);

    void inject(RegisterLincenceFragment registerLincenceFragment);

    void inject(EdtProfileAccountFragment edtProfileAccountFragment);
}
