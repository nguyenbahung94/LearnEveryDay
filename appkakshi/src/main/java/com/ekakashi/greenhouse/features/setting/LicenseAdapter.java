package com.ekakashi.greenhouse.features.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectLicense;

import java.util.List;

/**
 * Created by ptloc on 3/26/2018.
 */

public class LicenseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObjectLicense> mLicenseList;

    public LicenseAdapter(List<ObjectLicense> licenses) {
        this.mLicenseList = licenses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LicenseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license_information, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final LicenseViewHolder viewHolder = (LicenseViewHolder) holder;
        ObjectLicense license = mLicenseList.get(position);

        viewHolder.tvTitle.setText(String.valueOf(position + 1) + ". " + license.getTitle());
        viewHolder.tvContent.setText(license.getContent());
    }

    @Override
    public int getItemCount() {
        return mLicenseList == null ? 0 : mLicenseList.size();
    }


    public static class LicenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;

        LicenseViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent =itemView.findViewById(R.id.tvContent);
        }

    }
}
