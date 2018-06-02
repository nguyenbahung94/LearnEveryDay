package com.ekakashi.greenhouse.features.tutorial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.features.login_screen.LoginActivity;

/**
 * Created by vvhoan on 4/2/2018.
 */

public class TutorialActivity extends BaseActivity {
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout llDots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        changeColorForStatusBar();
        layouts = new int[]{R.layout.page_tutorial_1, R.layout.page_tutorial_2, R.layout.page_tutorial_3};
        MyViewPager viewPager = findViewById(R.id.viewpager_tutorial);
        llDots = findViewById(R.id.llDots);
        viewPager.setAdapter(new MyPagerAdapter(this, layouts));
        addDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //you shouldn't do anything at here
            }

            @Override
            public void onPageSelected(int position) {
                changeColorForDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //you shouldn't do anything at here
            }
        });

        //swipe left in last screen to move to login screen
        viewPager.setOnSwipeOutListener(new MyViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtEnd() {
                Prefs.getInstance(TutorialActivity.this).setFirstTimeLaunchToFalse();
                startActivity(new Intent(TutorialActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    private void changeColorForStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#7cad42"));
        }

    }

    private void addDots(int currentPage) {
        dots = new TextView[layouts.length];
        llDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(65);
            dots[i].setTextColor(Color.parseColor("#9CBC77"));
            if (i == currentPage) {
                dots[i].setTextColor(Color.WHITE);
            }
            llDots.addView(dots[i]);
        }
    }

    private void changeColorForDots(int currentPage) {
        for (int i = 0; i < dots.length; i++) {
            if (i == currentPage) {
                dots[i].setTextColor(Color.WHITE);
            } else {
                dots[i].setTextColor(Color.parseColor("#9CBC77"));
            }
        }
    }

    //static inner class
    public static class MyPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private int[] layouts;

        MyPagerAdapter(Context context, int[] layouts) {
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layouts = layouts;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
