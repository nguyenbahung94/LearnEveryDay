package com.bat.firstcom.supervisorapp.di.component;

import com.bat.firstcom.supervisorapp.di.module.NetworkModule;
import com.bat.firstcom.supervisorapp.presentation.changepassword.ChangePasswordActivity;
import com.bat.firstcom.supervisorapp.presentation.editoutlet.EditOutletActivity;
import com.bat.firstcom.supervisorapp.presentation.outletlist.OutletListActitivy;
import com.bat.firstcom.supervisorapp.presentation.pstlist.PSTListActitivy;
import com.bat.firstcom.supervisorapp.presentation.login.LoginActivity;
import com.bat.firstcom.supervisorapp.presentation.marking.MarkingActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tung phan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(PSTListActitivy PSTListActitivy);
    void inject(OutletListActitivy outletListActitivy);
    void inject(MarkingActivity markingActivity);
    void inject(EditOutletActivity editOutletActivity);
    void inject(ChangePasswordActivity changePasswordActivity);

}
