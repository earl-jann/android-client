/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.dialogfragments.documentdialog;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.api.GenericResponse;
import com.bancomo.exceptions.RequiredFieldException;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.util.Toaster;
import com.bancomo.objects.noncore.Document;
import com.bancomo.utils.AndroidVersionUtil;
import com.bancomo.utils.CheckSelfPermissionAndRequest;
import com.bancomo.utils.Constants;
import com.bancomo.utils.FileUtils;
import com.bancomo.utils.SafeUIBlockingUtility;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ishankhanna on 04/07/14.
 * <p/>
 * Use this Dialog Fragment to Create and/or Update Documents
 */
public class DocumentDialogFragment extends DialogFragment implements DocumentDialogMvpView {

    private static final int FILE_SELECT_CODE = 0;

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.tv_document_action)
    TextView tv_document_action;

    @BindView(R.id.et_document_name)
    EditText et_document_name;

    @BindView(R.id.et_document_description)
    EditText et_document_description;

    @BindView(R.id.tv_choose_file)
    TextView tv_choose_file;

    @BindView(R.id.bt_upload)
    Button bt_upload;

    @Inject
    DocumentDialogPresenter mDocumentDialogPresenter;

    View rootView;

    SafeUIBlockingUtility safeUIBlockingUtility;

    private OnDialogFragmentInteractionListener mListener;

    private String documentName;
    private String documentDescription;
    private String entityType;
    private String filePath;
    private String documentAction;
    private Document document;
    private int entityId;
    private File fileChoosen;
    private Uri uri;

    public static DocumentDialogFragment newInstance(String entityType, int entityId,
                                                     String documentAction,
                                                     Document document) {
        DocumentDialogFragment documentDialogFragment = new DocumentDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ENTITY_TYPE, entityType);
        args.putInt(Constants.ENTITY_ID, entityId);
        args.putString(Constants.DOCUMENT_ACTIONS, documentAction);
        args.putParcelable(Constants.DOCUMENT, document);
        documentDialogFragment.setArguments(args);
        return documentDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
        safeUIBlockingUtility = new SafeUIBlockingUtility(getActivity());
        if (getArguments() != null) {
            entityType = getArguments().getString(Constants.ENTITY_TYPE);
            entityId = getArguments().getInt(Constants.ENTITY_ID);
            documentAction = getArguments().getString(Constants.DOCUMENT_ACTIONS);
            document = getArguments().getParcelable(Constants.DOCUMENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_fragment_document, container, false);

        ButterKnife.bind(this, rootView);
        mDocumentDialogPresenter.attachView(this);

        if (documentAction == getResources().getString(R.string.update_document)) {
            tv_document_action.setText(R.string.update_document);
            et_document_name.setText(document.getName());
            et_document_description.setText(document.getDescription());
        } else if (documentAction == getResources().getString(R.string.upload_document)) {
            tv_document_action.setText(R.string.upload_document);
        }

        return rootView;
    }


    @OnClick(R.id.bt_upload)
    public void beginUpload() {
        try {
            validateInput();
        } catch (RequiredFieldException e) {
            e.notifyUserWithToast(getActivity());
        }
    }

    @OnClick(R.id.tv_choose_file)
    public void openFilePicker() {
        checkPermissionAndRequest();
    }

    /**
     * This Method Validating the Name and Description String if any of them is null them throw
     * an RequiredFieldException, Otherwise start uploading Document.
     *
     * @throws RequiredFieldException
     */
    public void validateInput() throws RequiredFieldException {

        documentName = et_document_name.getEditableText().toString();

        if (documentName.length() == 0 || documentName.equals(""))
            throw new RequiredFieldException(getResources().getString(R.string.name),
                    getString(R.string.message_field_required));

        documentDescription = et_document_description.getEditableText().toString();

        if (documentDescription.length() == 0 || documentDescription.equals(""))
            throw new RequiredFieldException(getResources().getString(R.string.description),
                    getString(R.string.message_field_required));

        //Start Uploading Document
        if (documentAction == getResources().getString(R.string.update_document)) {
            mDocumentDialogPresenter.updateDocument(entityType, entityId, document.getId(),
                    documentName, documentDescription, fileChoosen);
        } else if (documentAction == getResources().getString(R.string.upload_document)) {
            mDocumentDialogPresenter.createDocument(entityType, entityId,
                    documentName, documentDescription, fileChoosen);
        }
    }

    /**
     * This Method Checking the Permission READ_EXTERNAL_STORAGE is granted or not.
     * If not then prompt user a dialog to grant the READ_EXTERNAL_STORAGE permission.
     * and If Permission is granted already then start Intent to get the Document from the External
     * Storage.
     */
    @Override
    public void checkPermissionAndRequest() {
        if (CheckSelfPermissionAndRequest.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getExternalStorageDocument();
        } else {
            requestPermission();
        }
    }

    /**
     * This Method is Requesting the Permission
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void requestPermission() {
        CheckSelfPermissionAndRequest.requestPermission(
                (BancoMoBaseActivity) getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE,
                getResources().getString(
                        R.string.dialog_message_read_external_storage_permission_denied),
                getResources().getString(R.string.dialog_message_permission_never_ask_again_read),
                Constants.READ_EXTERNAL_STORAGE_STATUS);
    }

    /**
     * This Method getting the Response after User Grant or denied the Permission
     *
     * @param requestCode  Request Code
     * @param permissions  Permission
     * @param grantResults GrantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    getExternalStorageDocument();

                } else {

                    // permission denied, boo! Disable the
                    Toaster.show(rootView, getResources()
                            .getString(R.string.permission_denied_to_read_external_document));
                }
            }
        }
    }

    /**
     * This method is to start an intent(getExternal Storage Document).
     * If Android Version is Kitkat or greater then start intent with ACTION_OPEN_DOCUMENT,
     * otherwise with ACTION_GET_CONTENT
     */
    @Override
    public void getExternalStorageDocument() {
        Intent intentDocument;
        if (AndroidVersionUtil.isApiVersionGreaterOrEqual(Build.VERSION_CODES.KITKAT)) {
            intentDocument = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intentDocument = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intentDocument.addCategory(Intent.CATEGORY_OPENABLE);
        intentDocument.setType("*/*");
        startActivityForResult(intentDocument, FILE_SELECT_CODE);
    }

    /**
     * This Method will be called when any document will be selected from the External Storage.
     *
     * @param requestCode Request Code
     * @param resultCode  Result Code ok or Cancel
     * @param data        Intent Data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();

                    filePath = FileUtils.getPathReal(getActivity(), uri);
                    if (filePath != null) {
                        fileChoosen = new File(filePath);
                    }

                    if (fileChoosen != null) {
                        tv_choose_file.setText(fileChoosen.getName());
                    } else {
                        break;
                    }
                    bt_upload.setEnabled(true);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showDocumentedCreatedSuccessfully(GenericResponse genericResponse) {
        Toast.makeText(getActivity(), String.format(getString(R.string
                        .uploaded_successfully), fileChoosen.getName()),
                Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    @Override
    public void showDocumentUpdatedSuccessfully() {
        Toast.makeText(getActivity(), String.format(getString(R.string
                        .document_updated_successfully), fileChoosen.getName()),
                Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    @Override
    public void showError(int errorMessage) {
        Toast.makeText(getActivity(), getString(errorMessage), Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
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
        mDocumentDialogPresenter.detachView();
    }

    public interface OnDialogFragmentInteractionListener {
        void initiateFileUpload(String name, String description);
    }
}
