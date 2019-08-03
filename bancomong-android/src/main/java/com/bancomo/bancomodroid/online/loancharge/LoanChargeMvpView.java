package com.bancomo.bancomodroid.online.loancharge;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Charges;

import java.util.List;

/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface LoanChargeMvpView extends MvpView {

    void showLoanChargesList(List<Charges> charges);

    void showFetchingError(String s);
}
