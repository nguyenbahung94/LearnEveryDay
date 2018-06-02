package com.example.nbhung94.tinderavatarview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.athbk.avatarview.adapter.ItemVH;
import com.athbk.avatarview.adapter.TinderRVAdapter;
import com.example.nbhung94.tinderavatarview.Interface.IFishDrag;
import com.example.nbhung94.tinderavatarview.Model.Item;
import com.example.nbhung94.tinderavatarview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 12/6/2017.
 */

public class CustomAdapter extends TinderRVAdapter<Item, CustomAdapter.CustomVIewHolder> {
    private List<Item> lstData;
    private Context context;
    private IFishDrag iFishDrag;

    public CustomAdapter(Context context, List<Item> lstData) {
        this.lstData = lstData;
        this.context = context;
    }

    public void setiFishDrag(IFishDrag iFishDrag) {
        this.iFishDrag = iFishDrag;
    }

    @Override
    protected List<Item> getListItem() {
        return lstData;
    }

    @Override
    protected CustomVIewHolder onCreateItemViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        return new CustomVIewHolder(item);
    }

    @Override
    protected void onBindItemViewHolder(CustomVIewHolder vh, int i) {
        Picasso.with(context).load(lstData.get(i).getImgLink()).into(vh.imageView);
        vh.textView.setTextColor(lstData.get(i).getIndex());
    }

    class CustomVIewHolder extends ItemVH {
        private FrameLayout frameLayout;
        private ImageView imageView;
        private TextView textView;
        private IFishDrag iFishDrag;

        public CustomVIewHolder(View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.layout);
            imageView = frameLayout.findViewById(R.id.imageView);
            textView = frameLayout.findViewById(R.id.textView);
        }

        @Override
        public void onItemSelected() {
            super.onItemSelected();
            frameLayout.setAlpha(0.5f);
        }

        @Override
        public void onItemClear() {
            super.onItemClear();
            frameLayout.setAlpha(1f);
            if (iFishDrag != null) {
                iFishDrag.updateListData((ArrayList<Item>) getListItem());
            }
        }
    }
}


