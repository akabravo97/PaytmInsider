package com.bb.paytminsider.ui.fragments.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bb.paytminsider.R;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.ui.fragments.callbacks.EventsRecyclerViewItemClickListener;
import com.bumptech.glide.RequestManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "EventsCardAdapter";

    List<Event> events;
    private RequestManager requestManager;
    private EventsRecyclerViewItemClickListener viewItemClickListener;
    private int transitionId = 0;

    @Inject
    public EventsCardAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_info_card, parent, false);
        return new EventsCardViewHolder(view);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        ((EventsCardViewHolder) holder).clearAnimation();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EventsCardViewHolder) holder).bind(events.get(position), viewItemClickListener);
        animateIncomingCards(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }


    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public void setViewItemClickListener(EventsRecyclerViewItemClickListener viewItemClickListener) {
        this.viewItemClickListener = viewItemClickListener;
    }

    public void animateIncomingCards(View view) {
        Animation slidingAnimation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in);
        view.startAnimation(slidingAnimation);
    }

    public class EventsCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageEventDetails)
        ImageView imageEvent;
        @BindView(R.id.dateTextView)
        TextView dateTextView;
        @BindView(R.id.priceTextView)
        TextView priceTextView;
        @BindView(R.id.eventCategory)
        TextView categoryTextView;
        @BindView(R.id.nameTextView)
        TextView nameTextView;

        CircularProgressDrawable progressDrawable;

        String rupeesSymbol;
        View rootView;

        public EventsCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressDrawable = new CircularProgressDrawable(itemView.getContext());
            progressDrawable.setStrokeWidth(5f);
            progressDrawable.setCenterRadius(30f);
            progressDrawable.setBackgroundColor(R.color.blue_btn_bg_color);
            rupeesSymbol = itemView.getContext().getResources().getString(R.string.rupeesSymbol);
            rootView = itemView;
        }

        public void bind(Event event, EventsRecyclerViewItemClickListener listener) {
            progressDrawable.start();
            requestManager.load(event.getImage()).into(imageEvent);
            nameTextView.setText(event.getName());
            dateTextView.setText(event.getVeneueDate());
            if (event.getPrice() == 0) {
                priceTextView.setText(rootView.getContext().getString(R.string.price_free_event));
            } else {
                priceTextView.setText(rupeesSymbol + event.getPrice());
            }

            categoryTextView.setText(event.getCategory());

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventCardSelected(event);
                    //"transition" + getAdapterPosition()
                }
            });
        }

        public void clearAnimation() {
            rootView.clearAnimation();
        }


    }


}
