package com.tma.videotraining.ui.interactor;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Property;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tma.videotraining.R;

/**
 * Created by nbhung on 7/14/2017.
 */

public class FragmentRegisterInteractor {
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

    public FragmentRegisterInteractor() {
    }

    public void changItem(TextView tvbefore, TextView tvDecriptionbefo, ImageView imageViewbefore, ImageView imageViewafter, TextView tvafter, TextView tvDescriptionafter, ProgressBar mprogressbar, View viewRegister, Context context, Animation animation) {
        tvbefore.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
        tvbefore.startAnimation(animation);
        tvDecriptionbefo.setTextColor(ContextCompat.getColor(context, R.color.textYellow));
        tvDecriptionbefo.startAnimation(animation);
        imageViewbefore.setBackgroundResource(R.drawable.circlepsuccess);
        animateProgressBar(imageViewafter, tvafter, tvDescriptionafter, mprogressbar, viewRegister, context);
    }

    private void animateProgressBar(final ImageView imageView, final TextView tvafter, final TextView tvDescriptionafter, ProgressBar mprogressbar, final View viewRegister, final Context context) {
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressbar, property, 100);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setStartDelay(1000);
        anim.setDuration(1000);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView.setBackgroundResource(R.drawable.circlepress);
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
}
