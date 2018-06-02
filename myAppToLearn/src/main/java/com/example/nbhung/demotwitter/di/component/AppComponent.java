package com.example.nbhung.demotwitter.di.component;

import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;
import com.example.nbhung.demotwitter.di.module.NetworkModule;
import com.example.nbhung.demotwitter.presentation.login.LoginActivity;
import com.example.nbhung.demotwitter.presentation.register.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);

    void inject(TwitterAppDataRepository twitterAppDataRepository);

    void inject(RegisterActivity registerActivity);
}
