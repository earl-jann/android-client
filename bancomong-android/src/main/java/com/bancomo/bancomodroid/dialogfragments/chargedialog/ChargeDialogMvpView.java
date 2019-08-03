package com.bancomo.bancomodroid.dialogfragments.chargedialog;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Charges;
import com.bancomo.objects.templates.clients.ChargeTemplate;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface ChargeDialogMvpView extends MvpView {

    void showAllChargesV2(ChargeTemplate chargeTemplate);

    void showChargesCreatedSuccessfully(Charges changes);

    void showFetchingError(String s);
}
