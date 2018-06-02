package com.bat.firstcom.supervisorapp.presentation.editoutlet;

import com.bat.firstcom.supervisorapp.datalayer.model.ChangeOutletDatum;
import com.bat.firstcom.supervisorapp.presentation.base.BaseView;

/**
 * Created by Tung Phan on 5/27/2017.
 */

public interface EditOutletView extends BaseView {

    void updateLayoutFrom(ChangeOutletDatum changeOutletDatum);

    void postChangeOutletSuccessFully();

}
