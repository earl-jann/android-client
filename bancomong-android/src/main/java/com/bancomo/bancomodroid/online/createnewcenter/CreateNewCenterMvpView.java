package com.bancomo.bancomodroid.online.createnewcenter;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.response.SaveResponse;

import java.util.List;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface CreateNewCenterMvpView extends MvpView {

    void showOffices(List<Office> offices);

    void centerCreatedSuccessfully(SaveResponse saveResponse);

    void showFetchingError(int errorMessage);

    void showFetchingError(String s);
}
