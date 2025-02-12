/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online.datatablelistfragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.exceptions.RequiredFieldException;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.util.Toaster;
import com.bancomo.bancomodroid.formwidgets.FormEditText;
import com.bancomo.bancomodroid.formwidgets.FormNumericEditText;
import com.bancomo.bancomodroid.formwidgets.FormSpinner;
import com.bancomo.bancomodroid.formwidgets.FormToggleButton;
import com.bancomo.bancomodroid.formwidgets.FormWidget;
import com.bancomo.bancomodroid.online.ClientActivity;
import com.bancomo.objects.client.Client;
import com.bancomo.objects.client.ClientPayload;
import com.bancomo.objects.noncore.ColumnHeader;
import com.bancomo.objects.noncore.ColumnValue;
import com.bancomo.objects.noncore.DataTable;
import com.bancomo.objects.noncore.DataTablePayload;
import com.bancomo.services.data.GroupLoanPayload;
import com.bancomo.services.data.LoansPayload;
import com.bancomo.utils.Constants;
import com.bancomo.utils.BancoMoResponseHandler;
import com.bancomo.utils.PrefManager;
import com.bancomo.utils.SafeUIBlockingUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A generic fragment to show the DataTables at the runtime.
 *
 * It receives the list of DataTables, the corresponding LoanPayload (client/center/group)
 * and an identifier int that states the type of entity generating the request for datatables.
 *
 * It differs from the other DatatableDialogFragments in the sense that -
 * 1. it does NOT query for the datatables i.e. it does not fetch the datatable from the endpoint.
 * 2. it shows all the datatables (from datatable array) unlike in the other fragments which show
 *  a single datatable.
 */
