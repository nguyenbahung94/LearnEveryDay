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
import com.ekakashi.greenhouse.common.Utils;

import java.io.File;
import java.util.List;

/**
 * Created by nbhung on 3/27/2018.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder> {

    private Context context;
    private List<String> listAllImageInFolder;
    private List<String> listSelected;
    private int imagePickType;
    private SelectPhotosCallback mCallback;

    public PhotoViewAdapter(Context context, List<String> listAllImageInFolder, List<String> listSelected, int imagePickType, SelectPhotosCallback mCallback) {
        this.context = context;
        this.listAllImageInFolder = listAllImageInFolder;
        this.listSelected = listSelected;
        this.imagePickType = imagePickType;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load("file://" + listAllImageInFolder.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_image);
        holder.llContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePickType == Utils.Constant.PICK_ONE_IMAGE) {
                    selectOneImage(holder, position);
                } else {
                    selectMultiImage(holder, position);
                }
            }

        });
    }

    private void selectOneImage(final ViewHolder holder, int position) {
        if (checkImageSize10MB(listAllImageInFolder.get(position))) {
            holder.llContain.setBackgroundColor(Color.BLACK);
            holder.llContain.setAlpha(0.5f);
            listSelected.add(listAllImageInFolder.get(position));
            holder.imvSelect.setVisibility(View.VISIBLE);
            mCallback.onSelectOneImage();
        } else {
            Toast.makeText(context, R.string.image_10mb, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectMultiImage(final ViewHolder holder, int position) {
        boolean isSelected = false;
        for (String path : listSelected) {
            if (path.equals(listAllImageInFolder.get(position))) {
                isSelected = true;
                break;
            }
        }
        if (isSelected) {
            listSelected.remove(listAllImageInFolder.get(position));
            holder.imvSelect.setVisibility(View.INVISIBLE);
            holder.llContain.setBackgroundColor(Color.TRANSPARENT);
            holder.llContain.setAlpha(1);
        } else {
            if (listSelected.size() < 3) {
                if (checkImageSize10MB(listAllImageInFolder.get(position))) {
                    holder.llContain.setBackgroundColor(Color.BLACK);
                    holder.llContain.setAlpha(0.5f);
                    listSelected.add(listAllImageInFolder.get(position));
                    holder.imvSelect.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(context, R.string.image_10mb, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, R.string.image_limit, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return listAllImageInFolder == null ? 0 : listAllImageInFolder.size();
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
        if (length < 10) {
            return true;
        } else {
            return false;
        }
    }
}
