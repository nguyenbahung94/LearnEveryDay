package com.tma.videotraining.ui.interactor;

import com.tma.videotraining.ui.view.MainView;
import com.tma.videotraining.ui.view.ViewFragmentLogin;

/**
 * Created by nbhung on 7/10/2017.
 */

public class FragmentLoginInteractor {

    public FragmentLoginInteractor() {
    }

    public void login(String name, String pass, MainView mainView, ViewFragmentLogin callback) {
        if (name != null && pass != null) {
            if (name.equals("123") && pass.equals("123")) {
                mainView.hideNavigationDrawer();
                callback.success();
            }
        } else {
            callback.failed();
        }
    }
}
