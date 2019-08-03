/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online.createnewgroup;

/**
 * Created by nellyk on 1/22/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.exceptions.InvalidTextInputException;
import com.bancomo.exceptions.RequiredFieldException;
import com.bancomo.exceptions.ShortOfLengthException;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.ProgressableFragment;
import com.bancomo.bancomodroid.online.GroupsActivity;
import com.bancomo.bancomodroid.uihelpers.MFDatePicker;
import com.bancomo.objects.group.GroupPayload;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.response.SaveResponse;
import com.bancomo.utils.Constants;
import com.bancomo.utils.DateHelper;
import com.bancomo.utils.FragmentConstants;
import com.bancomo.utils.BancoMoResponseHandler;
import com.bancomo.utils.PrefManager;
import com.bancomo.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO Show Image and Text after successful or Failed during creation of Group and
//TODO A button to Continue or Finish the GroupCreation.
public class CreateNewGroupFragment extends ProgressableFragment
        implements MFDatePicker.OnDatePickListener, CreateNewGroupMvpView,
        AdapterView.OnItemSelectedListener {


    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.et_group_name)
    EditText et_groupName;

    @BindView(R.id.et_group_external_id)
    EditText et_groupexternalId;

    @BindView(R.id.cb_group_active_status)
    CheckBox cb_groupActiveStatus;

    @BindView(R.id.tv_group_submission_date)
    TextView tv_submissionDate;

    @BindView(R.id.tv_group_activationDate)
    TextView tv_activationDate;

    @BindView(R.id.sp_group_offices)
    Spinner sp_offices;

    @BindView(R.id.btn_submit)
    Button bt_submit;

    @Inject
    CreateNewGroupPresenter mCreateNewGroupPresenter;

    String activationdateString;
    int officeId;
    Boolean result = true;
    View rootView;
    String dateofsubmissionstring;
    private DialogFragment mfDatePicker;
    private DialogFragment newDatePicker;

    private List<String> mListOffices = new ArrayList<>();
    private List<Office> officeList;
    private ArrayAdapter<String> mOfficesAdapter;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_group_offices) {
            officeId = officeList.get(position).getId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public CreateNewGroupFragment() {
        // Required empty public constructor
    }

    public static CreateNewGroupFragment newInstance() {
        CreateNewGroupFragment createNewGroupFragment = new CreateNewGroupFragment();
        return createNewGroupFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_new_group, null);

        ButterKnife.bind(this, rootView);
        mCreateNewGroupPresenter.attachView(this);

        inflateOfficesSpinner();
        inflateSubmissionDate();
        inflateActivationDate();

        mCreateNewGroupPresenter.loadOffices();

        //client active checkbox onCheckedListener
        cb_groupActiveStatus.setOnCheckedChangeListener(new CompoundButton
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
        dateofsubmissionstring = tv_submissionDate.getText().toString();
        dateofsubmissionstring = DateHelper.getDateAsStringUsedForDateofBirth
                (dateofsubmissionstring).replace("-", " ");

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GroupPayload groupPayload = new GroupPayload();

                groupPayload.setName(et_groupName.getEditableText().toString());
                groupPayload.setExternalId(et_groupexternalId.getEditableText().toString());
                groupPayload.setActive(cb_groupActiveStatus.isChecked());
                groupPayload.setActivationDate(activationdateString);
                groupPayload.setSubmissionDate(dateofsubmissionstring);
                groupPayload.setOfficeId(officeId);
                groupPayload.setDateFormat("dd MMMM yyyy");
                groupPayload.setLocale("en");

                initiateGroupCreation(groupPayload);

            }
        });

        return rootView;
    }

    private void initiateGroupCreation(GroupPayload groupPayload) {
        //TextField validations
        if (!isGroupNameValid()) {
            return;
        }

        mCreateNewGroupPresenter.createGroup(groupPayload);
    }


    private void inflateOfficesSpinner() {
        mOfficesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                mListOffices);
        mOfficesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_offices.setAdapter(mOfficesAdapter);
        sp_offices.setOnItemSelectedListener(this);
    }

    public void inflateSubmissionDate() {
        mfDatePicker = MFDatePicker.newInsance(this);

        tv_submissionDate.setText(MFDatePicker.getDatePickedAsString());

        tv_submissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfDatePicker.show(getActivity().getSupportFragmentManager(), FragmentConstants
                        .DFRAG_DATE_PICKER);
            }
        });

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
        tv_submissionDate.setText(date);
        tv_activationDate.setText(date);

    }

    public boolean isGroupNameValid() {
        result = true;
        try {
            if (TextUtils.isEmpty(et_groupName.getEditableText().toString())) {
                throw new RequiredFieldException(getResources().getString(R.string.group_name),
                        getResources().getString(R.string.error_cannot_be_empty));
            }

            if (et_groupName.getEditableText().toString().trim().length() < 4 && et_groupName
                    .getEditableText().toString().trim().length() > 0) {
                throw new ShortOfLengthException(getResources().getString(R.string.group_name), 4);
            }
            if (!ValidationUtil.isNameValid(et_groupName.getEditableText().toString())) {
                throw new InvalidTextInputException(getResources().getString(R.string.group_name)
                        , getResources().getString(R.string.error_should_contain_only),
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
        officeList = offices;
        for (Office office : offices) {
            mListOffices.add(office.getName());
        }
        mOfficesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupCreatedSuccessfully(SaveResponse saveResponse) {
        Toast.makeText(getActivity(), "Group" + BancoMoResponseHandler.getResponse(),
                Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
        if (PrefManager.getUserStatus() == Constants.USER_ONLINE) {
            Intent groupActivityIntent = new Intent(getActivity(), GroupsActivity.class);
            groupActivityIntent.putExtra(Constants.GROUP_ID, saveResponse.getGroupId());
            startActivity(groupActivityIntent);
        }
    }

    @Override
    public void showFetchingError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar(boolean b) {
        showProgress(b);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCreateNewGroupPresenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}