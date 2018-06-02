package com.ekakashi.greenhouse.features.timeline_post.post_desciption;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.timeline_filter.FilterInterface;

import java.util.ArrayList;

public class PostDescriptionAdapter extends RecyclerView.Adapter<PostDescriptionAdapter.PostDescriptionViewHolder> {

    private ArrayList<PostDescriptionItem> list;
    private FilterInterface.AutoTurnBack autoTurnBack;
    private LayoutInflater mLayoutInflater;

    public PostDescriptionAdapter(Context context, ArrayList<PostDescriptionItem> list, FilterInterface.AutoTurnBack autoTurnBack) {
        this.list = list;
        this.autoTurnBack = autoTurnBack;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PostDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_post_description, parent, false);
        return new PostDescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostDescriptionViewHolder holder, int position) {
        final PostDescriptionItem item = list.get(position);
        holder.tvPostDescription.setText(item.getName());

        if (item.isSelected()) {
            holder.imgSelected.setVisibility(View.VISIBLE);
        } else {
            holder.imgSelected.setVisibility(View.INVISIBLE);
        }

        holder.layoutDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelected();
                item.setSelected(true);
                holder.imgSelected.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                autoTurnBack.onAutoTurnBack();
            }
        });
    }

    /*
     * set all item false
     */
    private void clearListSelected() {
        for (PostDescriptionItem item : list) {
            item.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PostDescriptionViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSelected;
        private TextView tvPostDescription;
        private RelativeLayout layoutDescription;

        public PostDescriptionViewHolder(View itemView) {
            super(itemView);
            imgSelected = itemView.findViewById(R.id.imgSelected);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
            layoutDescription = itemView.findViewById(R.id.layoutDescription);
        }
    }
}
