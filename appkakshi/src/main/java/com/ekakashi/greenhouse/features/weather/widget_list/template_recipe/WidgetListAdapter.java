package com.ekakashi.greenhouse.features.weather.widget_list.template_recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.ItemTouchHelperAdapter;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.ItemTouchHelperViewHolder;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnItemCLickListener;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nbhung on 3/13/2018.
 */

public class WidgetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private final List<Data> objectDefaultTemplateList;
    private final OnStartDragListener mDragListenerListener;
    private List<Data> dataList = new ArrayList<>();
    private Context context;
    public boolean isEdit = false;
    private OnItemCLickListener onItemCLickListener;

    public WidgetListAdapter(Context context, List<Data> objectDefaultTemplates, OnStartDragListener mDragListenerListener) {
        this.mDragListenerListener = mDragListenerListener;
        this.context = context;
        this.objectDefaultTemplateList = objectDefaultTemplates;
    }

    @Override
    public int getItemViewType(int position) {
        return objectDefaultTemplateList.get(position).getGraph() == null ? 0 : 1;
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
      /*  switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget_layout, parent, false);
                return new ViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_graph_layout, parent, false);
                return new ViewHolder(view);
        }*/
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
       // if (getItemViewType(position) == 0) {
            viewHolder.imvRemove.setVisibility(View.GONE);
            viewHolder.imvShow.setVisibility(View.VISIBLE);
            viewHolder.imvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCLickListener.deleteItem(v, position);
                }
            });

            if (isEdit) {
                viewHolder.imv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                            mDragListenerListener.onStartDrag(viewHolder);
                        }
                        return false;
                    }
                });
                viewHolder.imvRemove.setVisibility(View.GONE);
                viewHolder.imvShow.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imvRemove.setVisibility(View.GONE);
                viewHolder.imvShow.setVisibility(View.GONE);
            }
            viewHolder.imv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCLickListener.onItemClick(view, position);
                }
            });
            if (objectDefaultTemplateList.get(position).getIconUrl() != null) {
                Glide.with(context).load(objectDefaultTemplateList.get(position).getIconUrl()).into(((ViewHolder) holder).imv);
            }
            if (objectDefaultTemplateList.get(position).getName() != null) {
                viewHolder.tvNameOfItem.setText(objectDefaultTemplateList.get(position).getName());
            }
       /* } else {
            // TODO: code graph here
        }*/

    }

    @Override
    public int getItemCount() {
        return objectDefaultTemplateList == null ? 0 : objectDefaultTemplateList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(objectDefaultTemplateList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(objectDefaultTemplateList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        objectDefaultTemplateList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private ImageView imvShow;
        private ImageView imv;
        private ImageView imvRemove;
        private TextView tvNameOfItem;

        public ViewHolder(View itemView) {
            super(itemView);

            imv = itemView.findViewById(R.id.imv);
            imvRemove = itemView.findViewById(R.id.imvRemove);
            tvNameOfItem = itemView.findViewById(R.id.tvNameOfItem);
            imvShow = itemView.findViewById(R.id.imvShow);
        }


        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }
}
