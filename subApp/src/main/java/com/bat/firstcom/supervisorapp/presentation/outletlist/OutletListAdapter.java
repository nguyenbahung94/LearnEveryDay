package com.bat.firstcom.supervisorapp.presentation.outletlist;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.common.Strings;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.OutletDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class OutletListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OutletDatum> outletData;
    private FlowType flowType;
    private Context context;
    private CommonListAdapterListener listener;
    private boolean isEditOutlet = false;

    public OutletListAdapter(Context context, CommonListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setEditOutlet(boolean isEditOutlet) {
        this.isEditOutlet = isEditOutlet;
    }

    public void initAdapter(FlowType flowType, List<OutletDatum> outletData) {
        this.flowType = flowType;
        this.outletData = outletData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outlet_item_in_recyclerview, parent, false);
        return new OutletListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindOutletViewHolder((OutletListViewHolder) holder, position);
    }

    private void bindOutletViewHolder(OutletListViewHolder holder, int position) {
        OutletDatum outletDatum = outletData.get(position);
        holder.outletName.setText("("
                +outletDatum.getStartTime()
                +"-"
                + outletDatum.getEndTime()
                +")"
                + outletDatum.getOutletName());
        holder.outletAddress.setText(getAddressFrom(outletDatum));
        holder.itemParentView.setOnClickListener(v -> {
            if (flowType != null && flowType == FlowType.CHECKING) {
                processOutletItemClick(outletDatum, flowType);
            } else {
                listener.startTakingPictureActivity(outletDatum, flowType);
            }
        });
    }

    //get address of the outlet from OutletDatum with address1, address2, address3
    private String getAddressFrom(OutletDatum outletDatum) {
        String result = Strings.EMPTY;
        if (outletDatum.getAddress1() != null && !outletDatum.getAddress1()
                .equalsIgnoreCase(Strings.EMPTY)) {
            result += outletDatum.getAddress1();
        }
        result = Utils.addStringWithComma(result, outletDatum.getAddress2());
        result = Utils.addStringWithComma(result, outletDatum.getAddress3());
        return result;
    }

    private void processOutletItemClick(OutletDatum outletDatum, FlowType flowType) {
        if (isEditOutlet) { // if item clicked to edit outlet
            listener.startEditOutletActivity(outletDatum);
        } else { // start taking picture activity
            listener.startTakingPictureActivity(outletDatum, flowType);
        }
    }

    @Override
    public int getItemCount() {
        return outletData.size();
    }

    public class OutletListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.outletName)
        AppCompatTextView outletName;
        @BindView(R.id.outletAddress)
        AppCompatTextView outletAddress;
        @BindView(R.id.itemParentView)
        LinearLayout itemParentView;

        public OutletListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface CommonListAdapterListener {

        void startTakingPictureActivity(OutletDatum outletDatum, FlowType flowType);

        void startEditOutletActivity(OutletDatum outletDatum);
    }
}
