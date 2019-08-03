package com.bancomo.bancomodroid.online.savingaccounttransaction;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.savings.SavingsAccountTransactionResponse;
import com.bancomo.objects.templates.savings.SavingsAccountTransactionTemplate;

/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface SavingsAccountTransactionMvpView extends MvpView {

    void showSavingAccountTemplate(SavingsAccountTransactionTemplate
                                           savingsAccountTransactionTemplate);

    void showTransactionSuccessfullyDone(SavingsAccountTransactionResponse
                                                 savingsAccountTransactionResponse);

    void checkSavingAccountTransactionStatusInDatabase();

    void showSavingAccountTransactionExistInDatabase();

    void showSavingAccountTransactionDoesNotExistInDatabase();

    void showError(int errorMessage);
}
