package com.bb.paytminsider.ui.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.paytminsider.R;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.ui.fragments.callbacks.EventsSubscriptionRecyclerViewItemClickListener;
import com.bb.paytminsider.utils.EventSubscriptionDiffUtil;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventSubscriptionRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RequestManager requestManager;
    private EventsSubscriptionRecyclerViewItemClickListener eventsSubscriptionRecyclerViewItemClickListener;
    List<Event> events = new ArrayList<>();

    @Inject
    public EventSubscriptionRecyclerAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_subscription_card_view, parent, false);
        return new EventsSubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EventsSubscriptionViewHolder) holder).bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public void setEventsSubscriptionRecyclerViewItemClickListener(EventsSubscriptionRecyclerViewItemClickListener eventsSubscriptionRecyclerViewItemClickListener) {
        this.eventsSubscriptionRecyclerViewItemClickListener = eventsSubscriptionRecyclerViewItemClickListener;
    }

    public void setEvents(List<Event> events) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EventSubscriptionDiffUtil(this.events, events));
        this.events = events;
        diffResult.dispatchUpdatesTo(this);
    }

    public class EventsSubscriptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.eventSubscriptionImage)
        ImageView eventSubscriptionImage;

        @BindView(R.id.eventUnsubscriptionButton)
        Button eventUnsubscriptionButton;

        @BindView(R.id.eventSubscriptionName)
        TextView eventSubscriptionName;

        private View rootView;

        public EventsSubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }

        public void bind(Event event) {
            requestManager.load(event.getImage()).centerCrop().into(eventSubscriptionImage);
            eventUnsubscriptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventsSubscriptionRecyclerViewItemClickListener.onUnsubscribebuttonClickd(event);
                }
            });
            eventSubscriptionName.setText(event.getName());
        }
    }
}
