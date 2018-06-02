package com.bat.firstcom.supervisorapp.presentation.outletlist;

import com.bat.firstcom.supervisorapp.datalayer.model.OutletDatum;
import com.bat.firstcom.supervisorapp.presentation.base.BaseView;

import java.util.List;

/**
 * Created by Tung Phan on 5/27/2017.
 */

public interface OutletListView extends BaseView {

    void updateOutletListAdapter(List<OutletDatum> outletData);

}
