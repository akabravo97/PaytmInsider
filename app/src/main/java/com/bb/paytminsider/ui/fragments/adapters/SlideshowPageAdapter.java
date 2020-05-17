package com.bb.paytminsider.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bb.paytminsider.R;
import com.bb.paytminsider.room.model.Event;
import com.bumptech.glide.RequestManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideshowPageAdapter extends PagerAdapter {

    List<Event> events;

    @BindView(R.id.imageSlideShow)
    ImageView imageSlideShow;

    @BindView(R.id.categorySlideshow)
    TextView categorySlideshow;

    private RequestManager requestManager;

    @Inject
    public SlideshowPageAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public int getCount() {
        return events == null ? 0 : events.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View currentView = layoutInflater.inflate(R.layout.slide_show_view, container,false);


        ButterKnife.bind(this, currentView);

        requestManager.load(events.get(position).getImage()).into(imageSlideShow);
        categorySlideshow.setText(String.valueOf(events.get(position).getCategory()));
        container.addView(currentView, 0);
        return currentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
