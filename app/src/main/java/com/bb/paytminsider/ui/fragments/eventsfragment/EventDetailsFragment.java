package com.bb.paytminsider.ui.fragments.eventsfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.room.model.EventUser;
import com.bb.paytminsider.ui.fragments.callbacks.EventsActivityFragmentsCallbak;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventDetailsFragmentViewModel;
import com.bumptech.glide.RequestManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class EventDetailsFragment extends DaggerFragment {

    private static final String TAG = "EventDetailsFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    private EventDetailsFragmentViewModel detailsFragmentViewModel;

    private EventsActivityFragmentsCallbak activityFragmentsCallbak;

    @BindView(R.id.imageEventDetails)
    ImageView imageEventDetails;

    @BindView(R.id.eventDetailName)
    TextView eventDetailName;

    @BindView(R.id.eventDetailStartTime)
    TextView eventDetailStartTime;

    @BindView(R.id.eventDetailVenueName)
    TextView eventDetailVenueName;

    @BindView(R.id.popularityRatingBar)
    RatingBar popularityRatingBar;

    @BindView(R.id.subscribeButton)
    Button subscriptionButton;

    @BindView(R.id.backButtonEventDetails)
    ImageView backButtonEventDetails;

    private Event event;
    private Boolean isSubscribed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_details_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /*
    Binds all views with Event Object passed from Event Fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsFragmentViewModel = ViewModelProviders.of(this, providerFactory).get(EventDetailsFragmentViewModel.class);
        Bundle bundle = getArguments();
        event = (Event) bundle.getSerializable("Event");
        requestManager.load(event.getImage()).fitCenter().into(imageEventDetails);
        eventDetailName.setText(event.getName());
        eventDetailVenueName.setText(event.getVenueName());
        eventDetailStartTime.setText(event.getVeneueDate());
        popularityRatingBar.setRating((float) event.getPopularityScore() / 2);

        /*
        Initialize button on the bases of event is already subscribed or not
         */

        detailsFragmentViewModel.isUserSubscribedTOEvent(event).observe(getViewLifecycleOwner(), new Observer<List<EventUser>>() {
            @Override
            public void onChanged(List<EventUser> eventUsers) {
                if (eventUsers.size() > 0) {
                    subscriptionButton.setText(getString(R.string.unsubscribe_button_text));
                    subscriptionButton.setBackgroundResource(R.drawable.subscribed_round_button);
                    isSubscribed = true;
                } else {
                    subscriptionButton.setText(getString(R.string.subscribe_button_text));
                    subscriptionButton.setBackgroundResource(R.drawable.subscribe_round_button);
                    isSubscribed = false;
                }
            }
        });
    }

    /*
    Initialize callback to pass data to EventActivity
     */

    public void setActivityFragmentsCallbak(EventsActivityFragmentsCallbak activityFragmentsCallbak) {
        this.activityFragmentsCallbak = activityFragmentsCallbak;
    }

    /*
    If back button(the one drawn on View) is clicked than pass the event to Activity(activity pops the fragment)
     */

    @OnClick(R.id.backButtonEventDetails)
    public void backButtonClicked() {
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        backButtonEventDetails.startAnimation(fadeOut);
        activityFragmentsCallbak.backButtonPressedInEventDetailsFragment();
    }

    /*
    If subscribe/unsubscribe button is clicked handle it accordingly and also create a snackbar
     */

    @OnClick(R.id.subscribeButton)
    public void subscribeButtonClicked() {
        detailsFragmentViewModel.handleUserSubscriptionForEvent(event, (isSubscribed) ? EventDetailsFragmentViewModel.HANDLE_SUBSCRIPTION.DELETE_EVENT : EventDetailsFragmentViewModel.HANDLE_SUBSCRIPTION.SUBSCRIBE_EVENT);
        if (isSubscribed) {
            Snackbar.make(getView(), getResources().getString(R.string.event_unsubscription_success_snackbar), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView(), getResources().getString(R.string.event_subscription_sucess_snackbar), Snackbar.LENGTH_LONG).setAction(getString(R.string.event_subscription_undo_snackbar_button), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailsFragmentViewModel.handleUserSubscriptionForEvent(event, (isSubscribed) ? EventDetailsFragmentViewModel.HANDLE_SUBSCRIPTION.DELETE_EVENT : EventDetailsFragmentViewModel.HANDLE_SUBSCRIPTION.SUBSCRIBE_EVENT);
                }
            }).show();
        }
    }
}
