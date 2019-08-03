/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.online.clientdetails.ClientDetailsFragment;
import com.bancomo.bancomodroid.online.loanaccountsummary.LoanAccountSummaryFragment;
import com.bancomo.bancomodroid.online.loanrepayment.LoanRepaymentFragment;
import com.bancomo.bancomodroid.online.loanrepaymentschedule.LoanRepaymentScheduleFragment;
import com.bancomo.bancomodroid.online.loantransactions.LoanTransactionsFragment;
import com.bancomo.bancomodroid.online.savingaccountsummary.SavingsAccountSummaryFragment;
import com.bancomo.bancomodroid.online.savingaccounttransaction.SavingsAccountTransactionFragment;
import com.bancomo.bancomodroid.online.surveylist.SurveyListFragment;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;
import com.bancomo.objects.accounts.savings.DepositType;
import com.bancomo.objects.accounts.savings.SavingsAccountWithAssociations;
import com.bancomo.objects.survey.Survey;
import com.bancomo.utils.Constants;

import butterknife.ButterKnife;

public class ClientActivity extends BancoMoBaseActivity implements ClientDetailsFragment
        .OnFragmentInteractionListener,
        LoanAccountSummaryFragment.OnFragmentInteractionListener,
        SavingsAccountSummaryFragment.OnFragmentInteractionListener,
        SurveyListFragment.OnFragmentInteractionListener {

    private int clientId = 0, loanAccountNumber = 0, savingsAccountNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        ButterKnife.bind(this);
        showBackButton();
        clientId = getIntent().getExtras().getInt(Constants.CLIENT_ID);
        loanAccountNumber = getIntent().getExtras().getInt(Constants.LOAN_ACCOUNT_NUMBER);
        savingsAccountNumber = getIntent().getExtras().getInt(Constants.SAVINGS_ACCOUNT_NUMBER);
        DepositType depositType = new DepositType();
        depositType.setId(100);
        depositType.setValue(Constants.ENTITY_TYPE_SAVINGS);
        if (clientId != 0) {
            replaceFragment(ClientDetailsFragment.newInstance(clientId), false, R.id.container);
        } else if (loanAccountNumber != 0) {
            replaceFragment(LoanAccountSummaryFragment.newInstance(loanAccountNumber, false), true,
                    R.id.container);
        } else if (savingsAccountNumber != 0) {
            replaceFragment(SavingsAccountSummaryFragment.newInstance(savingsAccountNumber,
                    depositType, false), true, R.id.container);
        }
    }

    /**
     * Called when a Loan Account is Selected
     * from the list of Loan Accounts on Client Details Fragment
     * It displays the summary of the Selected Loan Account
     */
    @Override
    public void loadLoanAccountSummary(int loanAccountNumber) {
        replaceFragment(LoanAccountSummaryFragment.newInstance(loanAccountNumber, true), true, R.id
                .container);
    }

    /**
     * Called when a Savings Account is Selected
     * from the list of Savings Accounts on Client Details Fragment
     * <p/>
     * It displays the summary of the Selected Savings Account
     */
    @Override
    public void loadSavingsAccountSummary(int savingsAccountNumber, DepositType accountType) {
        replaceFragment(SavingsAccountSummaryFragment.newInstance(savingsAccountNumber,
                accountType, true), true, R.id.container);
    }

    /**
     * Called when the make the make repayment button is clicked
     * in the Loan Account Summary Fragment.
     * <p/>
     * It will display the Loan Repayment Fragment where
     * the Information of the repayment has to be filled in.
     */
    @Override
    public void makeRepayment(LoanWithAssociations loan) {
        replaceFragment(LoanRepaymentFragment.newInstance(loan), true, R.id.container);
    }

    /**
     * Called when the Repayment Schedule option from the Menu is
     * clicked
     * <p/>
     * It will display the Complete Loan Repayment Schedule.
     */
    @Override
    public void loadRepaymentSchedule(int loanId) {
        replaceFragment(LoanRepaymentScheduleFragment.newInstance(loanId), true, R.id.container);
    }

    /**
     * Called when the Transactions option from the Menu is clicked
     * <p/>
     * It will display all the Transactions associated with the Loan
     * and also their details
     */

    @Override
    public void loadLoanTransactions(int loanId) {
        replaceFragment(LoanTransactionsFragment.newInstance(loanId), true, R.id.container);
    }

    /**
     * Called when the make the make deposit button is clicked
     * in the Savings Account Summary Fragment.
     * <p/>
     * It will display the Transaction Fragment where the information
     * of the transaction has to be filled in.
     * <p/>
     * The transactionType defines if the transaction is a Deposit or a Withdrawal
     */
    @Override
    public void doTransaction(SavingsAccountWithAssociations savingsAccountWithAssociations,
                              String transactionType, DepositType accountType) {
        replaceFragment(SavingsAccountTransactionFragment.newInstance
                (savingsAccountWithAssociations, transactionType, accountType), true, R.id
                .container);
    }

    /**
     * Called when the Survey Question Card Click and Survey Question View opens for making survey.
     *
     * @param survey   Survey
     * @param clientId Client Id
     */
    @Override
    public void loadSurveyQuestion(Survey survey, int clientId) {
        Intent myIntent = new Intent(this, SurveyQuestionActivity.class);
        myIntent.putExtra(Constants.SURVEYS, (new Gson()).toJson(survey));
        myIntent.putExtra(Constants.CLIENT_ID, clientId);
        startActivity(myIntent);
    }
}
