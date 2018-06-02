package com.ekakashi.greenhouse.features.weather.period;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;

import java.util.ArrayList;

/**
 * Created by nquochuy on 3/13/2018.
 */

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.PeriodViewHolder> {

    private ArrayList<Period> list;
    private LayoutInflater layoutInflater;

    public PeriodAdapter(ArrayList<Period> list, Context context) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PeriodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeriodViewHolder(layoutInflater.inflate(R.layout.item_period, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PeriodViewHolder holder, int position) {
        final Period period = list.get(position);
        holder.tvPeriodTitle.setText(period.getTitle());
        holder.tvPeriodContent.setText(period.getContent());
        holder.vDivider.setVisibility(position == list.size() - 1 ? View.INVISIBLE : View.VISIBLE);

        holder.imgSelected.setVisibility(period.isSelected() ? View.VISIBLE : View.INVISIBLE);

        holder.layoutPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelected();
                period.setSelected(true);
                notifyDataSetChanged();
            }
        });
    }

    private void clearListSelected() {
        for (Period item : list) {
            item.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class PeriodViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSelected;
        private TextView tvPeriodTitle;
        private TextView tvPeriodContent;
        private RelativeLayout layoutPeriod;
        private View vDivider;

        public PeriodViewHolder(View itemView) {
            super(itemView);
            imgSelected = itemView.findViewById(R.id.imgSelected);
            tvPeriodTitle = itemView.findViewById(R.id.tvPeriodTitle);
            tvPeriodContent = itemView.findViewById(R.id.tvPeriodContent);
            layoutPeriod = itemView.findViewById(R.id.layoutPeriod);
            vDivider = itemView.findViewById(R.id.vDivider);
        }
    }
}
