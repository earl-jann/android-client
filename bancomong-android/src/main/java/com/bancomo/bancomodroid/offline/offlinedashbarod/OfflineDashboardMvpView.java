package com.bancomo.bancomodroid.offline.offlinedashbarod;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.LoanRepaymentRequest;
import com.bancomo.objects.accounts.savings.SavingsAccountTransactionRequest;
import com.bancomo.objects.client.ClientPayload;
import com.bancomo.objects.group.GroupPayload;

import java.util.List;

/**
 * Created by Rajan Maurya on 20/07/16.
 */
public interface OfflineDashboardMvpView extends MvpView {

    void showClients(List<ClientPayload> clientPayloads);

    void showGroups(List<GroupPayload> groupPayloads);

    void showLoanRepaymentTransactions(List<LoanRepaymentRequest> loanRepaymentRequests);

    void showSavingsAccountTransaction(List<SavingsAccountTransactionRequest> transactions);

    void showNoPayloadToShow();

    void showError(int s);
}
