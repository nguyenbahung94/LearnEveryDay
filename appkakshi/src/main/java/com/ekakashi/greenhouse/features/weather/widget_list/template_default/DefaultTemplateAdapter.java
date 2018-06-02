package com.ekakashi.greenhouse.features.weather.widget_list.template_default;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnItemCLickListener;

import java.util.List;

/**
 * Created by nbhung on 3/12/2018.
 */

public class DefaultTemplateAdapter extends RecyclerView.Adapter<DefaultTemplateAdapter.ViewHolder> {
    private Context context;
    private List<Data> defaultTemplateList;
    private OnItemCLickListener onItemCLickListener;

    public DefaultTemplateAdapter(Context context, List<Data> defaultTemplateList) {
        this.context = context;
        this.defaultTemplateList = defaultTemplateList;
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imv.setImageResource(R.drawable.avatar2);

        holder.imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCLickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return defaultTemplateList == null ? 0 : defaultTemplateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imv;

        public ViewHolder(View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imv);
        }
    }

}
