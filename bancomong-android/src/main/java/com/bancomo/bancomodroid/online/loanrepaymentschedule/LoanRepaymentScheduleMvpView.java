package com.bancomo.bancomodroid.online.loanrepaymentschedule;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface LoanRepaymentScheduleMvpView extends MvpView {

    void showLoanRepaySchedule(LoanWithAssociations loanWithAssociations);

    void showFetchingError(String s);
}
