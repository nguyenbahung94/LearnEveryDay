package com.hungnguyenba94gmail.learnskill.createdialogfragment;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

/**
 * Created by nbhung on 12/4/2017.
 */

public class BaseDialogFragment<T> extends DialogFragment {
    private T mActivityInstance;

    public final T getActivityInstance() {
        return mActivityInstance;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivityInstance = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityInstance = null;
    }
}
