package com.ekakashi.greenhouse.features.revision_history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.database.model_server_response.RevisionHistory;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RevisionHistory> historyList;

    public HistoryAdapter(Context context, List<RevisionHistory> historyList) {
        this.mContext = context;
        this.historyList = historyList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RevisionHistory history = historyList.get(position);
        HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
        if (history.getEkUser() != null) {
            viewHolder.tvAuthorRecipe.setText(history.getEkUser().getName());
/*
            viewHolder.tvHistoryRecipe.setText(App.getsInstance().getString(R.string.txt_created) + " " + Utils.getDateTimeString(history.createdAt, DateTimeNow.dd_MM_yyyy));
*/
            if (position == 0) {
                viewHolder.tvSubtitleAuthor.setText(mContext.getString(R.string.txt_created_by) + " " + history.getEkUser().getNickName());
            } else {
                viewHolder.tvSubtitleAuthor.setText(mContext.getString(R.string.txt_copied_by) + " " + history.getEkUser().getNickName());
            }

            //Utils.getDateTimeString(history.createdAt, DateTimeNow.dd_MM_yyyy)
            viewHolder.tvHistoryRecipe.setText(DateTimeNow.parseDateStringToLocalDateString(history.getCreatedAt(), DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, mContext.getString(R.string.format_date_time)));
        } else {
            viewHolder.tvAuthorRecipe.setText("");
            viewHolder.tvHistoryRecipe.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return historyList == null ? 0 : historyList.size();
    }

    private static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAuthorRecipe;
        private TextView tvHistoryRecipe;
        private TextView tvSubtitleAuthor;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            tvAuthorRecipe = itemView.findViewById(R.id.tvAuthorRecipe);
            tvHistoryRecipe = itemView.findViewById(R.id.tvHistoryRecipe);
            tvSubtitleAuthor = itemView.findViewById(R.id.tvSubtitleAuthor);
        }
    }
}
