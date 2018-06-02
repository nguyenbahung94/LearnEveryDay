package com.tma.videotraining.ui.main.NavigationDrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.main.fragment.FragmentLogin;
import com.tma.videotraining.ui.main.fragment.ListVideoFragment;
import com.tma.videotraining.ui.model.NavDrawer.NavDrawerItem;
import com.tma.videotraining.ui.view.interDrawer.ClickListener;
import com.tma.videotraining.ui.view.interDrawer.FragmentDrawerListener;
import com.tma.videotraining.ui.view.interDrawer.ViewFagmentDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 7/7/2017.
 */

public class FragmentDrawer extends Fragment implements ViewFagmentDrawer {
    private static String[] titles = null;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private Button btnLogin, btnLogOut;
    private View layout;

    private static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (String title : titles) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(title);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        init();
        event();

        return layout;
    }

    private void init() {
        btnLogOut = (Button) layout.findViewById(R.id.logout);
        btnLogin = (Button) layout.findViewById(R.id.btn_login);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void event() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                FragmentLogin listVideoFragment = new FragmentLogin();
                swap(listVideoFragment);
                btnLogOut.setVisibility(View.INVISIBLE);
                mDrawerLayout.closeDrawer(containerView);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(containerView);
                FragmentLogin login = new FragmentLogin();
                swap(login);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public void setUp(int fagmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fagmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }

        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void openDrawer() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            //  mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }

    @Override
    public void loginSuccess() {
        btnLogin.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        btnLogOut.setVisibility(View.VISIBLE);
        ListVideoFragment videoFragment = new ListVideoFragment();
        swap(videoFragment);
    }

    @Override
    public void swap(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

}
