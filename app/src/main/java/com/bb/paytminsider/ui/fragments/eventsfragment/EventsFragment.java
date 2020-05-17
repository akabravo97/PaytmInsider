package com.bb.paytminsider.ui.fragments.eventsfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.ui.fragments.adapters.EventsCardAdapter;
import com.bb.paytminsider.ui.fragments.callbacks.EventsActivityFragmentsCallbak;
import com.bb.paytminsider.ui.fragments.callbacks.EventsRecyclerViewItemClickListener;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventsFragmentViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class EventsFragment extends DaggerFragment {

    private static final String TAG = "EventsFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    EventsCardAdapter eventsCardAdapter;

    EventsFragmentViewModel eventsFragmentViewModel;

    private EventsActivityFragmentsCallbak eventsActivityFragmentsCallbak;

    @BindView(R.id.eventCardsRecyclerView)
    RecyclerView eventCardsRecyclerView;

    @BindView(R.id.eventType)
    Spinner eventTypeSpinner;
    @BindView(R.id.sortMethod)
    Spinner sortMethodSpinner;
    @BindView(R.id.sortType)
    Spinner sortTypeSpinner;
    /*
    Pre-Intialized values for Spinner
     */
    String eventType = "Popular";
    String methodType = "Popularity";
    String sortType = "Descending";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_fragment, container, false);
        ButterKnife.bind(this, rootView);

        eventsCardAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.ALLOW);
        eventCardsRecyclerView.setAdapter(eventsCardAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eventsFragmentViewModel = ViewModelProviders.of(this, providerFactory).get(EventsFragmentViewModel.class);
        /*
        Remove any observer that was observing previously to prevent inconsistencies.
         */
        eventsFragmentViewModel.getLatestEventsFromLocalDatabase().removeObservers(getViewLifecycleOwner());

        eventsFragmentViewModel.getLatestEventsFromLocalDatabase().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventsCardAdapter.setEvents(events);
            }
        });

        /*
        Update recycler view with the new parameters coming from spinner
         */

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventType = eventTypeSpinner.getItemAtPosition(position).toString();
                eventsFragmentViewModel.updateSpinnerEventType(eventType);
                eventsFragmentViewModel.updateEventCardsOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sortTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = sortTypeSpinner.getItemAtPosition(position).toString();
                eventsFragmentViewModel.updateSpinnerSortType(sortType);
                eventsFragmentViewModel.updateEventCardsOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sortMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                methodType = sortMethodSpinner.getItemAtPosition(position).toString();
                eventsFragmentViewModel.updateSpinnerMethodType(methodType);
                eventsFragmentViewModel.updateEventCardsOrder();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        If data is changed scroll to top
         */
        eventsCardAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                eventCardsRecyclerView.scrollToPosition(0);
            }
        });
        /*
        Trigger event details fragment on click of event card
         */
        eventsCardAdapter.setViewItemClickListener(new EventsRecyclerViewItemClickListener() {
            @Override
            public void onEventCardSelected(Event event) {
                eventsActivityFragmentsCallbak.triggerEventDetailsFragment(event);
            }
        });

        /*
        Initalize recycler view
         */

        eventsFragmentViewModel.updateEventCardsOrder();

    }

    public void setEventsActivityFragmentsCallbak(EventsActivityFragmentsCallbak eventsActivityFragmentsCallbak) {
        this.eventsActivityFragmentsCallbak = eventsActivityFragmentsCallbak;
    }


    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }
}
