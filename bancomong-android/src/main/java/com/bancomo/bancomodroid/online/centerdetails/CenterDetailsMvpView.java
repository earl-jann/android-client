package com.bancomo.bancomodroid.online.centerdetails;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.group.CenterInfo;
import com.bancomo.objects.group.CenterWithAssociations;

import java.util.List;

/**
 * Created by Rajan Maurya on 05/02/17.
 */

public interface CenterDetailsMvpView extends MvpView {

    void showProgressbar(boolean show);

    void showCenterDetails(CenterWithAssociations centerWithAssociations);

    void showMeetingDetails(CenterWithAssociations centerWithAssociations);

    void showSummaryInfo(List<CenterInfo> centerInfos);

    void showErrorMessage(int message);
}
