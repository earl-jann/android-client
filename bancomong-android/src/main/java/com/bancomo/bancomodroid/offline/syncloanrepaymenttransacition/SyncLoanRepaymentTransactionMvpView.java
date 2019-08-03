package com.bancomo.bancomodroid.offline.syncloanrepaymenttransacition;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.PaymentTypeOption;
import com.bancomo.objects.accounts.loan.LoanRepaymentRequest;

import java.util.List;

/**
 * Created by Rajan Maurya on 28/07/16.
 */
public interface SyncLoanRepaymentTransactionMvpView extends MvpView {

    void showLoanRepaymentTransactions(List<LoanRepaymentRequest> loanRepaymentRequests);

    void showPaymentTypeOption(List<PaymentTypeOption> paymentTypeOptions);

    void showOfflineModeDialog();

    void showPaymentSubmittedSuccessfully();

    void showPaymentFailed(String errorMessage);

    void showLoanRepaymentUpdated(LoanRepaymentRequest loanRepaymentRequest);

    void showLoanRepaymentDeletedAndUpdateLoanRepayment(
            List<LoanRepaymentRequest> loanRepaymentRequests);

    void showError(int stringId);
}
