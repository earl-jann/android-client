package com.bancomo.bancomodroid.online.createnewclient;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.organisation.Staff;
import com.bancomo.objects.templates.clients.ClientsTemplate;

import java.util.List;

/**
 * Created by Rajan Maurya on 6/6/16.
 */
public interface CreateNewClientMvpView extends MvpView {

    void showUserInterface();

    void showClientTemplate(ClientsTemplate clientsTemplate);

    void showOffices(List<Office> offices);

    void showStaffInOffices(List<Staff> staffs);

    void showClientCreatedSuccessfully(int s);

    void showMessage(int message);

    void showMessage(String message);
}
