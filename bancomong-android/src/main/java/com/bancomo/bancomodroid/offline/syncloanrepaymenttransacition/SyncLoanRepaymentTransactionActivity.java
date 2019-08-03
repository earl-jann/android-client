package com.bancomo.bancomodroid.offline.syncloanrepaymenttransacition;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;

/**
 * Created by Rajan Maurya on 30/07/16.
 */
public class SyncLoanRepaymentTransactionActivity extends BancoMoBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        showBackButton();
        replaceFragment(SyncLoanRepaymentTransactionFragment.newInstance(), false, R.id.container);
    }
}