public class DataTableListFragment extends Fragment
        implements DataTableListMvpView {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.ll_data_table_entry_form)
    LinearLayout linearLayout;

    @Inject
    DataTableListPresenter mDataTableListPresenter;

    private List<DataTable> dataTables;
    private ArrayList<DataTablePayload> dataTablePayloadElements;
    private LoansPayload clientLoansPayload;
    private GroupLoanPayload groupLoanPayload;
    private ClientPayload clientPayload;
    private int requestType;
    private View rootView;
    private SafeUIBlockingUtility safeUIBlockingUtility;
    private List<List<FormWidget>> listFormWidgets = new ArrayList<List<FormWidget>>();

    public static DataTableListFragment newInstance(List<DataTable> dataTables,
                                                    Object payload, int type) {
        DataTableListFragment dataTableListFragment = new DataTableListFragment();
        Bundle args = new Bundle();
        dataTableListFragment.dataTables = dataTables;
        dataTableListFragment.requestType = type;
        switch (type) {
            case Constants.CLIENT_LOAN:
                dataTableListFragment.clientLoansPayload = (LoansPayload) payload;
                break;
            case Constants.GROUP_LOAN:
                dataTableListFragment.groupLoanPayload = (GroupLoanPayload) payload;
                break;
            case Constants.CREATE_CLIENT:
                dataTableListFragment.clientPayload = (ClientPayload) payload;
                break;
        }
        dataTableListFragment.setArguments(args);
        return dataTableListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_ADJUST_RESIZE);

        rootView = inflater.inflate(R.layout.dialog_fragment_add_entry_to_datatable, container,
                false);

        ButterKnife.bind(this, rootView);
        mDataTableListPresenter.attachView(this);

        getActivity().setTitle(getActivity().getResources().getString(
                R.string.associated_datatables));

        safeUIBlockingUtility = new SafeUIBlockingUtility(DataTableListFragment.this
                .getActivity());

        for (DataTable datatable: dataTables) {
            createForm(datatable);
        }
        addSaveButton();

        return rootView;
    }

    public void createForm(DataTable table) {

        TextView tableName = new TextView(getActivity().getApplicationContext());
        tableName.setText(table.getRegisteredTableName());
        tableName.setGravity(Gravity.CENTER_HORIZONTAL);
        tableName.setTypeface(null, Typeface.BOLD);
        tableName.setTextColor(getActivity().getResources().getColor(R.color.black));
        tableName.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getActivity().getResources().getDimension(R.dimen.datatable_name_heading));
        linearLayout.addView(tableName);

        List<FormWidget> formWidgets = new ArrayList<FormWidget>();

        for (ColumnHeader columnHeader : table.getColumnHeaderData()) {

            if (!columnHeader.getColumnPrimaryKey()) {

                if (columnHeader.getColumnDisplayType().equals(FormWidget.SCHEMA_KEY_STRING) ||
                        columnHeader.getColumnDisplayType().equals(FormWidget.SCHEMA_KEY_TEXT)) {

                    FormEditText formEditText = new FormEditText(getActivity(), columnHeader
                            .getColumnName());
                    formWidgets.add(formEditText);
                    linearLayout.addView(formEditText.getView());

                } else if (columnHeader.getColumnDisplayType().equals(FormWidget.SCHEMA_KEY_INT)) {

                    FormNumericEditText formNumericEditText = new FormNumericEditText(getActivity
                            (), columnHeader.getColumnName());
                    formNumericEditText.setReturnType(FormWidget.SCHEMA_KEY_INT);
                    formWidgets.add(formNumericEditText);
                    linearLayout.addView(formNumericEditText.getView());


                } else if (columnHeader.getColumnDisplayType().equals(FormWidget
                        .SCHEMA_KEY_DECIMAL)) {

                    FormNumericEditText formNumericEditText = new FormNumericEditText(getActivity
                            (), columnHeader.getColumnName());
                    formNumericEditText.setReturnType(FormWidget.SCHEMA_KEY_DECIMAL);
                    formWidgets.add(formNumericEditText);
                    linearLayout.addView(formNumericEditText.getView());


                } else if (columnHeader.getColumnDisplayType().equals(FormWidget
                        .SCHEMA_KEY_CODELOOKUP) || columnHeader.getColumnDisplayType().equals
                        (FormWidget.SCHEMA_KEY_CODEVALUE)) {

                    if (columnHeader.getColumnValues().size() > 0) {
                        List<String> columnValueStrings = new ArrayList<String>();
                        List<Integer> columnValueIds = new ArrayList<Integer>();

                        for (ColumnValue columnValue : columnHeader.getColumnValues()) {
                            columnValueStrings.add(columnValue.getValue());
                            columnValueIds.add(columnValue.getId());
                        }

                        FormSpinner formSpinner = new FormSpinner(getActivity(), columnHeader
                                .getColumnName(), columnValueStrings, columnValueIds);
                        formSpinner.setReturnType(FormWidget.SCHEMA_KEY_CODEVALUE);
                        formWidgets.add(formSpinner);
                        linearLayout.addView(formSpinner.getView());
                    }

                } else if (columnHeader.getColumnDisplayType().equals(FormWidget.SCHEMA_KEY_DATE)) {

                    FormEditText formEditText = new FormEditText(getActivity(), columnHeader
                            .getColumnName());
                    formEditText.setIsDateField(true, getActivity().getSupportFragmentManager());
                    formWidgets.add(formEditText);
                    linearLayout.addView(formEditText.getView());
                } else if (columnHeader.getColumnDisplayType().equals(FormWidget.SCHEMA_KEY_BOOL)) {

                    FormToggleButton formToggleButton = new FormToggleButton(getActivity(),
                            columnHeader.getColumnName());
                    formWidgets.add(formToggleButton);
                    linearLayout.addView(formToggleButton.getView());
                }
            }
        }
        listFormWidgets.add(formWidgets);
    }

    private void addSaveButton() {
        Button bt_processForm = new Button(getActivity());
        bt_processForm.setLayoutParams(FormWidget.defaultLayoutParams);
        bt_processForm.setText(getString(R.string.save));
        bt_processForm.setBackgroundColor(getActivity().getResources().getColor(R.color.blue_dark));

        linearLayout.addView(bt_processForm);
        bt_processForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onSaveActionRequested();
                } catch (RequiredFieldException e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
            }
        });
    }

    public void onSaveActionRequested() throws RequiredFieldException {

        dataTablePayloadElements = new ArrayList<>();
        for (int i = 0; i < dataTables.size(); i++) {
            DataTablePayload dataTablePayload = new
                    DataTablePayload();
            dataTablePayload.setRegisteredTableName(dataTables.get(i).getRegisteredTableName());
            dataTablePayload.setData(addDataTableInput(i));
            dataTablePayloadElements.add(dataTablePayload);
        }

        switch (requestType) {
            case Constants.CLIENT_LOAN:
                clientLoansPayload.setDataTables(dataTablePayloadElements);
                mDataTableListPresenter.createLoansAccount(clientLoansPayload);
                break;

            case Constants.GROUP_LOAN:
                //Add Datatables in GroupLoan Payload and then add them here.
                mDataTableListPresenter.createGroupLoanAccount(groupLoanPayload);
                break;

            case Constants.CREATE_CLIENT:
                clientPayload.setDatatables(dataTablePayloadElements);
                mDataTableListPresenter.createClient(clientPayload);
                break;
        }
    }

    HashMap<String, Object> addDataTableInput(int index) {

        List<FormWidget> formWidgets = listFormWidgets.get(index);
        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put(Constants.DATE_FORMAT, "dd-mm-YYYY");
        payload.put(Constants.LOCALE, "en");
        for (FormWidget formWidget : formWidgets) {

            if (formWidget.getReturnType().equals(FormWidget.SCHEMA_KEY_INT)) {
                payload.put(formWidget.getPropertyName(), Integer.parseInt(formWidget.getValue()
                        .equals("") ? "0" : formWidget.getValue()));
            } else if (formWidget.getReturnType().equals(FormWidget.SCHEMA_KEY_DECIMAL)) {
                payload.put(formWidget.getPropertyName(), Double.parseDouble(formWidget.getValue
                        ().equals("") ? "0.0" : formWidget.getValue()));
            } else if (formWidget.getReturnType().equals(FormWidget.SCHEMA_KEY_CODEVALUE)) {
                FormSpinner formSpinner = (FormSpinner) formWidget;
                payload.put(formWidget.getPropertyName(), formSpinner.getIdOfSelectedItem
                        (formWidget.getValue()));
            } else {
                payload.put(formWidget.getPropertyName(), formWidget.getValue());
            }
        }
        return payload;
    }


    @Override
    public void showMessage(int messageId) {
        Toaster.show(rootView, getString(messageId));
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void showMessage(String message) {
        Toaster.show(rootView, message);
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void showClientCreatedSuccessfully(Client client) {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(getActivity(), getString(R.string.client) +
                BancoMoResponseHandler.getResponse(), Toast.LENGTH_SHORT).show();
        if (PrefManager.getUserStatus() == Constants.USER_ONLINE) {
            Intent clientActivityIntent = new Intent(getActivity(), ClientActivity.class);
            clientActivityIntent.putExtra(Constants.CLIENT_ID, client.getClientId());
            startActivity(clientActivityIntent);
        }
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
        mDataTableListPresenter.detachView();
    }
}
