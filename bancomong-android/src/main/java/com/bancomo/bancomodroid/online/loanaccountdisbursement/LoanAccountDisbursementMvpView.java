package com.bancomo.bancomodroid.online.loanaccountdisbursement;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.templates.loans.LoanTransactionTemplate;

/**
 * Created by Rajan Maurya on 8/6/16.
 */
public interface LoanAccountDisbursementMvpView extends MvpView {

    void showUserInterface();

    void showDisbursementDate(String date);

    void showLoanTransactionTemplate(LoanTransactionTemplate loanTransactionTemplate);

    void showDisburseLoanSuccessfully(GenericResponse genericResponse);

    void showError(String s);

    void showError(int errorMessage);
}
