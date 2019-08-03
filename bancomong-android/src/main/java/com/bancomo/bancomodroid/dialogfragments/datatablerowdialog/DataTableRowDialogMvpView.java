package com.bancomo.bancomodroid.dialogfragments.datatablerowdialog;

import com.bancomo.api.GenericResponse;
import com.bancomo.bancomodroid.base.MvpView;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface DataTableRowDialogMvpView extends MvpView {

    void showDataTableEntrySuccessfully(GenericResponse genericResponse);

    void showError(String message);
}
