package com.bancomo.bancomodroid.offline.syncsavingsaccounttransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;

/**
 * Created by Rajan Maurya on 20/08/16.
 */
public class SyncSavingsAccountTransactionActivity extends BancoMoBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        showBackButton();
        replaceFragment(SyncSavingsAccountTransactionFragment.newInstance(), false, R.id.container);
    }
}
