package com.ekakashi.greenhouse.common.stepcreatefield;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;

/**
 * Created by vvhoan on 3/26/2018.
 */

public class ThreeStepCreateField {
    private RelativeLayout mRoot;
    private ProgressBar mProgressBar;
    private TextView tvrStep2;
    private Context context;
    private AppCompatImageView imvStep2;
    private TextView tvRecipeStep2;
    private TextView tvStep3;
    private AppCompatImageView imvStep3;
    private TextView tvPlaceStep3;
    private TextView tvStep1;
    private ImageView imvStep1;

    public ThreeStepCreateField(RelativeLayout mRoot, Context context) {
        this.mRoot = mRoot;
        this.context = context;
        mProgressBar = mRoot.findViewById(R.id.progressBar1);
        tvrStep2 = mRoot.findViewById(R.id.tvrStep2);
        imvStep2 = mRoot.findViewById(R.id.imvStep2);
        tvRecipeStep2 = mRoot.findViewById(R.id.tvRecipeStep2);
        tvStep3 = mRoot.findViewById(R.id.tvStep3);
        imvStep3 = mRoot.findViewById(R.id.imvStep3);
        tvPlaceStep3 = mRoot.findViewById(R.id.tvPlaceStep3);
        tvStep1 = mRoot.findViewById(R.id.tvrStep1);
        imvStep1 = mRoot.findViewById(R.id.imvStep1);

    }

    public void stepOne() {
        mProgressBar.setProgress(50);
        tvStep1.setVisibility(View.GONE);
        imvStep1.setVisibility(View.VISIBLE);
        tvrStep2.setText("2");
        tvrStep2.setVisibility(View.VISIBLE);
        tvrStep2.setTextColor(ContextCompat.getColor(context, R.color.bg_green_btn));
        tvrStep2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable_color));
        imvStep2.setVisibility(View.GONE);
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep3.setVisibility(View.VISIBLE);
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep3.setVisibility(View.GONE);
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
    }

    public void stepTwo() {
        mProgressBar.setProgress(100);
        tvStep1.setVisibility(View.GONE);
        imvStep1.setVisibility(View.VISIBLE);
        imvStep2.setVisibility(View.VISIBLE);
        tvrStep2.setVisibility(View.GONE);
        imvStep2.setVisibility(View.VISIBLE);
        imvStep2.setImageResource(R.drawable.ic_svg_check_35_white);
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep3.setText("3");
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable_color));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.bg_green_btn));
        imvStep3.setVisibility(View.GONE);
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
    }

    public void hideAll() {
        mRoot.setVisibility(View.GONE);
    }
}
