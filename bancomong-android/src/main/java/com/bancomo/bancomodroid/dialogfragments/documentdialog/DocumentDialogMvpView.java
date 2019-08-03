package com.bancomo.bancomodroid.dialogfragments.documentdialog;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;

/**
 * Created by Rajan Maurya on 8/6/16.
 */
public interface DocumentDialogMvpView extends MvpView {

    void checkPermissionAndRequest();

    void requestPermission();

    void getExternalStorageDocument();

    void showDocumentedCreatedSuccessfully(GenericResponse genericResponse);

    void showDocumentUpdatedSuccessfully();

    void showError(int errorMessage);
}
