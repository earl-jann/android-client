package com.bancomo.bancomodroid.online.clientdetails;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.accounts.ClientAccounts;
import com.bancomo.objects.client.Client;

import okhttp3.ResponseBody;

/**
 * Created by Rajan Maurya on 07/06/16.
 */
public interface ClientDetailsMvpView extends MvpView {

    void showClientInformation(Client client);

    void showUploadImageSuccessfully(ResponseBody response, String imagePath);

    void showUploadImageFailed(String s);

    void showUploadImageProgressbar(boolean b);

    void showClientImageDeletedSuccessfully();

    void showClientAccount(ClientAccounts clientAccounts);

    void showFetchingError(String s);
}
