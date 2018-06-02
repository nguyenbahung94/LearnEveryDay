package com.ekakashi.greenhouse.features.timeline.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.dao.RecentSearch;
import com.ekakashi.greenhouse.features.timeline.TimelineInterface;

import java.util.ArrayList;
import java.util.List;


public class RecentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<RecentSearch> list = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private TimelineInterface.RecentSearchClick mCallback;

    public RecentSearchAdapter(List<RecentSearch> list, TimelineInterface.RecentSearchClick mCallback, Context context) {
        this.list = list;
        this.mCallback = mCallback;
        mLayoutInflater = LayoutInflater.from(context);
        this.list.add(0, new RecentSearch());
    }

    public void updateSearchList(List<RecentSearch> list) {
        this.list = list;
        this.list.add(0, new RecentSearch());
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = mLayoutInflater.inflate(R.layout.item_recent_search_header, parent, false);
            return new HeaderViewHolder(layoutView);
        } else {
            View layoutView = mLayoutInflater.inflate(R.layout.item_recent_search, parent, false);
            return new ItemViewHolder(layoutView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        RecentSearch recentSearch = list.get(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tvRecentSearch.setText(recentSearch.getKeySearch());
            ((ItemViewHolder) holder).tvRecentSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onRecentSearchClick(((ItemViewHolder) holder).tvRecentSearch.getText().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRecentSearch;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvRecentSearch = itemView.findViewById(R.id.tvRecentSearch);
        }
    }

}
