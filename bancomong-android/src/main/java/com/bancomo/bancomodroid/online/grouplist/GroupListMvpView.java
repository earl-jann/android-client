package com.bancomo.bancomodroid.online.grouplist;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.group.CenterWithAssociations;
import com.bancomo.objects.group.GroupWithAssociations;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface GroupListMvpView extends MvpView {

    void showGroupList(CenterWithAssociations centerWithAssociations);

    void showFetchingError(String s);

    void showEmptyGroups(int messageId);

    void showGroups(GroupWithAssociations groupWithAssociations);
}
