package com.bb.paytminsider.ui;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.ui.fragments.callbacks.EventsActivityFragmentsCallbak;
import com.bb.paytminsider.ui.fragments.eventsfragment.EventDetailsFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.EventsFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.EventsSubscriptionFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.SlideshowFragment;
import com.bb.paytminsider.ui.viewmodels.EventActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class EventActivity extends DaggerAppCompatActivity implements EventsActivityFragmentsCallbak, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "EventActivity";
    private EventActivityViewModel eventActivityViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentManager fragmentManager;
    private SlideshowFragment slideshowFragment;
    private EventsFragment eventsFragment;
    private EventDetailsFragment eventDetailsFragment;
    private EventsSubscriptionFragment subscriptionFragment;

    @BindView(R.id.drawer_layout)
    DrawerLayout topDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Make activity full screen
        */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_event);

        ButterKnife.bind(this);
        /*
        Intialize ActionBar and Toolbar
         */
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, topDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        topDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        eventActivityViewModel = ViewModelProviders.of(this, providerFactory).get(EventActivityViewModel.class);

        /*
        Null check is to prevent recreation of Fragments in case of device rotation.
         */

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            eventsFragment = new EventsFragment();
            eventsFragment.setEventsActivityFragmentsCallbak(this);
            fragmentTransaction.addToBackStack(eventsFragment.getClass().getName());
            //setting up transition
            eventsFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.event_details_transition));
            eventsFragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
            fragmentTransaction.replace(R.id.event_list_frame_layout, eventsFragment);

            /*
            Create slideshow fragment only in case of portrait mode
             */

            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                slideshowFragment = new SlideshowFragment();
                fragmentTransaction.replace(R.id.slideshow_frame_layout, slideshowFragment);
            } else {
                triggerSlideShowFragment(View.GONE);
            }
            fragmentTransaction.commit();
        }
    }

    /*
    Handle cases of on device orientation change
    Hides slideshow fragment in case of landscape mode
    else makes it visible
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            triggerSlideShowFragment(View.GONE);
        } else {
            if (slideshowFragment == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                slideshowFragment = new SlideshowFragment();
                fragmentTransaction.replace(R.id.slideshow_frame_layout, slideshowFragment);
                fragmentTransaction.commit();
            }
            triggerSlideShowFragment(View.VISIBLE);
        }
        super.onConfigurationChanged(newConfig);
    }

    /*
    Create Event details fragment which is triggered when an item from Recycler view of Event List is selected
     */

    @Override
    public void triggerEventDetailsFragment(Event event) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!EventDetailsFragment.class.getName().equals(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName())) {

            eventDetailsFragment = new EventDetailsFragment();
            eventDetailsFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));
            Bundle bundle = new Bundle();
            bundle.putSerializable("Event", event);
            eventDetailsFragment.setArguments(bundle);
            eventDetailsFragment.setActivityFragmentsCallbak(this);
            fragmentTransaction.add(R.id.event_list_frame_layout, eventDetailsFragment, eventDetailsFragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(eventDetailsFragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }

    /*
    Creates Event subscribption fragment when user selectes My Subscription from side navigation bar
     */
    public void triggerEventSubscriptionFragment() {
        //triggerSlideShowFragment(View.GONE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /*
        This condition checks if Subscription Fragment was already on top, in that case it does't recreate the fragment.
         */

        if (!EventsSubscriptionFragment.class.getName().equals(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName())) {
            subscriptionFragment = new EventsSubscriptionFragment();
            subscriptionFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_left));
            subscriptionFragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right));
            subscriptionFragment.setActivityFragmentsCallbak(this);
            fragmentTransaction.addToBackStack(subscriptionFragment.getClass().getName());
            fragmentTransaction.replace(R.id.event_list_frame_layout, subscriptionFragment);
            fragmentTransaction.commit();
        }
    }

    /*
    Callback coming from onClickListener of back button in EventDetailsFragment
     */

    @Override
    public void backButtonPressedInEventDetailsFragment() {
        getSupportFragmentManager().popBackStack();
    }

    /*
    On back pressed of user pops all the fragment untill it reaches the 1st fragment
     */

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
    /*
    Triggers to change the visibility of Slide Show fragment
     */

    @Override
    public void triggerSlideShowFragment(int visibility) {
        ((View) findViewById(R.id.slideshow_frame_layout)).setVisibility(visibility);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_my_subscription:
                triggerEventSubscriptionFragment();
                break;

            case R.id.nav_item_live_events:
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                }
                break;
        }
        topDrawerLayout.closeDrawer(Gravity.START);
        return true;
    }
}