package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.features.recipe.stage.info_stage.OnRemoveItemClick;

import java.util.List;

/**
 * Created by ptloc on 12/26/2017.
 */

public class ActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectAction> actionList;
    private Context context;
    private OnItemCallBack onItemCallBack;
    private OnRemoveItemClick onRemoveCallBack;
    private boolean isEdit;

    ActionAdapter(Context context, List<ObjectAction> objectActionList) {
        this.context = context;
        this.actionList = objectActionList;
    }

    public void onDestroy() {
        context = null;
        onItemCallBack = null;
        onRemoveCallBack = null;
    }

    void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    void setOnRemoveCallBack(OnRemoveItemClick onRemoveCallBack) {
        this.onRemoveCallBack = onRemoveCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ObjectAction action = actionList.get(position);
        ActionViewHolder viewHolder = (ActionViewHolder) holder;

        viewHolder.tvAction.setText(context.getString(R.string.action) + " " + (position + 1));

        if (action.getActionType() == EnumRule.POST_TO_TIMELINE) {
            viewHolder.tvDetail.setText(R.string.post_to_time_line);
        } else if (action.getActionType() == EnumRule.MOVE_TO_NEXT_STAGE) {
            viewHolder.tvDetail.setText(R.string.proceed_to_next_stage);
        } else {
            viewHolder.tvDetail.setText(R.string.give_cultivation_management_advice);
        }

        if (isEdit) {
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgDelete.setVisibility(View.GONE);
        }

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveItem(action, position);
            }
        });

        viewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCallBack != null) {
                    onItemCallBack.OnItemClick(position);
                }
            }
        });

    }

    private void onRemoveItem(final ObjectAction action, final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(context.getString(R.string.text_confirm_delete_action))
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        actionList.remove(action);
                        notifyDataSetChanged();
                        if (onRemoveCallBack != null) {
                            onRemoveCallBack.onRemoveItem(position);
                        }
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return actionList == null ? 0 : actionList.size();
    }

    void setEditMode(boolean isON) {
        isEdit = isON;
        notifyDataSetChanged();
    }

    public void setActions(List<ObjectAction> actions) {
        this.actionList = actions;
        notifyDataSetChanged();
    }


    private static class ActionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAction;
        private TextView tvDetail;
        private RelativeLayout layoutItem;
        private ImageView imgDelete;

        ActionViewHolder(View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
