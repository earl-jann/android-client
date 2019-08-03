package com.bancomo.bancomodroid.dialogfragments.identifierdialog;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.noncore.IdentifierTemplate;

/**
 * Created by Rajan Maurya on 01/10/16.
 */

public interface IdentifierDialogMvpView extends MvpView {

    void showIdentifierSpinners();

    void showClientIdentifierTemplate(IdentifierTemplate identifierTemplate);

    void showIdentifierCreatedSuccessfully();

    void showMessage(String message);

    void showError(int errorMessage);
}
