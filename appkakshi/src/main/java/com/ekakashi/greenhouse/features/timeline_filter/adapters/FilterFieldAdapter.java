package com.ekakashi.greenhouse.features.timeline_filter.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.FilterInterface;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/6/2018.
 */

public class FilterFieldAdapter extends RecyclerView.Adapter<FilterFieldAdapter.FilterSelectViewHolder> {

    private ArrayList<FilterField> filterFields;
    private FilterInterface.UnselectedAll unselectAllInterface;
    private LayoutInflater mLayoutInflater;
    private Bitmap bmChecked, bmUnchecked;

    public FilterFieldAdapter(ArrayList<FilterField> filterFields, FilterInterface.UnselectedAll unselectAllInterface, Context context) {
        this.filterFields = filterFields;
        this.unselectAllInterface = unselectAllInterface;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.bmChecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_checked);
        this.bmUnchecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_uncheck);
    }

    @Override
    public FilterSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_filter_selected, parent, false);
        return new FilterSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilterSelectViewHolder holder, final int position) {
        final FilterField item = filterFields.get(position);
        holder.tvFilter.setText(item.getPlaceName());
        holder.imgFilter.setImageBitmap(item.isSelected() ? bmChecked : bmUnchecked);

        holder.layoutFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(!item.isSelected());
                holder.imgFilter.setImageBitmap(item.isSelected() ? bmChecked : bmUnchecked);
                unselectAllInterface.onUnselectedAllCallback();
            }
        });
        if (filterFields.size() == 1) {
            holder.lineTop.setVisibility(View.VISIBLE);
            holder.lineDivider.setVisibility(View.GONE);
            holder.lineBottom.setVisibility(View.VISIBLE);
        } else {
            if (position == 0) {
                holder.lineTop.setVisibility(View.VISIBLE);
                holder.lineDivider.setVisibility(View.VISIBLE);
                holder.lineBottom.setVisibility(View.GONE);
            } else if (position == filterFields.size() - 1) {
                holder.lineTop.setVisibility(View.GONE);
                holder.lineDivider.setVisibility(View.GONE);
                holder.lineBottom.setVisibility(View.VISIBLE);
            } else {
                holder.lineTop.setVisibility(View.GONE);
                holder.lineDivider.setVisibility(View.VISIBLE);
                holder.lineBottom.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return filterFields == null ? 0 : filterFields.size();
    }

    public ArrayList<FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(ArrayList<FilterField> filterFields) {
        this.filterFields = filterFields;
    }

    static class FilterSelectViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFilter;
        private TextView tvFilter;
        private RelativeLayout layoutFilter;
        private View lineTop;
        private View lineDivider;
        private View lineBottom;

        FilterSelectViewHolder(View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.imgFilter);
            tvFilter = itemView.findViewById(R.id.tvFilterSelected);
            layoutFilter = itemView.findViewById(R.id.layoutFilter);
            lineTop = itemView.findViewById(R.id.line_top);
            lineDivider = itemView.findViewById(R.id.line_divider);
            lineBottom = itemView.findViewById(R.id.line_bottom);
        }
    }

    public void onDestroyData() {
        unselectAllInterface = null;
        mLayoutInflater = null;
        filterFields = null;
        bmChecked = null;
        bmUnchecked = null;
    }
}
