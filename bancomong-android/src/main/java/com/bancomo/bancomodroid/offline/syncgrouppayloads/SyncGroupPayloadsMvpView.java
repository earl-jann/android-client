package com.bancomo.bancomodroid.offline.syncgrouppayloads;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.group.GroupPayload;

import java.util.List;

/**
 * Created by Rajan Maurya on 19/07/16.
 */
public interface SyncGroupPayloadsMvpView extends MvpView {

    void showOfflineModeDialog();

    void showGroups(List<GroupPayload> groupPayloads);

    void showGroupSyncResponse();

    void showGroupSyncFailed(String errorMessage);

    void showPayloadDeletedAndUpdatePayloads(List<GroupPayload> groupPayloads);

    void showGroupPayloadUpdated(GroupPayload groupPayload);

    void showError(int stringId);
}
