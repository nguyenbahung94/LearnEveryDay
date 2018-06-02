package com.tma.videotraining.di.component;

import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.main.fragment.FragmentLogin;
import com.tma.videotraining.ui.main.fragment.FragmentRegister;
import com.tma.videotraining.ui.main.fragment.ListDownloadFragment;
import com.tma.videotraining.ui.main.fragment.ListVideoFragment;
import com.tma.videotraining.ui.main.fragment.VideoFragment;

import dagger.Component;

/**
 * Created by nbhung on 7/10/2017.
 */
@Component(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(ListVideoFragment listVideoFragment);

    void inject(VideoFragment videoFragment);

    void inject(FragmentLogin fragmentLogin);

    void inject(ListDownloadFragment listDownloadFragment);
    void inject(FragmentRegister fragmentRegister);
}

