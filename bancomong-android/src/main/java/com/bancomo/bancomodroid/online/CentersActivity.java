/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online;

import android.os.Bundle;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.online.centerdetails.CenterDetailsFragment;
import com.bancomo.bancomodroid.online.clientlist.ClientListFragment;
import com.bancomo.bancomodroid.online.grouplist.GroupListFragment;
import com.bancomo.bancomodroid.online.savingsaccount.SavingsAccountFragment;
import com.bancomo.objects.client.Client;
import com.bancomo.utils.Constants;

import java.util.List;

public class CentersActivity extends BancoMoBaseActivity implements
        GroupListFragment.OnFragmentInteractionListener,
        CenterDetailsFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        showBackButton();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int centerId = bundle.getInt(Constants.CENTER_ID);
            replaceFragment(CenterDetailsFragment.newInstance(centerId), false, R.id.container);
        }
    }

    @Override
    public void loadGroupsOfCenter(int centerId) {
        replaceFragment(GroupListFragment.newInstance(centerId), true, R.id.container);
    }

    @Override
    public void loadClientsOfGroup(List<Client> clientList) {
        replaceFragment(ClientListFragment.newInstance(clientList, true), true, R.id.container);
    }

    @Override
    public void addCenterSavingAccount(int centerId) {
        replaceFragment(SavingsAccountFragment.newInstance(centerId, true), true, R.id.container);
    }
}
