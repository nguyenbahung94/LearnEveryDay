package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.SnappyRecylerView.SnappyRecyclerView;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;

import java.util.List;

/**
 * Created by ptloc on 12/21/2017.
 */

public class SnappyRecipeAdapter extends SnappyRecyclerView.Adapter<SnappyRecyclerView.ViewHolder> {
    private Context context;
    private List<ObjectCategory> snappyRecipeList;
    private OnSnappyClickListener snappyClickListener;

    public SnappyRecipeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ObjectCategory> data) {
        this.snappyRecipeList = data;
        notifyDataSetChanged();
    }

    public void setSnappyClickListener(OnSnappyClickListener snappyClickListener) {
        this.snappyClickListener = snappyClickListener;
    }

    @Override
    public SnappyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SnappyRecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_snappy_recipce, parent, false));
    }

    @Override
    public void onBindViewHolder(final SnappyRecyclerView.ViewHolder holder, int position) {
        final SnappyRecipeViewHolder viewHolder = (SnappyRecipeViewHolder) holder;
        final ObjectCategory category = snappyRecipeList.get(position);
        if (Prefs.getInstance(context).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
            viewHolder.textView.setText(category.getJapanName());
        } else {
            viewHolder.textView.setText(category.getName());
        }

        Glide.with(context).load(category.getImage()).into(viewHolder.imageView);

        updateBackground(category.isSelected(), viewHolder);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSelected();
                category.setSelected(!category.isSelected());
                notifyDataSetChanged();
                if (snappyClickListener != null) {
                    snappyClickListener.OnSnappyItemClick(category.getName());
                }

            }
        });
    }

    private void resetSelected() {
        if (snappyRecipeList != null && !snappyRecipeList.isEmpty()) {
            for (ObjectCategory category : snappyRecipeList) {
                if (category.isSelected()) {
                    category.setSelected(!category.isSelected());
                    break;
                }
            }
        }
    }

    private void updateBackground(boolean isSelected, SnappyRecipeViewHolder viewHolder) {
        if (isSelected) {
            viewHolder.rootView.setBackgroundResource(R.drawable.button_border_corner_green_category_recipe);
            viewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            viewHolder.rootView.setBackgroundResource(R.drawable.button_border_corner_white_category_recipe);
            viewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.normal_text_color));
        }
    }

    public List<ObjectCategory> getSnappyRecipeList() {
        return snappyRecipeList;
    }

    @Override
    public int getItemCount() {
        return snappyRecipeList == null ? 0 : snappyRecipeList.size();
    }

    public void onDestroy() {
        this.context = null;
        snappyRecipeList = null;
        snappyClickListener = null;
    }

    public static class SnappyRecipeViewHolder extends SnappyRecyclerView.ViewHolder {
        LinearLayout rootView;
        ImageView imageView;
        TextView textView;

        SnappyRecipeViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
