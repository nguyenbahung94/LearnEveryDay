package com.ekakashi.greenhouse.features.recipe.stage.info_stage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by ptloc on 12/15/2017.
 */

public class RuleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectRule> ruleList;
    private boolean edit;
    private boolean fromSelectStage;
    private OnItemCallBack onItemCallBack;
    private OnNotifyRuleCallBack onNotifyRuleCallBack;
    private OnRemoveItemClick onRemoveItemClick;
    private Bitmap bmNotifyOn, bmNotifyOff;
    private Context context;
    private ObjectRecipe mRecipe;

    RuleListAdapter(Context context, ObjectRecipe recipe, List<ObjectRule> rules) {
        this.context = context;
        this.ruleList = rules;
        this.edit = false;
        this.bmNotifyOn = BitmapFactory.decodeResource(context.getResources(), R.drawable.noti_on);
        this.bmNotifyOff = BitmapFactory.decodeResource(context.getResources(), R.drawable.noti_off);
        this.mRecipe = recipe;
    }

    public void onDestroy() {
        context = null;
        onItemCallBack = null;
        onNotifyRuleCallBack = null;
        onRemoveItemClick = null;
    }

    void setOnRemoveItemClick(OnRemoveItemClick onRemoveItemClick) {
        this.onRemoveItemClick = onRemoveItemClick;
    }

    void setOnNotifyRuleCallBack(OnNotifyRuleCallBack onNotifyRuleCallBack) {
        this.onNotifyRuleCallBack = onNotifyRuleCallBack;
    }

    void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RuleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ObjectRule rule = ruleList.get(position);
        RuleViewHolder viewHolder = (RuleViewHolder) holder;

        if (fromSelectStage) {
            viewHolder.imgNotifyRule.setEnabled(false);
        } else {
            if (edit) {
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgDelete.setVisibility(GONE);
            }
            viewHolder.imgNotifyRule.setEnabled(true);
        }

        viewHolder.imgNotifyRule.setImageBitmap(rule.isNotify() ? bmNotifyOn : bmNotifyOff);

        viewHolder.tvRule.setText(Utils.createRuleName(context, mRecipe, rule.getRuleType(), rule.getConditions(), rule.getActions()));
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRemoveItemClick != null) {
                    onRemoveItemClick.onRemoveItem(position);
                }
            }
        });

        viewHolder.imgNotifyRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rule.setNotify(!rule.isNotify());
                if (onNotifyRuleCallBack != null) {
                    onNotifyRuleCallBack.notifyRule(rule, position);
                }
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

    @Override
    public int getItemCount() {
        return ruleList == null ? 0 : ruleList.size();
    }

    public void setEdit(boolean editMode) {
        this.edit = editMode;
        notifyDataSetChanged();
    }

    public void setRules(List<ObjectRule> rules) {
        this.ruleList = rules;
        notifyDataSetChanged();
    }

    void setFromSelectStage(boolean fromSelectStage) {
        this.fromSelectStage = fromSelectStage;
    }

    private static class RuleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRule;
        private ImageView imgNotifyRule;
        private ImageView imgDelete;
        private RelativeLayout layoutItem;

        RuleViewHolder(View itemView) {
            super(itemView);
            tvRule = itemView.findViewById(R.id.tvRule);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            imgNotifyRule = itemView.findViewById(R.id.imgNotifyRule);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
