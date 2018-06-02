package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;

import java.util.Collections;
import java.util.List;

import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.*;

import static android.view.View.GONE;

/**
 * Created by ptloc on 12/8/2017.
 */

public class EditStageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private List<ObjectGrowth> growthList;
    private Context context;
    private OnStartDragListener mDragStartListener;
    private int currentStageId;
    private OnItemCallBack onItemCallBack;
    private OnInfoCallBack onInfoCallBack;
    private OnRemoveStageCallBack onRemoveStageCallBack;
    private boolean edit = false;
    private int currentStagePos;

    public void setData(List<ObjectGrowth> mStageList) {
        this.growthList = mStageList;
        notifyDataSetChanged();
    }

    public void onDestroy() {
        context = null;
        onItemCallBack = null;
        onRemoveStageCallBack = null;
        onInfoCallBack = null;
        mDragStartListener = null;
    }

    public void setEdit(boolean editPressed) {
        this.edit = editPressed;
        notifyDataSetChanged();
    }

    public void setCurrentStageId(int currentStageId) {
        this.currentStageId = currentStageId;
        currentStagePos = getCurrentStagePosition();
        notifyDataSetChanged();
    }

    public int getCurrentStageId() {
        return currentStageId;
    }

    void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    void setOnInfoCallBack(OnInfoCallBack onInfoCallBack) {
        this.onInfoCallBack = onInfoCallBack;
        onInfoCallBack.setPositionSelect(this.currentStagePos);
    }

    void setOnRemoveStageCallBack(OnRemoveStageCallBack onRemoveStageCallBack) {
        this.onRemoveStageCallBack = onRemoveStageCallBack;
    }

    EditStageAdapter(Context context, List<ObjectGrowth> growthList, OnStartDragListener dragStartListener, int currentStageId) {
        mDragStartListener = dragStartListener;
        this.growthList = growthList;
        this.context = context;
        this.currentStageId = currentStageId;
        this.currentStagePos = getCurrentStagePosition();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GrowthPeriodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_stage, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ObjectGrowth stage = growthList.get(position);
        final GrowthPeriodViewHolder viewHolder = (GrowthPeriodViewHolder) holder;
        viewHolder.tvGrowthPeriod.setText(stage.getName());

        if (edit) {
            if (position < currentStagePos) {
                viewHolder.imgDelete.setVisibility(View.GONE);
                viewHolder.imgMove.setVisibility(View.GONE);
            } else {
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
                viewHolder.imgMove.setVisibility(View.VISIBLE);
            }
            viewHolder.imgInfo.setVisibility(GONE);
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgDelete.setVisibility(GONE);
            viewHolder.imgMove.setVisibility(GONE);

            viewHolder.imgInfo.setVisibility(View.VISIBLE);
            if (stage.getId() == currentStageId) {
                viewHolder.imgChecked.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgChecked.setVisibility(View.INVISIBLE);
            }
        }

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRemoveStageCallBack != null) {
                    onRemoveItem(stage, position);
                }
            }
        });

        viewHolder.imgMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder, currentStagePos, position);
                }
                return false;
            }
        });

        viewHolder.layoutGrowth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stage.getId() != currentStageId) {
                    if (onItemCallBack != null) {
                        onItemCallBack.OnItemClick(viewHolder.getAdapterPosition());
                    }
                }
            }
        });

        viewHolder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onInfoCallBack != null) {
                    onInfoCallBack.onInfoCallBack(viewHolder.getAdapterPosition());
                }
            }
        });

    }

    private void onRemoveItem(final ObjectGrowth stage, final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.text_confirm_delete_stage)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (currentStageId == stage.getId()) {
                            Toast.makeText(context, R.string.text_do_not_allowed_stage, Toast.LENGTH_SHORT).show();
                        } else {
                            onRemoveStageCallBack.onRemoveStage(stage, position);
                        }
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();

    }

    public List<ObjectGrowth> getStages() {
        return sortStages();
    }

    private List<ObjectGrowth> sortStages() {
        if (growthList != null && !growthList.isEmpty()) {
            for (int i = 0; i < growthList.size(); i++) {
                growthList.get(i).setOrderPos(i + 1);
            }
        }
        return growthList;
    }

    private int getCurrentStagePosition() {
        if (growthList != null && !growthList.isEmpty()) {
            int position, listSize = growthList.size();
            for (position = 0; position < listSize; position++) {
                if (growthList.get(position).getId() != null) {
                    if (growthList.get(position).getId() == currentStageId) {
                        return position + 1;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return growthList == null ? 0 : growthList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(growthList, i, i + 1);
            }

        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(growthList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
//        onInfoCallBack.setPositionSelect(getCurrentStagePosition());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
    }

    public static class GrowthPeriodViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView tvGrowthPeriod;
        ImageView imgDelete, imgChecked, imgInfo, imgMove;
        RelativeLayout layoutGrowth;
        View line;

        GrowthPeriodViewHolder(View itemView) {
            super(itemView);
            tvGrowthPeriod = itemView.findViewById(R.id.tvGrowthPeriod);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgMove = itemView.findViewById(R.id.imgMove);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            layoutGrowth = itemView.findViewById(R.id.layoutGrowth);
            line = itemView.findViewById(R.id.line);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }
}
