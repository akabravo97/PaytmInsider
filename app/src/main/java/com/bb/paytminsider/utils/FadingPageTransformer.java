package com.bb.paytminsider.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/*
Sets alpha value of viewpages depending on there location in the view
Oringinal location ranges from -1 to 1.
alpha = 1 - abs(position)
 */
public class FadingPageTransformer implements ViewPager.PageTransformer {


    public FadingPageTransformer() {
    }

    @Override
    public void transformPage(@NonNull View page, float position) {

        if (position <= -1 || position >= 1) {
            page.setAlpha(0);

        } else if (position == 0) {

            page.setAlpha(1);

        } else {
            page.setAlpha(1- Math.abs(position));
        }
    }
}
