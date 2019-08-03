package com.bancomo.bancomodroid.online.datatablelistfragment;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Client;

public interface DataTableListMvpView extends MvpView {

    void showMessage(int messageId);

    void showMessage(String message);

    void showClientCreatedSuccessfully(Client client);
}
