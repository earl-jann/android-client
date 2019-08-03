package com.bancomo.bancomodroid.online.centerlist;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.group.Center;
import com.bancomo.objects.group.CenterWithAssociations;

import java.util.List;

/**
 * Created by Rajan Maurya on 5/6/16.
 */
public interface CenterListMvpView extends MvpView {

    void showUserInterface();

    void showCenters(List<Center> centers);

    void showMoreCenters(List<Center> centers);

    void showEmptyCenters(int message);

    void showMessage(int message);

    void showCentersGroupAndMeeting(CenterWithAssociations centerWithAssociations, int id);

    void showFetchingError();
}
