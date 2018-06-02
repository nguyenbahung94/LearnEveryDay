package com.tma.stockmarket.ui.interactor.ExchangeRate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.model.User;
import com.tma.stockmarket.ui.model.database.DBmanager;

import at.grabner.circleprogress.CircleProgressView;


public class RegisterFragmentInteractor {
    private final Property<ProgressBar, Integer> property = new Property<ProgressBar, Integer>(int.class, "progress") {
        @Override
        public Integer get(ProgressBar progressBar) {
            return progressBar.getProgress();
        }

        @Override
        public void set(ProgressBar object, Integer value) {
            object.setProgress(value);
        }
    };

    public RegisterFragmentInteractor() {
    }

    public void changItem(TextView tvbefore, TextView tvDecriptionbefo, ImageView imageViewbefore, ImageView imageViewafter, TextView tvafter, TextView tvDescriptionafter, ProgressBar mprogressbar, View viewRegister, CircleProgressView circleProgressView, Context context, int position) {
        tvbefore.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
        new AnimationUtils();
        tvbefore.startAnimation(AnimationUtils.loadAnimation(context, R.anim.textalpha));
        tvDecriptionbefo.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
        new AnimationUtils();
        tvDecriptionbefo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.textalpha));
        imageViewbefore.setBackgroundResource(R.drawable.circlepsuccess);
        animateProgressBar(imageViewafter, tvafter, tvDescriptionafter, mprogressbar, viewRegister, context);
        augmentProgress(circleProgressView, position);
    }

    private void animateProgressBar(final ImageView imageView, final TextView tvafter, final TextView tvDescriptionafter, ProgressBar mprogressbar, final View viewRegister, final Context context) {
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressbar, property, 100);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setStartDelay(1500);
        anim.setDuration(1000);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView.setBackgroundResource(R.drawable.circlepress);
                imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.ani_shake));
                tvafter.setTextColor(ContextCompat.getColor(context, R.color.textWhite));
                tvDescriptionafter.setTextColor(ContextCompat.getColor(context, R.color.textWhite));
                viewRegister.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void augmentProgress(CircleProgressView progressView, int position) {
        if (position == 1) {
            int current = (int) progressView.getCurrentValue();
            if (current < 33) {
                progressView.setValueAnimated(current, (current + 33), 3300);
            }
        }
        if (position == 2) {
            int current = (int) progressView.getCurrentValue();
            if (current < 66) {
                progressView.setValueAnimated(current, (current + 33), 3300);
            }
        }
        if (position == 3) {
            int current = (int) progressView.getCurrentValue();
            if (current < 99) {
                progressView.setValueAnimated(current, (current + 33), 3300);
            }
        }


    }

    public void addAccount(DBmanager mDBmanager, SharedPreferences mSharedPreferences, final Context context, TextView tvCompelteRegister, TextView tvDescriptionCompeltedRegister, ImageView imgCompleteRegister) {
        String idUser = mSharedPreferences.getString(context.getString(R.string.iduser), "");
        String passUser = mSharedPreferences.getString(context.getString(R.string.passuser), "");
        String fullname = mSharedPreferences.getString(context.getString(R.string.fullname), "");
        String imageUser = mSharedPreferences.getString(context.getString(R.string.imageuser), "");
        byte[] byteImage = Base64.decode(imageUser, Base64.DEFAULT);
        String bithDay = mSharedPreferences.getString(context.getString(R.string.birthday), "");
        int sex = mSharedPreferences.getInt(context.getString(R.string.sex), 0);
        String phone = mSharedPreferences.getString(context.getString(R.string.phone), "");
        final User userRegister = new User(idUser, fullname, passUser, sex, byteImage, phone);
        if (mDBmanager.addUser(new String[]{"id", "name", "password", "sex", "phone"}, new String[]{idUser, fullname, passUser, String.valueOf(sex), phone}, byteImage)) {
            Log.e("true", "true");
            tvCompelteRegister.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
            tvCompelteRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.textalpha));
            tvDescriptionCompeltedRegister.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
            tvDescriptionCompeltedRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.textalpha));
            imgCompleteRegister.setBackgroundResource(R.drawable.circlepsuccess);
            Toast.makeText(context, "congratulation register success!", Toast.LENGTH_SHORT).show();

        } else {
            Log.e("failed", "failed");
        }
    }

    public void setValueShared(SharedPreferences shared, Context context) {
        shared.edit().putString(context.getString(R.string.iduser), "").apply();
        shared.edit().putString(context.getString(R.string.passuser), "").apply();
        shared.edit().putString(context.getString(R.string.fullname), "").apply();
        shared.edit().putString(context.getString(R.string.imageuser), "").apply();
        shared.edit().putString(context.getString(R.string.birthday), "").apply();
        shared.edit().putInt(context.getString(R.string.sex), 0).apply();
        shared.edit().putString(context.getString(R.string.phone), "").apply();
    }
}
