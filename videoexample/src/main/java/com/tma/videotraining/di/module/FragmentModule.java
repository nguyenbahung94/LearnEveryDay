package com.tma.videotraining.di.module;

import com.tma.videotraining.ui.interactor.FragmentLoginInteractor;
import com.tma.videotraining.ui.interactor.FragmentRegisterInteractor;
import com.tma.videotraining.ui.interactor.ListDownloadFragmentInteractor;
import com.tma.videotraining.ui.interactor.ListVideoFragmentInteractor;
import com.tma.videotraining.ui.interactor.VideoFragmentInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nbhung on 7/10/2017.
 */
@Module
public class FragmentModule {
    @Provides
    FragmentLoginInteractor proFragmentLoginInteractor() {
        return new FragmentLoginInteractor();
    }

    @Provides
    ListVideoFragmentInteractor proListVideoFragmentInteractor() {
        return new ListVideoFragmentInteractor();
    }

    @Provides
    VideoFragmentInteractor proVideoFragmentInteractor() {
        return new VideoFragmentInteractor();
    }

    @Provides
    ListDownloadFragmentInteractor proListDownloadFragmentInteractor() {
        return new ListDownloadFragmentInteractor();
    }

    @Provides
    FragmentRegisterInteractor proFragmentRegisterInteractor() {
        return new FragmentRegisterInteractor();
    }

}
