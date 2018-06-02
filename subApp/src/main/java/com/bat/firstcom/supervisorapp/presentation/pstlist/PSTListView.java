package com.bat.firstcom.supervisorapp.presentation.pstlist;

import com.bat.firstcom.supervisorapp.datalayer.model.PSTDatum;
import com.bat.firstcom.supervisorapp.presentation.base.BaseView;

import java.util.List;

/**
 * Created by Tung Phan on 5/27/2017.
 */

public interface PSTListView extends BaseView {

    void updateCoachingPSTListAdapter(List<PSTDatum> pstData);

    void updateCheckingPSTListAdapter(List<PSTDatum> pstData);

}
