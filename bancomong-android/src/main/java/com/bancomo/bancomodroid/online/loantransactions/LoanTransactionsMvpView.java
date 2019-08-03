package com.bancomo.bancomodroid.online.loantransactions;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;

/**
 * Created by Rajan Maurya on 7/6/16.
 */
public interface LoanTransactionsMvpView extends MvpView {

    void showLoanTransaction(LoanWithAssociations loanWithAssociations);

    void showFetchingError(String s);
}
