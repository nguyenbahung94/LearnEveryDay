package com.ekakashi.greenhouse.features.recipe.stage.select_current_stage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.CustomOnStartDragListener;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.ItemTouchHelperAdapter;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.ItemTouchHelperViewHolder;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by ptloc on 12/15/2017.
 */

public class SelectionStageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private List<ObjectGrowth> stages;
    private OnItemCallBack onItemCallBack;
    private CustomOnStartDragListener mDragStartListener;
    private Bitmap bmChecked, bmUnchecked;
    public Context context;
    private boolean edit;

    public void setData(List<ObjectGrowth> stages) {
        this.stages.clear();
        this.stages.addAll(stages);
        notifyDataSetChanged();
    }

    void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }


    void setDragStartListener(CustomOnStartDragListener mDragStartListener) {
        this.mDragStartListener = mDragStartListener;
    }

    void setEditMode(boolean edit) {
        this.edit = edit;
        notifyDataSetChanged();
    }

    SelectionStageAdapter(Context context, List<ObjectGrowth> growthList) {
        this.context = context;
        this.stages = growthList;
        this.bmChecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.checked);
        this.bmUnchecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_uncheck);
    }

    public void onDestroy() {
        onItemCallBack = null;
        context = null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stage, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ObjectGrowth stage = stages.get(position);
        final StageViewHolder viewHolder = (StageViewHolder) holder;

        if (stage.isSelect()) {
            viewHolder.imgChecked.setImageBitmap(bmChecked);
        } else {
            viewHolder.imgChecked.setImageBitmap(bmUnchecked);
        }

        if (edit) {
            if (stage.isSelect()) {
                viewHolder.imgDelete.setVisibility(View.GONE);
                viewHolder.imgMove.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
                viewHolder.imgMove.setVisibility(View.VISIBLE);
            }
            viewHolder.imgChecked.setVisibility(View.GONE);
            viewHolder.imgInfo.setVisibility(View.GONE);

            viewHolder.layoutItem.setOnClickListener(null);
        } else {
            viewHolder.imgDelete.setVisibility(View.GONE);
            viewHolder.imgMove.setVisibility(View.GONE);

            viewHolder.imgChecked.setVisibility(View.VISIBLE);
            viewHolder.imgInfo.setVisibility(View.VISIBLE);

            viewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetSelectCheck();
                    stage.setSelect(!stage.isSelect());
                    notifyDataSetChanged();
                }
            });
        }

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStage(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.tvStage.setText(stage.getName());

        viewHolder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCallBack != null) {
                    onItemCallBack.OnItemClick(position);
                }
            }
        });

        viewHolder.imgMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

//        if (isLastPosition(position)) {
//            viewHolder.line.setVisibility(View.GONE);
//        } else {
//            viewHolder.line.setVisibility(View.VISIBLE);
//        }

    }

    private void removeStage(final int positionItem) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.text_confirm_delete_stage)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        stages.remove(positionItem);
                        notifyItemRemoved(positionItem);
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
        if (stages != null && !stages.isEmpty()) {
            for (int i = 0; i < stages.size(); i++) {
                stages.get(i).setOrderPos(i + 1);
            }
        }
        return stages;
    }

    private void resetSelectCheck() {
        if (stages != null && !stages.isEmpty()) {
            for (ObjectGrowth stage : stages) {
                if (stage.isSelect()) {
                    stage.setSelect(!stage.isSelect());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return stages == null ? 0 : stages.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(stages, i, i + 1);
            }

        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(stages, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private static class StageViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        TextView tvStage;
        ImageView imgInfo;
        ImageView imgChecked;
        ImageView imgDelete;
        ImageView imgMove;
        View line;
        RelativeLayout layoutItem;

        StageViewHolder(View itemView) {
            super(itemView);
            tvStage = itemView.findViewById(R.id.tvStage);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgMove = itemView.findViewById(R.id.imgMove);
            line = itemView.findViewById(R.id.line);
            layoutItem = itemView.findViewById(R.id.layoutItem);
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
