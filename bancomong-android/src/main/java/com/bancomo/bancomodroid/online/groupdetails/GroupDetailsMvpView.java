package com.bancomo.bancomodroid.online.groupdetails;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.GroupAccounts;
import com.bancomo.objects.client.Client;
import com.bancomo.objects.group.Group;

import java.util.List;


/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface GroupDetailsMvpView extends MvpView {

    void showGroup(Group group);

    void showGroupClients(List<Client> clients);

    void showGroupAccounts(GroupAccounts groupAccounts);

    void showFetchingError(int errorMessage);
}
