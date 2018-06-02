package com.ekakashi.greenhouse.custom_gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekakashi.greenhouse.R;

import java.io.File;
import java.util.List;

/**
 * Created by nbhung on 3/27/2018.
 */

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {

    private Context context;
    private List<String> allList;
    private List<String> listPosition;
    private int numSelect;

    public AdapterRecycleView(Context context, List<String> allList, int int_position, List<String> listPosition, int numSelect) {
        this.context = context;
        this.allList = allList;
        this.listPosition = listPosition;
        this.numSelect = numSelect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final boolean[] found = {false};
        Glide.with(context).load("file://" + allList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_image);
        holder.llContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (String ss : listPosition) {
                    if (ss.equals(allList.get(position))) {
                        found[0] = true;
                        break;
                    }
                }
                if (found[0]) {
                    found[0] = false;
                    listPosition.remove(allList.get(position));
                    holder.imvSelect.setVisibility(View.INVISIBLE);
                    holder.llContain.setBackgroundColor(Color.TRANSPARENT);
                    holder.llContain.setAlpha(1);
                } else {
                    if (listPosition.size() < numSelect) {
                        if (checkImageSize10MB(allList.get(position))) {
                            holder.llContain.setBackgroundColor(Color.BLACK);
                            holder.llContain.setAlpha(0.5f);
                            listPosition.add(allList.get(position));
                            holder.imvSelect.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, R.string.image_10mb, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.image_limit), Toast.LENGTH_LONG).show();
                    }

                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return allList == null ? 0 : allList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvSelect;
        private ImageView iv_image;
        private LinearLayout llContain;

        public ViewHolder(View itemView) {
            super(itemView);
            imvSelect = itemView.findViewById(R.id.imvSelect);
            iv_image = itemView.findViewById(R.id.iv_image);
            llContain = itemView.findViewById(R.id.llContain);
        }

    }
    private boolean checkImageSize10MB(String path) {
        File file = new File(path);
        long length = file.length() / (1048576);
        return length < 10;
    }
}
