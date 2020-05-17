package com.bb.paytminsider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.ui.viewmodels.SplashActivityViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {
    private static final String TAG = "SplashActivity";
    @Inject
    ViewModelProviderFactory providerFactory;

    private SplashActivityViewModel splashActivityViewModel;

    @BindView(R.id.hydImageView)
    ImageView hydImageView;

    @BindView(R.id.delhiImageView)
    ImageView delhiImageView;

    @BindView(R.id.mumbaiImageView)
    ImageView mumbaiImageView;

    @BindView(R.id.insiderTextView)
    TextView insiderTextView;

    SweetAlertDialog sweetAlertDialog;

    boolean shouldProceed;
    Animation fadeInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Make activity full screen and hide Action bar
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        splashActivityViewModel = ViewModelProviders.of(this, providerFactory).get(SplashActivityViewModel.class);
        ButterKnife.bind(this);

        /*
        Initializing repeating fade-in-out animation for INSIDER textView
         */

        fadeInOut = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
        insiderTextView.setAnimation(fadeInOut);

        /*
        If alert dialog was visible, dismiss it. Not doing this might lead to leaking of window on rotation of device.
         */

        if (sweetAlertDialog != null) sweetAlertDialog.cancel();

        /*
        Requests Event Repository via ViewModel to fetch latest events from network.
        In case of failure,
        1. If cached data is available : Prompts user to ask for loading old data.
        2. If cached data is unavailable : Shows error to user
         */

        splashActivityViewModel.fetchLatestEvents().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.startsWith("ERROR")) {
                    String errorBody = s.split(":")[1];
                    splashActivityViewModel.isEventsCached().observe(SplashActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Log.d(TAG, "onChanged:boolean " + aBoolean);
                            showSweetDialog("Error", errorBody, SweetAlertDialog.ERROR_TYPE, aBoolean);
                        }
                    });
                } else {
                    fadeInOut.cancel();
                    splashActivityViewModel.insertEvents(s);
                    Intent eventsActivityIntent = new Intent(SplashActivity.this, EventActivity.class);
                    eventsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(eventsActivityIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
    Initializer for showing alert dialog
    shouldProceed is true : if cached items are available
     */

    private void showSweetDialog(String title, String message, int type, boolean shouldProceed) {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) return;
        message = message + (shouldProceed ? getResources().getString(R.string.cached_proceed_warning) : "");
        sweetAlertDialog = new SweetAlertDialog(SplashActivity.this, type)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("Close")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                });

        if (shouldProceed) {
            sweetAlertDialog.setCancelText("Proceed")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            fadeInOut.cancel();
                            Intent eventsActivityIntent = new Intent(SplashActivity.this, EventActivity.class);
                            eventsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(eventsActivityIntent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
        }
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }


}
