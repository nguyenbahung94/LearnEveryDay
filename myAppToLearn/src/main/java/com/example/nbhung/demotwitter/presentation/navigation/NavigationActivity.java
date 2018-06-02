package com.example.nbhung.demotwitter.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.presentation.profile.ProfileFragment;
import com.example.nbhung.demotwitter.presentation.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class NavigationActivity extends BaseActivity implements DuoMenuView.OnMenuClickListener {
    @BindView(R.id.drawer)
    private DuoDrawerLayout mDuoDrawerLayout;
    private DuoMenuView mDuoMenuView;
    @BindView(R.id.toolbar)
    private Toolbar mToolbar;
    private MenuAdapter mMenuAdapter;
    private ArrayList<String> mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
        setSupportActionBar(mToolbar);
        handleMenu();
        handleDrawer();
    }

    private void handleDrawer() {
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, mDuoDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDuoDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void handleMenu() {
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        mMenuAdapter = new MenuAdapter(mTitles);
        mDuoMenuView.setOnMenuClickListener(this);
        mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void hideLoading() {
        hideDialogLoading();
    }

    @Override
    public void showLoading() {
        showDialogLoading();
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {

    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        //setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            default:
                swapFragment(new ProfileFragment());
                break;
        }

        // Close the drawer
        mDuoDrawerLayout.closeDrawer();
    }

    private void swapFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
