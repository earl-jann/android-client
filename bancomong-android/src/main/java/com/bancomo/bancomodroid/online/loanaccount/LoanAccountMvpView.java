package com.bancomo.bancomodroid.online.loanaccount;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.Loans;
import com.bancomo.objects.organisation.LoanProducts;
import com.bancomo.objects.templates.loans.LoanTemplate;

import java.util.List;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface LoanAccountMvpView extends MvpView {

    void showAllLoan(List<LoanProducts> productLoanses);

    void showLoanAccountTemplate(LoanTemplate loanTemplate);

    void showLoanAccountCreatedSuccessfully(Loans loans);

    void showMessage(int messageId);

    void showFetchingError(String s);
}
