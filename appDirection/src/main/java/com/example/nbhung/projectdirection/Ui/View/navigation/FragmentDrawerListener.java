package com.example.nbhung.projectdirection.Ui.View.navigation;

import android.view.View;



public interface FragmentDrawerListener {
    void onDrawerItemSelected(View view, int position);

    void getLocation();

    void showAndHide();
}
