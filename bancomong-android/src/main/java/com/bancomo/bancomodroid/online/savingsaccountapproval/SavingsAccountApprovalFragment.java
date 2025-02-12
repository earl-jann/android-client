/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online.savingsaccountapproval;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.BancoMoBaseFragment;
import com.bancomo.bancomodroid.uihelpers.MFDatePicker;
import com.bancomo.objects.accounts.loan.SavingsApproval;
import com.bancomo.objects.accounts.savings.DepositType;
import com.bancomo.utils.Constants;
import com.bancomo.utils.DateHelper;
import com.bancomo.utils.FragmentConstants;
import com.bancomo.utils.SafeUIBlockingUtility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author nellyk
 */
public class SavingsAccountApprovalFragment extends BancoMoBaseFragment implements
        MFDatePicker.OnDatePickListener, SavingsAccountApprovalMvpView {

    public final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.tv_approval_date)
    TextView tvApprovalDate;

    @BindView(R.id.btn_approve_savings)
    Button btnApproveSavings;

    @BindView(R.id.et_savings_approval_reason)
    EditText etSavingsApprovalReason;

    @Inject
    SavingsAccountApprovalPresenter mSavingsAccountApprovalPresenter;

    View rootView;

    String approvaldate;
    public int savingsAccountNumber;
    public DepositType savingsAccountType;
    private DialogFragment mfDatePicker;
    private SafeUIBlockingUtility safeUIBlockingUtility;

    public static SavingsAccountApprovalFragment newInstance(int savingsAccountNumber,
                                                             DepositType type) {
        SavingsAccountApprovalFragment savingsAccountApproval =
                new SavingsAccountApprovalFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.SAVINGS_ACCOUNT_NUMBER, savingsAccountNumber);
        args.putParcelable(Constants.SAVINGS_ACCOUNT_TYPE, type);
        savingsAccountApproval.setArguments(args);
        return savingsAccountApproval;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
        if (getArguments() != null) {
            savingsAccountNumber = getArguments().getInt(Constants.SAVINGS_ACCOUNT_NUMBER);
            savingsAccountType = getArguments().getParcelable(Constants.SAVINGS_ACCOUNT_TYPE);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        if (getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        rootView = inflater.inflate(R.layout.dialog_fragment_approve_savings, null);

        ButterKnife.bind(this, rootView);
        mSavingsAccountApprovalPresenter.attachView(this);
        safeUIBlockingUtility = new SafeUIBlockingUtility(getActivity());

        showUserInterface();

        return rootView;
    }

    @Override
    public void showUserInterface() {
        mfDatePicker = MFDatePicker.newInsance(this);
        tvApprovalDate.setText(MFDatePicker.getDatePickedAsString());
        approvaldate = tvApprovalDate.getText().toString();
        showApprovalDate();
    }

    @OnClick(R.id.btn_approve_savings)
    void onClickApproveSavings() {
        SavingsApproval savingsApproval = new SavingsApproval();
        savingsApproval.setNote(etSavingsApprovalReason.getEditableText().toString());
        savingsApproval.setApprovedOnDate(approvaldate);
        initiateSavingsApproval(savingsApproval);
    }

    @OnClick(R.id.tv_approval_date)
    void onClickApprovalDate() {
        mfDatePicker.show(getActivity().getSupportFragmentManager(), FragmentConstants
                .DFRAG_DATE_PICKER);
    }

    @Override
    public void onDatePicked(String date) {
        tvApprovalDate.setText(date);
        approvaldate = date;
        showApprovalDate();
    }

    public void showApprovalDate() {
        approvaldate = DateHelper.getDateAsStringUsedForCollectionSheetPayload(approvaldate)
                .replace("-", " ");
    }

    private void initiateSavingsApproval(final SavingsApproval savingsApproval) {
        mSavingsAccountApprovalPresenter.approveSavingsApplication(
                savingsAccountNumber, savingsApproval);
    }

    @Override
    public void showSavingAccountApprovedSuccessfully(GenericResponse genericResponse) {
        Toast.makeText(getActivity(), "Savings Approved", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressbar(boolean b) {
        if (b) {
            safeUIBlockingUtility.safelyBlockUI();
        } else {
            safeUIBlockingUtility.safelyUnBlockUI();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSavingsAccountApprovalPresenter.detachView();
    }
}
