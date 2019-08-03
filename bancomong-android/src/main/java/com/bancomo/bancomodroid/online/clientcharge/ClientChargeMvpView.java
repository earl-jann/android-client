package com.bancomo.bancomodroid.online.clientcharge;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Charges;
import com.bancomo.objects.client.Page;

/**
 * Created by Rajan Maurya on 5/6/16.
 */
public interface ClientChargeMvpView extends MvpView {

    void showChargesList(Page<Charges> chargesPage);

    void showFetchingErrorCharges(String s);
}
