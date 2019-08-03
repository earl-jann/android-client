package com.bancomo.bancomodroid.online.clientlist;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Client;

import java.util.List;

/**
 * Created by Rajan Maurya on 6/6/16.
 */
public interface ClientListMvpView extends MvpView {

    void showUserInterface();

    void showClientList(List<Client>  clients);

    void showLoadMoreClients(List<Client> clients);

    void showEmptyClientList(int message);

    void unregisterSwipeAndScrollListener();

    void showMessage(int message);

    void showError();

}
