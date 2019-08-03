package com.bancomo.bancomodroid.online.savingsaccountapproval;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;

/**
 * Created by Rajan Maurya on 09/06/16.
 */
public interface SavingsAccountApprovalMvpView extends MvpView {

    void showUserInterface();

    void showSavingAccountApprovedSuccessfully(GenericResponse genericResponse);

    void showError(String s);
}
