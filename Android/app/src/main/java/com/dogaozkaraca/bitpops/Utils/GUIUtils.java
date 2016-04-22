package com.dogaozkaraca.bitpops.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;


public class GUIUtils {

    public static final int SCALE_FACTOR = 30;




    public static int getStatusBarHeight(Context c) {

        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void hideRevealEffect (final View v, int centerX, int centerY, int initialRadius) {

        v.setVisibility(View.VISIBLE);

        // create the animation (the final radius is zero)
        @SuppressLint("NewApi") Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, initialRadius, 0);

        anim.setDuration(350);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }
    public static void hideRevealEffectDoCenter (final View v, int centerX, int centerY, int initialRadius,Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        // create the animation (the final radius is zero)
        @SuppressLint("NewApi") Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, initialRadius, 0);

        anim.setDuration(300);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.INVISIBLE);
            }
        });
        anim.addListener(lis);

        anim.start();
    }

    public static void showRevealEffect(final View v, int centerX, int centerY, Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int height = v.getHeight();

        @SuppressLint("NewApi") Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, height);

        anim.setDuration(300);

        anim.addListener(lis);
        anim.start();
    }



    public static void hideViewByScale(View view) {

        ViewPropertyAnimator propertyAnimator = view.animate().setStartDelay(SCALE_FACTOR)
                .scaleX(0).scaleY(0);

        propertyAnimator.start();
    }

    public static void showViewByScale(View view) {

        ViewPropertyAnimator propertyAnimator = view.animate().setStartDelay(SCALE_FACTOR)
                .scaleX(1).scaleY(1);

        propertyAnimator.start();
    }


}