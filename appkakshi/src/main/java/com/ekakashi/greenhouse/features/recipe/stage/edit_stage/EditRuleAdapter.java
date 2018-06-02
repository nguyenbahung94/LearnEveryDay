package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;

import java.util.List;

/**
 * Created by ptloc on 2/2/2018.
 */

public class EditRuleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjectRule> rules;
    private Context context;
    private Bitmap bmNotifyOn, bmNotifyOff;

    EditRuleAdapter(List<ObjectRule> rules, Context context) {
        this.rules = rules;
        this.context = context;
        this.bmNotifyOn = BitmapFactory.decodeResource(context.getResources(), R.drawable.noti_on);
        this.bmNotifyOff = BitmapFactory.decodeResource(context.getResources(), R.drawable.noti_off);
    }

    public void onDestroy() {
        this.context = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditRuleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rule, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ObjectRule rule = rules.get(position);
        final EditRuleViewHolder viewHolder = (EditRuleViewHolder) holder;

        viewHolder.imgDelete.setVisibility(View.VISIBLE);

        viewHolder.imgNotifyRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rule.setNotify(!rule.isNotify());
                notifyItemChanged(position);
            }
        });


        viewHolder.imgNotifyRule.setImageBitmap(rule.isNotify() ? bmNotifyOn : bmNotifyOff);

        viewHolder.tvRule.setText(rule.getName());
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveItem(position);
            }
        });

    }

    private void onRemoveItem(final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.text_confirm_delete_rule)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        rules.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return rules == null ? 0 : rules.size();
    }

    public void setData(List<ObjectRule> rules) {
        this.rules = rules;
        notifyDataSetChanged();
    }

    public static class EditRuleViewHolder extends RecyclerView.ViewHolder {
        TextView tvRule;
        ImageView imgDelete;
        ImageView imgNotifyRule;

        EditRuleViewHolder(View itemView) {
            super(itemView);
            tvRule = itemView.findViewById(R.id.tvRule);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgNotifyRule = itemView.findViewById(R.id.imgNotifyRule);
        }
    }
}
