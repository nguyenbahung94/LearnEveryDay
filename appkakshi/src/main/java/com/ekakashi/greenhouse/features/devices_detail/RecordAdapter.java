package com.ekakashi.greenhouse.features.devices_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecord;

import java.util.List;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.mViewHolder> {
    private Context context;
    private List<ObjectRecord> objectRecordList;

    RecordAdapter(Context context, List<ObjectRecord> objectRecordList) {
        this.context = context;
        this.objectRecordList = objectRecordList;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operation_record, parent, false));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        ObjectRecord record = objectRecordList.get(position);
        holder.tvName.setText(record.getName());
        Glide.with(context).load(record.getImage()).into(holder.imvAvatar);
        holder.tvTime.setText(record.getDateTime());
        holder.tvShowStatus.setText(record.getStatus());
        String openPercent = context.getString(R.string.txt_devices_percent, record.getDegree());
        holder.tvShowOpenClose.setText(openPercent);
    }

    @Override
    public int getItemCount() {
        return objectRecordList.size();
    }

    static class mViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvAvatar;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvShowStatus;
        private TextView tvShowOpenClose;

        mViewHolder(View itemView) {
            super(itemView);
            imvAvatar = itemView.findViewById(R.id.imvAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvShowStatus = itemView.findViewById(R.id.tvShowStatus);
            tvShowOpenClose = itemView.findViewById(R.id.tvShowOpenClose);
        }
    }
}
