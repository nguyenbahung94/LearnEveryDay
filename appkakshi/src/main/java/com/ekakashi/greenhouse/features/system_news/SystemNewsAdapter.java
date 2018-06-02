package com.ekakashi.greenhouse.features.system_news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.DateTimeNow;

import java.util.LinkedHashMap;

/**
 * Created by nquochuy on 1/25/2018.
 */

public class SystemNewsAdapter extends RecyclerView.Adapter<SystemNewsAdapter.SystemNewsViewHolder> {

    private LinkedHashMap<Integer, SystemNews.Data> systemNewsList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    SystemNewsAdapter(Context context, LinkedHashMap<Integer, SystemNews.Data> systemNewsList) {
        this.context = context;
        this.systemNewsList = systemNewsList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SystemNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_system_news, parent, false);
        return new SystemNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemNewsViewHolder holder, int position) {
        final SystemNews.Data item = getByIndex(position);
        holder.tvTitle.setText(item.getOutline());
        String date = DateTimeNow.parseDateStringToLocalDateString(item.getCreatedAt(),
                DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, context.getString(R.string.format_date_time));
        holder.tvDate.setText(date);

        holder.layoutSystemNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotificationDetailActivity.class);
                intent.putExtra(SystemNewsActivity.SYSTEM_NEWS, item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return systemNewsList == null ? 0 : systemNewsList.size();
    }

    private SystemNews.Data getByIndex(int index) {
        return (SystemNews.Data) systemNewsList.values().toArray()[index];
    }


    void setNews(LinkedHashMap<Integer, SystemNews.Data> list) {
        this.systemNewsList = list;
        notifyDataSetChanged();
    }

    static class SystemNewsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDate;
        private RelativeLayout layoutSystemNews;

        SystemNewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            layoutSystemNews = itemView.findViewById(R.id.layoutSystemNews);
        }
    }
}
