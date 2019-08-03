package com.bancomo.bancomodroid.online.grouploanaccount;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.loan.Loans;
import com.bancomo.objects.organisation.LoanProducts;
import com.bancomo.objects.templates.loans.GroupLoanTemplate;

import java.util.List;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface GroupLoanAccountMvpView extends MvpView {

    void showAllLoans(List<LoanProducts> productLoans);

    void showGroupLoansAccountCreatedSuccessfully(Loans loans);

    void showGroupLoanTemplate(GroupLoanTemplate groupLoanTemplate);

    void showFetchingError(String s);
}
