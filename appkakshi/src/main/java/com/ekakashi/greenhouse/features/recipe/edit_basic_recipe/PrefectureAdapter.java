package com.ekakashi.greenhouse.features.recipe.edit_basic_recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectPrefecture;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;

import java.util.List;

/**
 * Created by ptloc on 1/7/2018.
 */

public class PrefectureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectPrefecture> prefectureList;
    private OnItemCallBack onItemClickCallBack;

    public void setOnItemClickCallBack(OnItemCallBack onItemCallBack) {
        this.onItemClickCallBack = onItemCallBack;
    }

    public void onDestroy() {
        onItemClickCallBack = null;
    }

    public PrefectureAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrefectureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catergory, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        final ObjectPrefecture prefecture = prefectureList.get(position);
        PrefectureViewHolder viewHolder = (PrefectureViewHolder) holder;

        if (Prefs.getInstance(holder.itemView.getContext()).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
            viewHolder.dialog_name.setText(prefecture.getJapanName());
        } else {
            viewHolder.dialog_name.setText(prefecture.getName());
        }

        if (prefecture.isSelected()) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        viewHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheck();
                prefecture.setSelected(!prefecture.isSelected());
                notifyDataSetChanged();
                if (onItemClickCallBack != null) {
                    onItemClickCallBack.OnItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return prefectureList == null ? 0 : prefectureList.size();
    }

    private void resetCheck() {
        for (ObjectPrefecture prefecture : prefectureList) {
            if (prefecture.isSelected()) {
                prefecture.setSelected(false);
                break;
            }
        }
    }

    public void setData(List<ObjectPrefecture> prefectureList) {
        this.prefectureList = prefectureList;
        notifyDataSetChanged();
    }

    private static class PrefectureViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgChecked;
        private TextView dialog_name;
        private RelativeLayout layoutCategory;

        PrefectureViewHolder(View itemView) {
            super(itemView);

            imgChecked = itemView.findViewById(R.id.imgChecked);
            dialog_name = itemView.findViewById(R.id.dialog_name);
            layoutCategory = itemView.findViewById(R.id.layoutCategory);
        }
    }
}
