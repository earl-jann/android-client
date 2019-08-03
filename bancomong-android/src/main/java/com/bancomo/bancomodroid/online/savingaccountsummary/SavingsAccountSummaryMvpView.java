package com.bancomo.bancomodroid.online.savingaccountsummary;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.savings.SavingsAccountWithAssociations;

/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface SavingsAccountSummaryMvpView extends MvpView {

    void showSavingAccount(SavingsAccountWithAssociations savingsAccountWithAssociations);

    void showSavingsActivatedSuccessfully(GenericResponse genericResponse);

    void showFetchingError(int errorMessage);

    void showFetchingError(String errorMessage);
}
