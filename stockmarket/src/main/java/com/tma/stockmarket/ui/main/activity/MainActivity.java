package com.tma.stockmarket.ui.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.main.fragment.ExchangeRateFragment;
import com.tma.stockmarket.ui.main.fragment.LoginFragment;
import com.tma.stockmarket.ui.main.fragment.UserGetExchangeRateFragment;
import com.tma.stockmarket.ui.model.User;
import com.tma.stockmarket.ui.navigation.FragmentDrawerListener;
import com.tma.stockmarket.ui.navigation.navigationDrawer.FragmentDrawer;
import com.tma.stockmarket.ui.view.activity.MainActivityView;

public class MainActivity extends AppCompatActivity implements FragmentDrawerListener, MainActivityView {
    private FragmentDrawer fragmentDrawer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(
                R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        changeFagment(2);
        fragmentDrawer.setDrawerListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            fragmentDrawer.openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }


    private void changeFagment(int position) {
        switch (position) {
            case 0:
                ExchangeRateFragment fragment = new ExchangeRateFragment();
                String title = getString(R.string.title_echangerate);
                swapFragment(fragment, title);
                break;
            case 1:
                UserGetExchangeRateFragment userGetExchangeRateFragment = new UserGetExchangeRateFragment();
                String titleUser = getString(R.string.userget);
                swapFragment(userGetExchangeRateFragment, titleUser);
                break;
            case 2:
                LoginFragment loginFragment = new LoginFragment();
                String titleLogin = getString(R.string.login);
                swapFragment(loginFragment, titleLogin);
                break;
            default:
                break;
        }
    }

    private void swapFragment(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        changeFagment(position);
    }

    @Override
    public void changeTittle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    public void hideAndShowNavi(User user) {
        fragmentDrawer.loginSuccess(user);
        changeFagment(0);
    }


    @Override
    public void showScreenLogin() {
        fragmentDrawer.logout();
    }
}
