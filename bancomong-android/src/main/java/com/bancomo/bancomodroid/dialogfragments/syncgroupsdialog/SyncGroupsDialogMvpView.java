package com.bancomo.bancomodroid.dialogfragments.syncgroupsdialog;

import com.bancomo.bancomodroid.base.MvpView;

/**
 * Created by Rajan Maurya on 11/09/16.
 */
public interface SyncGroupsDialogMvpView extends MvpView {

    void showUI();

    void showSyncingGroup(String clientName);

    void showSyncedFailedGroups(int failedCount);

    void setMaxSingleSyncGroupProgressBar(int total);

    void setClientSyncProgressBarMax(int count);

    void updateClientSyncProgressBar(int i);

    void updateSingleSyncGroupProgressBar(int i);

    void updateTotalSyncGroupProgressBarAndCount(int i);

    int getMaxSingleSyncGroupProgressBar();

    void showNetworkIsNotAvailable();

    void showGroupsSyncSuccessfully();

    Boolean isOnline();

    void dismissDialog();

    void showDialog();

    void hideDialog();

    void showError(int s);
}
