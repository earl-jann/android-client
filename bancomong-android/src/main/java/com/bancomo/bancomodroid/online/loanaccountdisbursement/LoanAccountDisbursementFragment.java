/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online.loanaccountdisbursement;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.BancoMoBaseFragment;
import com.bancomo.bancomodroid.core.util.Toaster;
import com.bancomo.bancomodroid.uihelpers.MFDatePicker;
import com.bancomo.objects.accounts.loan.LoanDisbursement;
import com.bancomo.objects.templates.loans.LoanTransactionTemplate;
import com.bancomo.utils.Constants;
import com.bancomo.utils.DateHelper;
import com.bancomo.utils.FragmentConstants;
import com.bancomo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nellyk on 1/22/2016.
 */
public class LoanAccountDisbursementFragment extends BancoMoBaseFragment implements
        MFDatePicker.OnDatePickListener, LoanAccountDisbursementMvpView,
        AdapterView.OnItemSelectedListener {

    public final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.tv_loan_disbursement_dates)
    TextView tvLoanDisbursementDates;

    @BindView(R.id.btn_disburse_loan)
    Button btnDisburseLoan;

    @BindView(R.id.sp_loan_payment_type)
    Spinner spPaymentType;

    @BindView(R.id.et_disbursed_amount)
    EditText etDisbursedAmount;

    @BindView(R.id.et_disbursement_note)
    EditText etDisbursementNote;

    @BindView(R.id.ll_disburse)
    LinearLayout llDisburse;

    @Inject
    LoanAccountDisbursementPresenter loanAccountDisbursementPresenter;

    View rootView;

    private int paymentTypeId;
    private String disbursementDates;
    private int loanAccountNumber;
    private DialogFragment mfDatePicker;
    private List<String> paymentTypeOptions;
    private ArrayAdapter<String> paymentTypeOptionAdapter;
    private LoanTransactionTemplate loanTransactionTemplate;

    public static LoanAccountDisbursementFragment newInstance(int loanAccountNumber) {
        LoanAccountDisbursementFragment loanAccountDisbursement =
                new LoanAccountDisbursementFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.LOAN_ACCOUNT_NUMBER, loanAccountNumber);
        loanAccountDisbursement.setArguments(args);
        return loanAccountDisbursement;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
        if (getArguments() != null) {
            loanAccountNumber = getArguments().getInt(Constants.LOAN_ACCOUNT_NUMBER);
        }
        paymentTypeOptions = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        rootView = inflater.inflate(R.layout.dialog_fragment_disburse_loan, null);
        ButterKnife.bind(this, rootView);
        loanAccountDisbursementPresenter.attachView(this);

        showUserInterface();
        loanAccountDisbursementPresenter.loadLoanTemplate(loanAccountNumber);

        return rootView;
    }

    @OnClick(R.id.btn_disburse_loan)
    void onSubmitDisburse() {
        LoanDisbursement loanDisbursement = new LoanDisbursement();
        loanDisbursement.setNote(etDisbursementNote.getEditableText().toString());
        loanDisbursement.setActualDisbursementDate(disbursementDates);
        loanDisbursement.setTransactionAmount(
                Double.valueOf(etDisbursedAmount.getEditableText().toString()));
        loanDisbursement.setPaymentId(paymentTypeId);
        loanAccountDisbursementPresenter.disburseLoan(loanAccountNumber, loanDisbursement);
    }

    @Override
    public void showUserInterface() {
        setToolbarTitle(getString(R.string.disburse_loan));
        mfDatePicker = MFDatePicker.newInsance(this);
        tvLoanDisbursementDates.setText(MFDatePicker.getDatePickedAsString());
        showDisbursementDate(tvLoanDisbursementDates.getText().toString());

        paymentTypeOptionAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, paymentTypeOptions);
        paymentTypeOptionAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPaymentType.setAdapter(paymentTypeOptionAdapter);
        spPaymentType.setOnItemSelectedListener(this);

    }

    @Override
    public void showDisbursementDate(String date) {
        disbursementDates = DateHelper.getDateAsStringUsedForCollectionSheetPayload(date)
                .replace("-", " ");
    }

    @OnClick(R.id.tv_loan_disbursement_dates)
    public void onClickTvDisburseDate() {
        mfDatePicker.show(getActivity().getSupportFragmentManager(), FragmentConstants
                .DFRAG_DATE_PICKER);
    }

    @Override
    public void onDatePicked(String date) {
        tvLoanDisbursementDates.setText(date);
        showDisbursementDate(date);
    }

    @Override
    public void showLoanTransactionTemplate(LoanTransactionTemplate loanTransactionTemplate) {
        this.loanTransactionTemplate = loanTransactionTemplate;
        etDisbursedAmount.setText(String.valueOf(loanTransactionTemplate.getAmount()));
        paymentTypeOptions.addAll(Utils.getPaymentTypeOptions(
                loanTransactionTemplate.getPaymentTypeOptions()));
        paymentTypeOptionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDisburseLoanSuccessfully(GenericResponse genericResponse) {
        Toast.makeText(getActivity(), R.string.loan_disburse_successfully,
                Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void showError(String message) {
        Toaster.show(rootView, message, Toaster.INDEFINITE);
    }

    @Override
    public void showError(int errorMessage) {
        Toaster.show(rootView, errorMessage);
    }

    @Override
    public void showProgressbar(boolean show) {
        if (show) {
            showBancoMoProgressBar();
            llDisburse.setVisibility(View.GONE);
        } else {
            hideBancoMoProgressBar();
            llDisburse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loanAccountDisbursementPresenter.detachView();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_loan_payment_type) {
            paymentTypeId = loanTransactionTemplate.getPaymentTypeOptions().get(position).getId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
