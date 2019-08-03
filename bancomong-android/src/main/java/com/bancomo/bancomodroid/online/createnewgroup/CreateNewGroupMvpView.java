package com.bancomo.bancomodroid.online.createnewgroup;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.response.SaveResponse;

import java.util.List;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface CreateNewGroupMvpView extends MvpView {

    void showOffices(List<Office> offices);

    void showGroupCreatedSuccessfully(SaveResponse group);

    void showFetchingError(String s);
}
