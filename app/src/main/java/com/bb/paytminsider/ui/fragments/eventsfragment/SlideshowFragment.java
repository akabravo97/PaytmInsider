package com.bb.paytminsider.ui.fragments.eventsfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bb.paytminsider.R;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.dagger.viewmodel.ViewModelProviderFactory;
import com.bb.paytminsider.ui.fragments.adapters.SlideshowPageAdapter;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.SlideshowFragmentViewModel;
import com.bb.paytminsider.utils.FadingPageTransformer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager;

public class SlideshowFragment extends DaggerFragment {

    private static final String TAG = "SlideshowFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    SlideshowFragmentViewModel slideshowFragmentViewModel;

    @Inject
    SlideshowPageAdapter slideshowPageAdapter;

    @BindView(R.id.slideShowViewPager)
    AutoScrollViewPager slideShowViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.slideshow_fragment, container, false);
        ButterKnife.bind(this, rootView);
        slideShowViewPager.setAdapter(slideshowPageAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        slideshowFragmentViewModel = ViewModelProviders.of(this, providerFactory).get(SlideshowFragmentViewModel.class);

        /*
        Initalize paging animation as well as start autoscroll of viewpagers
         */
        slideShowViewPager.setPageTransformer(false, new FadingPageTransformer());
        slideShowViewPager.startAutoScroll();
        slideshowFragmentViewModel.fetchPopularEventsFromCache().removeObservers(getViewLifecycleOwner());
        slideshowFragmentViewModel.fetchPopularEventsFromCache().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                slideshowPageAdapter.setEvents(events);
            }
        });
    }

}
