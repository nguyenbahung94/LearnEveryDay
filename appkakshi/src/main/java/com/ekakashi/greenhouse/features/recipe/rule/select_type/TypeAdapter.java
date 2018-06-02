package com.ekakashi.greenhouse.features.recipe.rule.select_type;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectType;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by ptloc on 1/30/2018.
 */

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectType> objectRuleTypes;
    private OnItemCallBack callBack;

    public TypeAdapter(List<ObjectType> objectRuleTypes) {
        this.objectRuleTypes = objectRuleTypes;
    }

    public void setCallBack(OnItemCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rule_type, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ObjectType ruleType = objectRuleTypes.get(position);
        TypeViewHolder viewHolder = (TypeViewHolder) holder;
        viewHolder.tvRuleType.setText(ruleType.getDescription());

        if (ruleType.isSelect()) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        viewHolder.layoutRuleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSelectItem();
                ruleType.setSelect(!ruleType.isSelect());
                notifyDataSetChanged();
                if (callBack != null) {
                    callBack.OnItemClick(position);
                }
            }
        });

        if (position == (objectRuleTypes.size() - 1)) {
            viewHolder.line.setVisibility(GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }
    }

    private void resetSelectItem() {
        for (ObjectType type : objectRuleTypes) {
            if (type.isSelect()) {
                type.setSelect(!type.isSelect());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return objectRuleTypes == null ? 0 : objectRuleTypes.size();
    }

    private static class TypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRuleType;
        private ImageView imgChecked;
        private RelativeLayout layoutRuleType;
        private View line;

        TypeViewHolder(View itemView) {
            super(itemView);
            tvRuleType = itemView.findViewById(R.id.tvRuleType);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            layoutRuleType = itemView.findViewById(R.id.layoutRuleType);
            line = itemView.findViewById(R.id.line);
        }
    }
}