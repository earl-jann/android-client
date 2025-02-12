/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online.createnewcenter;

/**
 * Created by nellyk on 1/22/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.exceptions.InvalidTextInputException;
import com.bancomo.exceptions.RequiredFieldException;
import com.bancomo.exceptions.ShortOfLengthException;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.BancoMoBaseFragment;
import com.bancomo.bancomodroid.online.CentersActivity;
import com.bancomo.bancomodroid.uihelpers.MFDatePicker;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.response.SaveResponse;
import com.bancomo.services.data.CenterPayload;
import com.bancomo.utils.Constants;
import com.bancomo.utils.DateHelper;
import com.bancomo.utils.FragmentConstants;
import com.bancomo.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateNewCenterFragment extends BancoMoBaseFragment
        implements MFDatePicker.OnDatePickListener, CreateNewCenterMvpView {


    private static final String TAG = "CreateNewCenter";

    @BindView(R.id.et_center_name)
    EditText et_centerName;

    @BindView(R.id.cb_center_active_status)
    CheckBox cb_centerActiveStatus;

    @BindView(R.id.tv_center_activationDate)
    TextView tv_activationDate;

    @BindView(R.id.sp_center_offices)
    Spinner sp_offices;

    @BindView(R.id.btn_submit)
    Button bt_submit;

    @BindView(R.id.ll_center)
    LinearLayout llCenter;

    int officeId;
    Boolean result = true;
    @Inject
    CreateNewCenterPresenter mCreateNewCenterPresenter;
    private View rootView;
    private String activationdateString;
    private DialogFragment newDatePicker;
    private HashMap<String, Integer> officeNameIdHashMap = new HashMap<String, Integer>();

    public static CreateNewCenterFragment newInstance() {
        CreateNewCenterFragment createNewCenterFragment = new CreateNewCenterFragment();
        return createNewCenterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_new_center, null);

        ButterKnife.bind(this, rootView);
        mCreateNewCenterPresenter.attachView(this);

        inflateOfficeSpinner();
        inflateActivationDate();
        //client active checkbox onCheckedListener
        cb_centerActiveStatus.setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tv_activationDate.setVisibility(View.VISIBLE);
                } else {
                    tv_activationDate.setVisibility(View.GONE);
                }

            }
        });

        activationdateString = tv_activationDate.getText().toString();
        activationdateString = DateHelper.getDateAsStringUsedForCollectionSheetPayload
                (activationdateString).replace("-", " ");
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CenterPayload centerPayload = new CenterPayload();

                centerPayload.setName(et_centerName.getEditableText().toString());
                centerPayload.setActive(cb_centerActiveStatus.isChecked());
                centerPayload.setActivationDate(activationdateString);
                centerPayload.setOfficeId(officeId);
                centerPayload.setDateFormat("dd MMMM yyyy");
                centerPayload.setLocale("en");

                initiateCenterCreation(centerPayload);

            }
        });

        return rootView;
    }

    //inflating office list spinner
    private void inflateOfficeSpinner() {
        mCreateNewCenterPresenter.loadOffices();
    }


    private void initiateCenterCreation(CenterPayload centerPayload) {

        if (isCenterNameValid()) {
            mCreateNewCenterPresenter.createCenter(centerPayload);
        }
    }


    public void inflateActivationDate() {
        newDatePicker = MFDatePicker.newInsance(this);

        tv_activationDate.setText(MFDatePicker.getDatePickedAsString());

        tv_activationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDatePicker.show(getActivity().getSupportFragmentManager(), FragmentConstants
                        .DFRAG_DATE_PICKER);
            }

        });


    }

    public void onDatePicked(String date) {
        tv_activationDate.setText(date);

    }

    public boolean isCenterNameValid() {
        result = true;
        try {
            if (TextUtils.isEmpty(et_centerName.getEditableText().toString())) {
                throw new RequiredFieldException(getResources().getString(R.string.center_name),
                        getResources().getString(R.string.error_cannot_be_empty));
            }

            if (et_centerName.getEditableText().toString().trim().length() < 4 && et_centerName
                    .getEditableText().toString().trim().length() > 0) {
                throw new ShortOfLengthException(getResources().getString(R.string.center_name), 4);
            }
            if (!ValidationUtil.isNameValid(et_centerName.getEditableText().toString())) {
                throw new InvalidTextInputException(
                        getResources().getString(R.string.center_name),
                        getResources().getString(R.string.error_should_contain_only),
                        InvalidTextInputException.TYPE_ALPHABETS);
            }
        } catch (InvalidTextInputException e) {
            e.notifyUserWithToast(getActivity());
            result = false;
        } catch (ShortOfLengthException e) {
            e.notifyUserWithToast(getActivity());
            result = false;
        } catch (RequiredFieldException e) {
            e.notifyUserWithToast(getActivity());
            result = false;
        }
        return result;
    }


    @Override
    public void showOffices(List<Office> offices) {
        final List<String> officeList = new ArrayList<String>();

        for (Office office : offices) {
            officeList.add(office.getName());
            officeNameIdHashMap.put(office.getName(), office.getId());
        }
        ArrayAdapter<String> officeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, officeList);
        officeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        sp_offices.setAdapter(officeAdapter);
        sp_offices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                officeId = officeNameIdHashMap.get(officeList.get(i));
                Log.d("officeId " + officeList.get(i), String.valueOf(officeId));
                if (officeId != -1) {

                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_select_office)
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void centerCreatedSuccessfully(SaveResponse saveResponse) {
        Toast.makeText(getActivity(), R.string.center_created_successfully,
                Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
        Intent intent = new Intent(getActivity(), CentersActivity.class);
        intent.putExtra(Constants.CENTER_ID, saveResponse.getGroupId());
        startActivity(intent);
    }

    @Override
    public void showFetchingError(int errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFetchingError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar(boolean show) {
        if (show) {
            llCenter.setVisibility(View.GONE);
            showBancoMoProgressBar();
        } else {
            llCenter.setVisibility(View.VISIBLE);
            hideBancoMoProgressBar();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCreateNewCenterPresenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}