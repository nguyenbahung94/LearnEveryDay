package com.ekakashi.greenhouse.features.recipe.stage.info_stage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;

import java.util.List;

/**
 * Created by ptloc on 12/15/2017.
 */

public class StageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectGrowth> growthList;
//    private boolean isDefault = false;
//    private boolean fromEditRecipe = false;
    private int currentStageId;

    public void setData(List<ObjectGrowth> stages) {
        growthList = stages;
        notifyDataSetChanged();
    }

//    public void setFromEditRecipe(boolean fromEditRecipe) {
//        this.fromEditRecipe = fromEditRecipe;
//    }
//
//    public void setDefault(boolean isDefault) {
//        this.isDefault = isDefault;
//    }

    public StageAdapter(List<ObjectGrowth> growthList, Integer currentStage) {
        this.growthList = growthList;
        this.currentStageId = currentStage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stage, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ObjectGrowth stage = growthList.get(position);
        StageViewHolder viewHolder = (StageViewHolder) holder;
        viewHolder.tvStage.setText(stage.getName());
        if (currentStageId == stage.getId()) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        viewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isDefault || fromEditRecipe) {
                    currentStageId = stage.getId();
                    notifyDataSetChanged();
//                }
            }
        });

        if (position == (growthList.size() - 1)) {
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }

    }

    public int getCurrentStageId() {
        return currentStageId;
    }

    @Override
    public int getItemCount() {
        return growthList == null ? 0 : growthList.size();
    }


    public static class StageViewHolder extends RecyclerView.ViewHolder {

        TextView tvStage;
        ImageView imgInfo;
        ImageView imgChecked;
        View line;
        RelativeLayout layoutItem;

        StageViewHolder(View itemView) {
            super(itemView);
            tvStage = itemView.findViewById(R.id.tvStage);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            line = itemView.findViewById(R.id.line);
            layoutItem = itemView.findViewById(R.id.layoutItem);
        }
    }
}
