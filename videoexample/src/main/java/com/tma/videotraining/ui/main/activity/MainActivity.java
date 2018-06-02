package com.tma.videotraining.ui.main.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.di.component.DaggerAppComponent;
import com.tma.videotraining.di.module.AppModule;
import com.tma.videotraining.di.module.DownloadManagerModule;
import com.tma.videotraining.di.module.ViewModule;
import com.tma.videotraining.ui.main.NavigationDrawer.FragmentDrawer;
import com.tma.videotraining.ui.main.fragment.FragmentLogin;
import com.tma.videotraining.ui.main.fragment.FragmentRegister;
import com.tma.videotraining.ui.main.fragment.ListDownloadFragment;
import com.tma.videotraining.ui.main.fragment.ListVideoFragment;
import com.tma.videotraining.ui.main.fragment.VideoFragment;
import com.tma.videotraining.ui.presenter.PlayVideoPresenterImp;
import com.tma.videotraining.ui.view.MainView;
import com.tma.videotraining.ui.view.interDrawer.FragmentDrawerListener;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView, FragmentDrawerListener {
    @Inject
    PlayVideoPresenterImp presenterImp;
    @Inject
    DownloadManager.Query dmQuery;
    @Inject
    DownloadManager.Request dmRequest;
    private Toolbar mToolbar;
    private FragmentDrawer fragmentDrawer;
    private String videourl = "http://techslides.com/demos/sample-videos/small.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        fragmentDrawer.setDrawerListener(this);
        changeFagment(4);

        DaggerAppComponent.builder().appModule(new AppModule(getApplication(), getApplicationContext()))
                .viewModule(new ViewModule(this))
                .downloadManagerModule(new DownloadManagerModule(videourl)).build().inject(this);

        if (shouldAskPermissions()) {
            askPermissions();
        }
    }

    private boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    private void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            int requestCode = 200;
            requestPermissions(permissions, requestCode);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "action setting", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == android.R.id.home) {
            fragmentDrawer.openDrawer();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        changeFagment(position);
    }

    private void changeFagment(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ListVideoFragment();
                title = getString(R.string.title_videos);
                break;
            case 1:
                fragment = new ListDownloadFragment();
                title = getString(R.string.title_download);
                break;
            case 2:
                fragment = new VideoFragment();
                title = getString(R.string.title_video);
                break;
            case 3:
                fragment = new FragmentLogin();
                title = "Login";
                break;
            case 4:
                fragment = new FragmentRegister();
                title = "Register";
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(title);
        }

    }

    @Override
    public void hideNavigationDrawer() {
        fragmentDrawer.loginSuccess();
    }
}
