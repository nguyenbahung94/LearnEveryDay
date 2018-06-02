package com.ekakashi.greenhouse.features.recipe.edit_basic_recipe;

import android.support.annotation.NonNull;
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
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;

import java.util.List;

/**
 * Created by ptloc on 1/7/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectCategory> categoryList;
    private OnItemCallBack onItemClickCallBack;

    public void setOnItemClickCallBack(OnItemCallBack onItemCallBack) {
        this.onItemClickCallBack = onItemCallBack;
    }

    public void onDestroy() {
        onItemClickCallBack = null;
    }

    CategoryAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catergory, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ObjectCategory category = categoryList.get(position);
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;

        if (Prefs.getInstance(holder.itemView.getContext()).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
            viewHolder.dialog_name.setText(category.getJapanName());
        } else {
            viewHolder.dialog_name.setText(category.getName());
        }

        if (category.isSelected()) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        viewHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheck();
                category.setSelected(!category.isSelected());
                notifyDataSetChanged();
                if (onItemClickCallBack != null) {
                    onItemClickCallBack.OnItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    private void resetCheck() {
        for (ObjectCategory category : categoryList) {
            if (category.isSelected()) {
                category.setSelected(false);
                break;
            }
        }
    }

    void addData(List<ObjectCategory> mCategoryList) {
        this.categoryList = mCategoryList;
        notifyDataSetChanged();
    }

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgChecked;
        private TextView dialog_name;
        private RelativeLayout layoutCategory;

        CategoryViewHolder(View itemView) {
            super(itemView);

            imgChecked = itemView.findViewById(R.id.imgChecked);
            dialog_name = itemView.findViewById(R.id.dialog_name);
            layoutCategory = itemView.findViewById(R.id.layoutCategory);
        }
    }
}
