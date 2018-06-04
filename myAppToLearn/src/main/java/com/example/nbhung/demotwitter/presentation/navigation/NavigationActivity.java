package com.example.nbhung.demotwitter.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.presentation.profile.ProfileFragment;
import com.example.nbhung.demotwitter.presentation.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class NavigationActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        handleMenu();
        handleDrawer();

    }

    private void handleDrawer() {
    }

    private void handleMenu() {

    }



    private void swapFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
