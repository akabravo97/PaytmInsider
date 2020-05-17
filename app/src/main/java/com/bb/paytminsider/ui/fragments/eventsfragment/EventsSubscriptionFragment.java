package com.bb.paytminsider.ui.fragments.eventsfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.ui.fragments.adapters.EventSubscriptionRecyclerAdapter;
import com.bb.paytminsider.ui.fragments.callbacks.EventsActivityFragmentsCallbak;
import com.bb.paytminsider.ui.fragments.callbacks.EventsSubscriptionRecyclerViewItemClickListener;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventSubscriptionDetailViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class EventsSubscriptionFragment extends DaggerFragment implements EventsSubscriptionRecyclerViewItemClickListener {

    private static final String TAG = "EventsSubscriptionFragm";

    private EventSubscriptionDetailViewModel subscriptionDetailViewModel;
    private EventsActivityFragmentsCallbak activityFragmentsCallbak;

    @Inject
    ViewModelProviderFactory providerFactory;

    @BindView(R.id.subscriptionRecyclerView)
    RecyclerView subscriptionRecyclerView;

    @Inject
    EventSubscriptionRecyclerAdapter subscriptionRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_subscription_details_fragment, container, false);
        ButterKnife.bind(this, rootView);
        /*
        Intialize event listener for unsubscibed button clicked
         */
        subscriptionRecyclerAdapter.setEventsSubscriptionRecyclerViewItemClickListener(this::onUnsubscribebuttonClickd);
        /*
        Item animator when any event is unsusbcribed
         */
        subscriptionRecyclerView.setItemAnimator(new SlideInUpAnimator());
        subscriptionRecyclerView.setAdapter(subscriptionRecyclerAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriptionDetailViewModel = ViewModelProviders.of(this, providerFactory).get(EventSubscriptionDetailViewModel.class);
        subscriptionDetailViewModel.getAllSubscribedEventsForUser().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                if (events.size() == 0) {
                    Snackbar.make(getView(), getString(R.string.no_subscription_snackbar), Snackbar.LENGTH_LONG).show();
                }
                subscriptionRecyclerAdapter.setEvents(events);
            }
        });
    }

    public void setActivityFragmentsCallbak(EventsActivityFragmentsCallbak activityFragmentsCallbak) {
        this.activityFragmentsCallbak = activityFragmentsCallbak;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onUnsubscribebuttonClickd(Event event) {
        subscriptionDetailViewModel.deleteFromEventUser(event);
    }
}
