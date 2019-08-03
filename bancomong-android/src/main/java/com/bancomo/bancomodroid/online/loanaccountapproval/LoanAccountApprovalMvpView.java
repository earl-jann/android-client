package com.bancomo.bancomodroid.online.loanaccountapproval;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;

/**
 * Created by Rajan Maurya on 8/6/16.
 */
public interface LoanAccountApprovalMvpView extends MvpView {

    void showUserInterface();

    void showLoanApproveSuccessfully(GenericResponse genericResponse);

    void showLoanApproveFailed(String s);
}
