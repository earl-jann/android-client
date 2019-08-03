package com.bancomo.bancomodroid.activity.pathtracking;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.user.UserLocation;

import java.util.List;

/**
 * Created by Rajan Maurya on 24/01/17.
 */

public interface PathTrackingMvpView extends MvpView {

    void showUserInterface();

    void showPathTracking(List<UserLocation> userLocations);

    void showEmptyPathTracking();

    void showError();

}
