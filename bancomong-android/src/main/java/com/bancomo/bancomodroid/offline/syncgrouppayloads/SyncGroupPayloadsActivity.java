package com.bancomo.bancomodroid.offline.syncgrouppayloads;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;

/**
 * Created by Rajan Maurya on 21/07/16.
 */
public class SyncGroupPayloadsActivity extends BancoMoBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        showBackButton();
        replaceFragment(SyncGroupPayloadsFragment.newInstance(), false, R.id.container);
    }
}
