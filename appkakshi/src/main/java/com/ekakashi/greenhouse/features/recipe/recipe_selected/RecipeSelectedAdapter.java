package com.ekakashi.greenhouse.features.recipe.recipe_selected;

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
import com.ekakashi.greenhouse.features.recipe.rule.OnItemClickListener;

import java.util.List;

/**
 * Created by ptloc on 12/14/2017.
 */

public class RecipeSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectRecipe> recipeList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnRecipeStageInterface onRecipeStageInterface;

    RecipeSelectedAdapter(Context context, List<ObjectRecipe> recipeList) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public interface OnRecipeStageInterface {
        void onClickRecipeStageCallback(ObjectRecipe recipe, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnRecipeStageInterface(OnRecipeStageInterface onRecipeStageInterface) {
        this.onRecipeStageInterface = onRecipeStageInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeSelectedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_selected, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ObjectRecipe recipe = recipeList.get(position);
        final RecipeSelectedViewHolder viewHolder = (RecipeSelectedViewHolder) holder;

//        Glide.with(context).load(R.drawable.avatar2).into(viewHolder.imgRecipe);
        viewHolder.tvRecipeName.setText(recipe.getRecipeName());
        Glide.with(context).load(recipe.getImage()).into(viewHolder.imgRecipe);

        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
            viewHolder.tvRecipeDetail.setText(recipe.getDescription());
            viewHolder.tvRecipeDetail.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvRecipeDetail.setVisibility(View.GONE);
        }

        String stage = getCurrentStage(recipe);
        if (stage != null) {
            viewHolder.tvStage.setText(context.getString(R.string.add_stage_string) + " " + stage);
            viewHolder.layoutStage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layoutStage.setVisibility(View.GONE);
        }

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnRecipeClick(recipe, position);
                }
            }
        });

        viewHolder.layoutStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecipeStageInterface != null) {
                    onRecipeStageInterface.onClickRecipeStageCallback(recipe, position);
                }
            }
        });
    }

    private String getCurrentStage(ObjectRecipe recipe) {
        if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
            if (recipe.getEkFields().get(0).getCurrentStage().getId() != null) {
                int currentStageId = recipe.getEkFields().get(0).getCurrentStage().getId();
                for (ObjectGrowth objectGrowth : recipe.getStages()) {
                    if (objectGrowth.getId() != null) {
                        if (objectGrowth.getId() == currentStageId) {
                            return objectGrowth.getName();
                        }
                    }
                }
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    private static class RecipeSelectedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDelete;
        ImageView imgRecipe;
        TextView tvRecipeName;
        TextView tvRecipeDetail;
        TextView tvStage;
        RelativeLayout layoutStage;

        RecipeSelectedViewHolder(View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvRecipeDetail = itemView.findViewById(R.id.tvRecipeDetail);
            tvStage = itemView.findViewById(R.id.tvStage);
            layoutStage = itemView.findViewById(R.id.layoutStage);
        }
    }
}
