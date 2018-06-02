package com.tma.videotraining.ui.view;

import com.tma.videotraining.ui.model.NavDrawer.ListVideo;

/**
 * Created by nbhung on 7/10/2017.
 */

public interface ViewFragment {

    interface ViewListVideoFragment {
        void showDetail(ListVideo listVideo);

    }

    interface ViewVideoFragment {
        void playSuccess();

    }
}
