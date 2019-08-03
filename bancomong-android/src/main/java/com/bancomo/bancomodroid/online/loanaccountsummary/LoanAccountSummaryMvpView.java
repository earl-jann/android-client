package com.bancomo.bancomodroid.online.loanaccountsummary;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;

/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface LoanAccountSummaryMvpView extends MvpView {

    void showLoanById(LoanWithAssociations loanWithAssociations);

    void showFetchingError(String s);
}
