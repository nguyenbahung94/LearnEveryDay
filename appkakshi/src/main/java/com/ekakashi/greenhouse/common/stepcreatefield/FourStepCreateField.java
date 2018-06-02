package com.ekakashi.greenhouse.common.stepcreatefield;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;


public class FourStepCreateField {
    private RelativeLayout mRoot;
    private ProgressBar mProgressBar;
    private TextView tvrStep2;
    private Context context;
    private AppCompatImageView imvStep2;
    private TextView tvRecipeStep2;
    private TextView tvStep3;
    private AppCompatImageView imvStep3;
    private TextView tvPlaceStep3;
    private TextView tvStep4;
    private AppCompatImageView imvStep4;
    private TextView tvConfirmStep4;

    public FourStepCreateField(RelativeLayout mRoot, Context context) {
        this.mRoot = mRoot;
        this.context = context;
        mProgressBar = mRoot.findViewById(R.id.progressBar1);
        tvrStep2 = mRoot.findViewById(R.id.tvrStep2);
        imvStep2 = mRoot.findViewById(R.id.imvStep2);
        tvRecipeStep2 = mRoot.findViewById(R.id.tvRecipeStep2);
        tvStep3 = mRoot.findViewById(R.id.tvStep3);
        imvStep3 = mRoot.findViewById(R.id.imvStep3);
        tvPlaceStep3 = mRoot.findViewById(R.id.tvPlaceStep3);
        tvStep4 = mRoot.findViewById(R.id.tvStep4);
        tvConfirmStep4 = mRoot.findViewById(R.id.tvConfirmStep4);
        imvStep4 = mRoot.findViewById(R.id.imvStep4);

    }

    public void stepOne() {
        mProgressBar.setProgress(20);
        tvrStep2.setText("2");
        tvrStep2.setVisibility(View.VISIBLE);
        tvrStep2.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        tvrStep2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        imvStep2.setVisibility(View.GONE);
        imvStep2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_svg_check_35_white));
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
        tvStep3.setVisibility(View.VISIBLE);
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep3.setVisibility(View.GONE);
        imvStep3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_svg_check_35_white));
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
        tvStep4.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_svg_check_35_white));
        imvStep4.setVisibility(View.GONE);
        tvConfirmStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
    }

    public void stepTwo() {
        mProgressBar.setProgress(40);
        tvrStep2.setVisibility(View.VISIBLE);
        tvrStep2.setTextColor(ContextCompat.getColor(context, R.color.bg_green_btn));
        tvrStep2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg));
        imvStep2.setVisibility(View.GONE);
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep3.setVisibility(View.VISIBLE);
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep3.setVisibility(View.GONE);
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
        tvStep4.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep4.setVisibility(View.GONE);
        tvConfirmStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
    }

    public void stepThree() {
        mProgressBar.setProgress(60);
        //     imvOne.setVisibility(View.VISIBLE);
        tvrStep2.setVisibility(View.GONE);
        imvStep2.setVisibility(View.VISIBLE);
        imvStep2.setImageResource(R.drawable.ic_svg_check_35_white);
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep3.setVisibility(View.VISIBLE);
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.bg_green_btn));
        imvStep3.setVisibility(View.GONE);
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep4.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep4.setVisibility(View.GONE);
        tvConfirmStep4.setTextColor(ContextCompat.getColor(context, R.color.tv_color_d2));
    }

    public void stepFour() {
        mProgressBar.setProgress(100);
        tvrStep2.setText("2");
        tvrStep2.setVisibility(View.GONE);
        tvrStep2.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        tvrStep2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        imvStep2.setVisibility(View.VISIBLE);
        imvStep2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_svg_check_35_white));
        tvRecipeStep2.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep3.setVisibility(View.GONE);
        tvStep3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg_disable));
        tvStep3.setTextColor(ContextCompat.getColor(context, R.color.tv_white));
        imvStep3.setVisibility(View.VISIBLE);
        imvStep3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_svg_check_35_white));
        tvPlaceStep3.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
        tvStep4.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bg));
        tvStep4.setTextColor(ContextCompat.getColor(context, R.color.bg_green_btn));
        imvStep4.setImageResource(R.drawable.ic_svg_check_35_white);
        imvStep4.setVisibility(View.GONE);
        tvConfirmStep4.setTextColor(ContextCompat.getColor(context, R.color.tvColor44));
    }

    public void hideAll() {
        mRoot.setVisibility(View.GONE);
    }
}

