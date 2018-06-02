package com.tma.stockmarket.ui.navigation.navigationDrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.main.fragment.EdtProfileAccountFragment;
import com.tma.stockmarket.ui.main.fragment.LoginFragment;
import com.tma.stockmarket.ui.model.User;
import com.tma.stockmarket.ui.navigation.ClickListener;
import com.tma.stockmarket.ui.navigation.FragmentDrawerListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDrawer extends Fragment {
    private static String[] titles = null;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private View layout;
    private Button btnLogout, btnEdit;
    private TextView tvFullNameUser;
    private CircleImageView imgViewUser;
    private LinearLayout layoutContentLogout, btnLogin;

    private static List<NavDrawerItem> getData() {

        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(title);
            if (i == 0) {
                navItem.setItem(R.drawable.ic_if_currency_dollar_381602);
            }
            if (i == 1) {
                navItem.setItem(R.drawable.ic_if_dollar_exchange_532645);
            }
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        init();
        event();

        return layout;
    }

    private void init() {

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.INVISIBLE);
        btnLogin = (LinearLayout) layout.findViewById(R.id.btn_login);

        tvFullNameUser = (TextView) layout.findViewById(R.id.tvFullNameUser);
        imgViewUser = (CircleImageView) layout.findViewById(R.id.avatarUserIamge);
        layoutContentLogout = (LinearLayout) layout.findViewById(R.id.layoutcontentlogout);
        btnLogout = (Button) layoutContentLogout.findViewById(R.id.btn_logout);
        btnEdit = (Button) layout.findViewById(R.id.btn_editaccount);

    }

    private void event() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap(new EdtProfileAccountFragment());
                drawerListener.changeTittle("Edit Profile");
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.changeTittle("Log in");
                btnLogin.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                layoutContentLogout.setVisibility(View.GONE);
                tvFullNameUser.setVisibility(View.INVISIBLE);
                imgViewUser.setVisibility(View.INVISIBLE);
                mDrawerLayout.closeDrawer(containerView);
                swap(new LoginFragment());
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.changeTittle("Log in");
                mDrawerLayout.closeDrawer(containerView);
                swap(new LoginFragment());
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

    private void swap(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

    public void logout() {
        btnLogin.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        layoutContentLogout.setVisibility(View.GONE);
        tvFullNameUser.setVisibility(View.INVISIBLE);
        imgViewUser.setVisibility(View.INVISIBLE);
        mDrawerLayout.closeDrawer(containerView);
        swap(new LoginFragment());
    }

    public void loginSuccess(User user) {
        tvFullNameUser.setVisibility(View.VISIBLE);
        imgViewUser.setVisibility(View.VISIBLE);
        tvFullNameUser.setText(user.getName());
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        imgViewUser.setImageBitmap(imgBitmap);
        btnLogin.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        layoutContentLogout.setVisibility(View.VISIBLE);
    }
}
