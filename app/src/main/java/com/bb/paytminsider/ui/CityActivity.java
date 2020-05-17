package com.bb.paytminsider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.ui.viewmodels.CityActivityViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class CityActivity extends DaggerAppCompatActivity {
    private static final String TAG = "CityActivity";
    @Inject
    ViewModelProviderFactory providerFactory;

    @BindView(R.id.delhiImageView)
    ImageView delhiImageView;

    @BindView(R.id.mumbaiImageView)
    ImageView mumbaiImageView;

    @BindView(R.id.hydImageView)
    ImageView hydImageView;

    private CityActivityViewModel cityActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Requesting window feature to hide ActionBar and make activity fullscreen
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cityActivityViewModel = ViewModelProviders.of(this, providerFactory).get(CityActivityViewModel.class);

        /*
        if city is already(preference is already set) selected start splash activity
         */

        if (cityActivityViewModel.isCitySelected()) {
            Intent splashIntent = new Intent(CityActivity.this, SplashActivity.class);
            splashIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(splashIntent);
            finish();
        }

        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
    }

    /*
    When city is selected this method is triggered to handle the selection and start Splash Activity
     */

    @OnClick({R.id.mumbaiImageView, R.id.hydImageView, R.id.delhiImageView})
    public void onCitySelected(ImageView imageView) {
        Intent splashIntent = new Intent(CityActivity.this, SplashActivity.class);
        Pair<View, String> hydPair = new Pair<>(hydImageView, getResources().getString(R.string.hyd_city_transition));
        Pair<View, String> mumPair = new Pair<>(mumbaiImageView, getResources().getString(R.string.mum_city_transition));
        Pair<View, String> delPair = new Pair<>(delhiImageView, getResources().getString(R.string.del_city_transition));
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(CityActivity.this, hydPair, delPair, mumPair);

        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                startActivity(splashIntent, optionsCompat.toBundle());

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(fadeOut);

        switch (imageView.getId()) {
            case R.id.delhiImageView:
                cityActivityViewModel.onCitySelected(getResources().getString(R.string.delhi_city_name));
                break;
            case R.id.mumbaiImageView:
                cityActivityViewModel.onCitySelected(getResources().getString(R.string.mumbai_city_name));
                break;
            case R.id.hydImageView:
                cityActivityViewModel.onCitySelected(getResources().getString(R.string.hyd_city_name));
                break;
        }
    }
}
