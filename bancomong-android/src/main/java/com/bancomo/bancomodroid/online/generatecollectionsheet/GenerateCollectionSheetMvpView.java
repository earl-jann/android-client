package com.bancomo.bancomodroid.online.generatecollectionsheet;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.group.Center;
import com.bancomo.objects.group.CenterWithAssociations;
import com.bancomo.objects.group.Group;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.organisation.Staff;

import java.util.List;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface GenerateCollectionSheetMvpView extends MvpView {

    void showOffices(List<Office> offices);

    void showStaffInOffice(List<Staff> staffs, int officeId);

    void showCentersInOffice(List<Center> centers);

    void showGroupsInOffice(List<Group> groups);

    void showGroupByCenter(CenterWithAssociations centerWithAssociations);

    void showFetchingError(String s);
}
