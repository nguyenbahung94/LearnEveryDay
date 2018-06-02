package com.ekakashi.greenhouse.features.device_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.dialog_fragment.CustomAlertDialog;
import com.ekakashi.greenhouse.database.dao.DeviceObject;

import java.util.List;


public class AdapterDevice extends RecyclerView.Adapter<AdapterDevice.MyViewHolder> implements View.OnClickListener {
    private List<DeviceObject> list;
    private Context context;
    private LinkedDeviceInterface.OnItemClick onItemClickListener;
    private boolean showItemDelete = false;

    AdapterDevice(List<DeviceObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    void setItemCLickListener(LinkedDeviceInterface.OnItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_device, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (position == list.size()) {
            holder.relContent.setVisibility(View.GONE);
            holder.linAddMore.setVisibility(View.VISIBLE);
            holder.linAddMore.setOnClickListener(this);
            holder.linAddMore.setTag(position);
            return;
        } else {
            holder.relContent.setVisibility(View.VISIBLE);
            holder.linAddMore.setVisibility(View.GONE);
        }
        final DeviceObject item = list.get(position);
        if (showItemDelete) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
        }
        Glide.with(context).load(item.snIconPath).into(holder.imgCircleItem);
        holder.imgDelete.setOnClickListener(this);
        holder.imgDelete.setTag(position);
        holder.tvDeviceName.setText(item.snName);
        holder.tvCodeDevice.setText(item.snDeviceTypeId);
        //Utils.getDateTimeString(item.registrationDate, context.getString(R.string.format_date_time))
        holder.tvDate.setText(DateTimeNow.parseDateStringToLocalDateString(item.registrationDate, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, context.getString(R.string.format_date_time)));
        holder.relContent.setOnClickListener(this);
        holder.relContent.setTag(position);
    }

    public List<DeviceObject> getList() {
        return list;
    }

    public void setList(List<DeviceObject> list) {
        this.list = list;
    }

    void setShowItemDeleteTrue() {
        showItemDelete = true;
        notifyDataSetChanged();
    }

    void setShowItemDeleteFalse() {
        showItemDelete = false;
        notifyDataSetChanged();
    }

    private void onShowAlert(final int position) {
        CustomAlertDialog.customDialogShow(context, context.getString(R.string.device_delete_confirm), new YesOrNo() {
            @Override
            public void clickYes() {
                if (onItemClickListener != null) {
                    onItemClickListener.clickDeleteOk(position);
                }
            }

            @Override
            public void clickNo() {
                if (onItemClickListener != null) {
                    onItemClickListener.clickDeleteCancel(position);
                }
            }
        });
    }

    @Override
    public void onClick(final View v) {
        if (onItemClickListener == null) {
            return;
        }
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.imgDeleteDevice:
                this.onShowAlert(position);
                break;
            case R.id.rel_content:
            case R.id.lin_add_more:
                onItemClickListener.onItemClick(v, position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size() + 1);
    }

    /**
     * called in onDestroy of Activity.
     */
    void clearData() {
        context = null;
        onItemClickListener = null;
        list = null;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linAddMore;
        private RelativeLayout relContent;
        private TextView tvCodeDevice;
        private TextView tvDate;
        private ImageView imgCircleItem;
        private ImageView imgDelete;
        private TextView tvDeviceName;


        private MyViewHolder(View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            imgCircleItem = itemView.findViewById(R.id.imgCircleItem);
            tvCodeDevice = itemView.findViewById(R.id.tvCodeId);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgDelete = itemView.findViewById(R.id.imgDeleteDevice);
            linAddMore = itemView.findViewById(R.id.lin_add_more);
            relContent = itemView.findViewById(R.id.rel_content);
        }
    }
}
