package com.ekakashi.greenhouse.features.recipe.edit_recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.OnInfoCallBack;

import java.util.List;


public class EditRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectRecipe> recipeList;
    private Context context;
    private OnItemCallBack mItemCallBack;
    private OnInfoCallBack onInfoCallBack;
    private boolean editMode;

    EditRecipeAdapter(Context context) {
        this.context = context;
    }

    void setItemCallBack(OnItemCallBack mItemCallBack) {
        this.mItemCallBack = mItemCallBack;
    }

    void setOnInfoCallBack(OnInfoCallBack onInfoCallBack) {
        this.onInfoCallBack = onInfoCallBack;
    }

    void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditRecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = holder.getAdapterPosition();
        final ObjectRecipe recipe = recipeList.get(pos);
        EditRecipeViewHolder viewHolder = (EditRecipeViewHolder) holder;
        loadData((EditRecipeViewHolder) holder, recipe);

        viewHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onInfoCallBack != null) {
                    onInfoCallBack.onInfoCallBack(position);
                }
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemCallBack != null) {
                    mItemCallBack.OnItemClick(pos);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData(EditRecipeViewHolder holder, ObjectRecipe recipe) {
        if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
            Glide.with(context).load(recipe.getImage()).into(holder.imgRecipe);
        }
        holder.tvRecipeName.setText(recipe.getRecipeName());

        String stage = getCurrentStageName(recipe);
        if (stage != null && !stage.isEmpty()) {
            holder.tvStage.setText(context.getString(R.string.add_stage_string) + " " + stage);
            holder.tvStage.setVisibility(View.VISIBLE);
        } else {
            holder.tvStage.setVisibility(View.GONE);
        }

        if (editMode) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgMore.setVisibility(View.GONE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgMore.setVisibility(View.VISIBLE);
        }
    }


    private String getCurrentStageName(ObjectRecipe recipe) {
        int currentStageId = recipe.getCurrentStageId();
        if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
            for (ObjectGrowth stage : recipe.getStages()) {
                if (stage.getId() != null) {
                    if (stage.getId() == currentStageId) {
                        return stage.getName();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    void addData(List<ObjectRecipe> mRecipeList) {
        recipeList = mRecipeList;
        notifyDataSetChanged();
    }

    public void onDestroy() {
        context = null;
    }

    private static class EditRecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRecipe, imgDelete, imgMore;
        TextView tvRecipeName;
        TextView tvStage;
        RelativeLayout layoutItem, layoutStage;
        View line;

        EditRecipeViewHolder(View itemView) {
            super(itemView);

            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgMore = itemView.findViewById(R.id.imgMore);
//            imgDown = itemView.findViewById(R.id.imgDown);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvStage = itemView.findViewById(R.id.tvStage);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            layoutStage = itemView.findViewById(R.id.layoutStage);
            line = itemView.findViewById(R.id.line);
        }
    }
}
