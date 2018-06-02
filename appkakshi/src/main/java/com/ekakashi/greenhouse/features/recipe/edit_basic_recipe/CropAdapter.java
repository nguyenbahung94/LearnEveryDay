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
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;

import java.util.List;

/**
 * Created by ptloc on 3/28/2018.
 */

public class CropAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObjectCrop> cropList;
    private OnItemCallBack onItemClickCallBack;
    private CropViewHolder viewHolder;

    public void setOnItemClickCallBack(OnItemCallBack onItemCallBack) {
        this.onItemClickCallBack = onItemCallBack;
    }

    public void onDestroy() {
        onItemClickCallBack = null;
    }

    CropAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CropViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catergory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        final ObjectCrop crop = cropList.get(position);
        viewHolder = (CropViewHolder) holder;

        if (Prefs.getInstance(holder.itemView.getContext()).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
            viewHolder.dialog_name.setText(crop.getNameJapan());
        } else {
            viewHolder.dialog_name.setText(crop.getName());
        }

        if (crop.isSelected()) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        viewHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheck();
                crop.setSelected(!crop.isSelected());
                notifyDataSetChanged();
                if (onItemClickCallBack != null) {
                    onItemClickCallBack.OnItemClick(position);
                }
            }
        });

    }

    public void clearDataCrop() {
        cropList.clear();
    }

    @Override
    public int getItemCount() {
        return cropList == null ? 0 : cropList.size();
    }

    private void resetCheck() {
        for (ObjectCrop crop : cropList) {
            if (crop.isSelected()) {
                crop.setSelected(false);
                break;
            }
        }
    }


    void addData(List<ObjectCrop> mCategoryList) {
        this.cropList = mCategoryList;
        notifyDataSetChanged();
    }

    private static class CropViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgChecked;
        private TextView dialog_name;
        private RelativeLayout layoutCategory;

        CropViewHolder(View itemView) {
            super(itemView);

            imgChecked = itemView.findViewById(R.id.imgChecked);
            dialog_name = itemView.findViewById(R.id.dialog_name);
            layoutCategory = itemView.findViewById(R.id.layoutCategory);
        }
    }
}
