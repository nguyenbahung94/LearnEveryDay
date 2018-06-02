package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemClickListener;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ptloc on 12/13/2017.
 */

public class RecipeSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinkedHashMap<Integer, ObjectRecipe> mRecipeList;
    private Context context;
    private OnItemClickListener onItemClick;
    private OnCheckRecipeInterface onCheckRecipeInterface;
    private OnPressInfoInterface onPressInfoInterface;
    private Bitmap bmChecked, bmUnchecked;
    private int numberRecipeSelected = 0;

    RecipeSelectionAdapter(Context context) {
        this.context = context;
        this.bmChecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.checked);
        this.bmUnchecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_uncheck);
        numberRecipeSelected = 0;
    }

    void setRecipeList(LinkedHashMap<Integer, ObjectRecipe> items) {
        this.mRecipeList = items;
        notifyDataSetChanged();
    }


    public void onDestroy() {
        context = null;
        onItemClick = null;
        onCheckRecipeInterface = null;
        onPressInfoInterface = null;
    }

    public interface OnCheckRecipeInterface {
        void onClickImageCheckCallback(int numberRecipeSelected, int position);
    }

    public interface OnPressInfoInterface {
        void onClickInfoCallback(ObjectRecipe recipe, int position);
    }

    void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    void setOnCheckRecipeInterface(OnCheckRecipeInterface onCheckRecipeInterface) {
        this.onCheckRecipeInterface = onCheckRecipeInterface;
    }

    void setOnPressInfoInterface(OnPressInfoInterface onPressInfoInterface) {
        this.onPressInfoInterface = onPressInfoInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_selection, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MyRecipeViewHolder viewHolder = (MyRecipeViewHolder) holder;
        final ObjectRecipe recipe = getByIndex(position);
        String description;

        viewHolder.tvRecipeName.setText(recipe.getRecipeName());
        String[] descriptionList;
        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
//            descriptionList = recipe.getDescription().split("\\r?\\n");
            descriptionList = recipe.getDescription().split(".");
            if (descriptionList.length != 0) {
                description = descriptionList[0];
                if (description != null && !description.isEmpty()) {
                    viewHolder.tvRecipeDetail.setText(description);
                    viewHolder.tvRecipeDetail.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvRecipeDetail.setText(recipe.getDescription());
                    viewHolder.tvRecipeDetail.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.tvRecipeDetail.setVisibility(View.GONE);
            }
        } else {
            viewHolder.tvRecipeDetail.setVisibility(View.GONE);
        }


        String stage = getCurrentStage(recipe);
        if (stage != null && !stage.trim().isEmpty()) {
            viewHolder.tvStage.setText(context.getString(R.string.add_stage_string) + " " + stage);
            viewHolder.layoutStage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layoutStage.setVisibility(View.GONE);
        }

        Glide.with(context).load(recipe.getImage()).into(viewHolder.imgRecipe);

        viewHolder.imgCheck.setImageBitmap(recipe.isSelected() ? bmChecked : bmUnchecked);

        viewHolder.layoutBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe.isSelected()) {
                    numberRecipeSelected = 0;
                } else {
                    clearSelectInArray();
                    numberRecipeSelected = 1;
                }
                recipe.setSelected(!recipe.isSelected());

                viewHolder.imgCheck.setImageBitmap(recipe.isSelected() ? bmChecked : bmUnchecked);
                notifyDataSetChanged();

                if (onCheckRecipeInterface != null) {
                    onCheckRecipeInterface.onClickImageCheckCallback(numberRecipeSelected, position);
                }
            }
        });

        viewHolder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPressInfoInterface != null) {
                    onPressInfoInterface.onClickInfoCallback(recipe, position);
                }
            }
        });

        viewHolder.layoutStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.OnRecipeClick(recipe, position);
                }
            }
        });

    }

    public String getCurrentStage(ObjectRecipe recipe) {
        if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
            for (ObjectGrowth stage : recipe.getStages()) {
                if (stage.isSelect()) {
                    return stage.getName();
                }
            }
        }
        return null;
    }

    private void clearSelectInArray() {
        if (mRecipeList != null && !mRecipeList.isEmpty()) {
            for (Integer recipeId : mRecipeList.keySet()) {
                mRecipeList.get(recipeId).setSelected(false);
            }
        }
    }

    private ObjectRecipe getByIndex(int index) {
        return (ObjectRecipe) mRecipeList.values().toArray()[index];
    }

//    private int indexOf(int recipeId) {
//        if (mRecipeList != null && !mRecipeList.isEmpty()) {
//            int index = -1;
//            for (Integer key : mRecipeList.keySet()) {
//                index++;
//                if (recipeId == key) {
//                    return index;
//                }
//            }
//        }
//        return -1;
//    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }

    private static class MyRecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipe;
        ImageView imgInfo;
        ImageView imgDown;
        TextView tvRecipeName;
        TextView tvRecipeDetail;
        TextView tvCategoryRecipe;
        View lineBelowCategory;
        TextView tvStage;
        ImageView imgCheck;
        RelativeLayout layoutBody;
        RelativeLayout layoutStage;

        MyRecipeViewHolder(View itemView) {
            super(itemView);

            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            imgDown = itemView.findViewById(R.id.imgDown);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvRecipeDetail = itemView.findViewById(R.id.tvRecipeDetail);
            tvCategoryRecipe = itemView.findViewById(R.id.tvCategoryRecipe);
            lineBelowCategory = itemView.findViewById(R.id.line_below_category);
            tvStage = itemView.findViewById(R.id.tvStage);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            layoutBody = itemView.findViewById(R.id.layoutBody);
            layoutStage = itemView.findViewById(R.id.layoutStage);
        }
    }
}
