package com.tma.stockmarket.ui.navigation;

import android.view.View;

public interface FragmentDrawerListener {
    void onDrawerItemSelected(View view, int position);

    void changeTittle(String title);
}
