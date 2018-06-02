package com.ekakashi.greenhouse.custom_gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekakashi.greenhouse.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 3/26/2018.
 */

public class GridViewAdapter extends ArrayAdapter<Model_images> {

    private Context context;
    private ViewHolder viewHolder;
    private ArrayList<Model_images> al_menu = new ArrayList<>();
    private int int_position;
    private List<String> listPossition;


    public GridViewAdapter(Context context, ArrayList<Model_images> al_menu, int int_position) {
        super(context, R.layout.adapter_photosfolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;

    }

    public void setListPossition(List<String> listPossition) {
        this.listPossition = listPossition;
    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.get(int_position).getAl_imagepath().size() + "");
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAl_imagepath().size() > 0) {
            return al_menu.get(int_position).getAl_imagepath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final boolean[] found = {false};
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photosfolder, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.imvSelect = (ImageView) convertView.findViewById(R.id.imvSelect);
            viewHolder.llContain = (LinearLayout) convertView.findViewById(R.id.llContain);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);
        Glide.with(context).load("file://" + al_menu.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);
        viewHolder.llContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listImage = al_menu.get(int_position).getAl_imagepath();
                for (String ss : listPossition) {
                    if (ss.equals(listImage.get(position))) {
                        found[0] = true;
                        break;
                    }
                }
                if (found[0]) {
                    found[0] = false;
                    listPossition.remove(listImage.get(position));
                    viewHolder.imvSelect.setVisibility(View.INVISIBLE);
                    notifyDataSetChanged();
                } else {
                    if (checkImageSize10MB(listImage.get(position))) {
                        listPossition.add(listImage.get(position));
                        viewHolder.imvSelect.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "this image have size more than 10MB", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        return convertView;

    }

    private static class ViewHolder {
        private TextView tv_foldern, tv_foldersize;
        private ImageView iv_image, imvSelect;
        private LinearLayout llContain;

    }

    private boolean checkImageSize10MB(String path) {
        File file = new File(path);
        long length = file.length() / (1024);
        if (length < 10240) {
            return true;
        } else {
            return false;
        }
    }

}
