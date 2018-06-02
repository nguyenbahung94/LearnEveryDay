package com.bat.firstcom.supervisorapp.presentation.pstlist;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.datalayer.model.PSTDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class PSTListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PSTDatum> pstData;
    private FlowType flowType;
    private Context context;
    private CommonListAdapterListener listener;

    public PSTListAdapter(Context context, CommonListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void initAdapter(FlowType flowType
            , List<PSTDatum> pstData) {
        this.flowType = flowType;
        this.pstData = pstData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pst_item_in_recylerview, parent, false);

        return new PSTListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindPSTViewHolder((PSTListViewHolder) holder, position);
    }

    private void bindPSTViewHolder(PSTListViewHolder holder, int position) {
        PSTDatum pstDatum = pstData.get(position);
        holder.pstName.setText(pstDatum.getName());
        if (flowType != null && flowType == FlowType.CHECKING) {
            holder.btnscoreTimes.setText(String.valueOf(pstDatum.getNumberOfTimes()));
        } else {
            holder.btnScore.setText(String.valueOf(pstDatum.getScore()));
            holder.scoreTimes.setText(String.valueOf(pstDatum.getNumberOfTimes()));
        }
        holder.itemParentView.setOnClickListener(v ->
                listener.showPickedOptionDialog(pstDatum));
    }

    @Override
    public int getItemCount() {
        return pstData.size();
    }

    public class PSTListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pstName)
        AppCompatTextView pstName;
        @BindView(R.id.btnScore)
        AppCompatButton btnScore;
        @BindView(R.id.scoreTimes)
        AppCompatTextView scoreTimes;
        @BindView(R.id.btnscoreTimes)
        AppCompatButton btnscoreTimes;
        @BindView(R.id.itemParentView)
        LinearLayout itemParentView;

        public PSTListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initViewsVisibility();
        }

        private void initViewsVisibility() {
            if (flowType != null && flowType == FlowType.CHECKING) {
                setBtnscoreTimesVisibility(View.VISIBLE);
                setScoresTimeVisibility(View.GONE);
                setBtnScoreVisibility(View.GONE);
            } else {
                setBtnscoreTimesVisibility(View.GONE);
                setScoresTimeVisibility(View.VISIBLE);
                setBtnScoreVisibility(View.VISIBLE);
            }
        }

        private void setScoresTimeVisibility(int visibility) {
            scoreTimes.setVisibility(visibility);
        }

        private void setBtnScoreVisibility(int visibility) {
            btnScore.setVisibility(visibility);
        }

        private void setBtnscoreTimesVisibility(int visibility) {
            btnscoreTimes.setVisibility(visibility);
        }
    }

    public interface CommonListAdapterListener {

        void showPickedOptionDialog(PSTDatum pstDatum);

    }
}
