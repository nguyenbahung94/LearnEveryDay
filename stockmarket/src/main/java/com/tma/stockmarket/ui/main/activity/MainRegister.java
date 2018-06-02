package com.tma.stockmarket.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.main.fragment.RegisterInfoFragment;
import com.tma.stockmarket.ui.main.fragment.RegisterLincenceFragment;
import com.tma.stockmarket.ui.main.fragment.RegisterProfileFragment;
import com.tma.stockmarket.ui.view.activity.MainViewRegister;




public class MainRegister extends AppCompatActivity implements MainViewRegister {
    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainregister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        intent = getIntent();
        int chooseFragment = intent.getIntExtra(getString(R.string.choose_fragment), 0);
        changeFagment(chooseFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransitionExit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void changeFagment(int position) {
        Fragment fragment = null;
        String title = "";
        switch (position) {
            case 0:
                fragment = new RegisterProfileFragment();
                title = getString(R.string.profile);
                break;
            case 1:
                fragment = new RegisterInfoFragment();
                title = getString(R.string.infor);
                break;
            case 2:
                fragment = new RegisterLincenceFragment();
                title = getString(R.string.lincence);
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_register, fragment);
            fragmentTransaction.commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }

        }

    }

    @Override
    public void saveFragment(int result) {
        if (result == 200) {
            intent.putExtra(getString(R.string.result_fragment), 200);
        }
        if (result == 201) {
            intent.putExtra(getString(R.string.result_fragment), 201);
        }
        if (result == 202) {
            intent.putExtra(getString(R.string.result_fragment), 202);
        }
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransitionExit();
    }
}
