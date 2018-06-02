package com.example.nbhung94.customrecyclerview.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nbhung94.customrecyclerview.R;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.ItemTouchHelperAdapter;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.ItemTouchHelperViewHolder;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.OnItemCLickListener;
import com.example.nbhung94.customrecyclerview.recyclerview.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by nbhung on 11/15/2017.
 */

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHodel> implements ItemTouchHelperAdapter {
    private List<String> mList;
    private Context context;
    private OnStartDragListener onStartDragListener;
    private OnItemCLickListener itemCLickListener;

    public recycleViewAdapter(List<String> mList, Context context, OnStartDragListener onStartDragListener) {
        this.mList = mList;
        this.context = context;
        this.onStartDragListener = onStartDragListener;
    }

    public void setiItemClick(OnItemCLickListener itemCLickListener) {
        this.itemCLickListener = itemCLickListener;
    }

    @Override

    public ViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHodel(v);
    }

    @Override
    public void onBindViewHolder(final ViewHodel holder, final int position) {
        holder.tvContent.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLickListener.onItemClick(v,holder.getAdapterPosition());
            }
        });

        //start a drag whenever the handle view it touched
        holder.mCardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE) {
                    onStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
     //   Collections.swap(mList, fromPosition, toPosition);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHodel extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView tvContent;
        CardView mCardView;

        private ViewHodel(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardName);
            tvContent = itemView.findViewById(R.id.tvCarView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
