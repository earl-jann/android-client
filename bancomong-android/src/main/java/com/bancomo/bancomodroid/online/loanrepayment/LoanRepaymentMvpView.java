package com.bancomo.bancomodroid.online.loanrepayment;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.LoanRepaymentResponse;
import com.bancomo.objects.templates.loans.LoanRepaymentTemplate;

/**
 * Created by Rajan Maurya on 8/6/16.
 */
public interface LoanRepaymentMvpView extends MvpView {

    void showLoanRepayTemplate(LoanRepaymentTemplate loanRepaymentTemplate);

    void showPaymentSubmittedSuccessfully(LoanRepaymentResponse loanRepaymentResponse);

    void checkLoanRepaymentStatusInDatabase();

    void showLoanRepaymentExistInDatabase();

    void showLoanRepaymentDoesNotExistInDatabase();

    void showError(int errorMessage);
}
