package com.bancomo.bancomodroid.online;

import com.bancomo.api.datamanager.DataManagerGroups;
import com.bancomo.bancomodroid.FakeRemoteDataSource;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.online.groupslist.GroupsListMvpView;
import com.bancomo.bancomodroid.online.groupslist.GroupsListPresenter;
import com.bancomo.bancomodroid.util.RxSchedulersOverrideRule;
import com.bancomo.objects.client.Client;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.group.Group;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Rajan Maurya on 28/6/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupsListPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    GroupsListPresenter mGroupsListPresenter;

    @Mock
    DataManagerGroups mDataManagerGroups;

    @Mock
    GroupsListMvpView mGroupsListMvpView;

    Page<Group> groupPage;

    int offset = 0;
    int limit = 100;

    @Before
    public void setUp() {

        mGroupsListPresenter = new GroupsListPresenter(mDataManagerGroups);
        mGroupsListPresenter.attachView(mGroupsListMvpView);

        groupPage = FakeRemoteDataSource.getGroups();

    }

    @After
    public void tearDown() {
        mGroupsListPresenter.detachView();
    }

    @Test
    public void testLoadGroup() {

        stubDatabaseGetAllGroups(Observable.just(groupPage));

        mGroupsListPresenter.loadDatabaseGroups();

        mGroupsListPresenter.setAlreadyClientSyncStatus();
        verify(mGroupsListMvpView, never()).showMessage(R.string.failed_to_load_db_groups);

        stubDataManagerGetGroups(Observable.just(groupPage));

        mGroupsListPresenter.loadGroups(false, offset);

        verify(mGroupsListMvpView).showProgressbar(true);
        verify(mGroupsListMvpView).showGroups(groupPage.getPageItems());
        verify(mGroupsListMvpView, never()).showFetchingError();
        verify(mGroupsListMvpView).showProgressbar(false);
    }

    @Test
    public void testLoadGroupFails() {

        stubDataManagerGetGroups(Observable.<Page<Client>>error(new RuntimeException()));

        mGroupsListPresenter.loadGroups(false, offset);
        verify(mGroupsListMvpView).showFetchingError();
        verify(mGroupsListMvpView, never()).showGroups(groupPage.getPageItems());
    }

    @Test
    public void testLoadMoreGroups() {

        stubDatabaseGetAllGroups(Observable.just(groupPage));

        mGroupsListPresenter.loadDatabaseGroups();

        mGroupsListPresenter.setAlreadyClientSyncStatus();
        verify(mGroupsListMvpView, never()).showMessage(R.string.failed_to_load_db_groups);

        stubDataManagerGetGroups(Observable.just(groupPage));

        mGroupsListPresenter.loadGroups(true, offset);

        verify(mGroupsListMvpView).showProgressbar(true);
        verify(mGroupsListMvpView).showLoadMoreGroups(groupPage.getPageItems());
        verify(mGroupsListMvpView, never()).showFetchingError();
        verify(mGroupsListMvpView).showProgressbar(false);
    }

    @Test
    public void testLoadMoreGroupFails() {

        stubDataManagerGetGroups(Observable.<Page<Client>>error(new RuntimeException()));

        mGroupsListPresenter.loadGroups(true, offset);
        verify(mGroupsListMvpView).showMessage(R.string.failed_to_fetch_groups);
        verify(mGroupsListMvpView, never()).showGroups(groupPage.getPageItems());
    }

    @Test
    public void testEmptyGroupList() {

        stubDataManagerGetGroups(Observable.just(new Page<Client>()));

        mGroupsListPresenter.loadGroups(false, offset);

        verify(mGroupsListMvpView).showProgressbar(true);
        verify(mGroupsListMvpView).showEmptyGroups(R.string.empty_groups_list);
        verify(mGroupsListMvpView).unregisterSwipeAndScrollListener();
        verify(mGroupsListMvpView, never()).showFetchingError();
        verify(mGroupsListMvpView).showProgressbar(false);
    }

    @Test
    public void testNoMoreGroupsAvailable() {

        stubDataManagerGetGroups(Observable.just(new Page<Client>()));

        mGroupsListPresenter.loadGroups(true, offset);

        verify(mGroupsListMvpView).showProgressbar(true);
        verify(mGroupsListMvpView).showMessage(R.string.no_more_groups_available);
        verify(mGroupsListMvpView, never()).showFetchingError();
        verify(mGroupsListMvpView).showProgressbar(false);
    }

    @Test
    public void testLoadDatabaseGroups() {
        stubDatabaseGetAllGroups(Observable.just(groupPage));

        mGroupsListPresenter.loadDatabaseGroups();
        mGroupsListPresenter.setAlreadyClientSyncStatus();
        verify(mGroupsListMvpView, never()).showMessage(R.string.failed_to_load_db_groups);
    }

    @Test
    public void testLoadDatabaseGroupsFails() {
        stubDatabaseGetAllGroups(Observable.<Page<Client>>error(new RuntimeException()));

        mGroupsListPresenter.loadDatabaseGroups();

        verify(mGroupsListMvpView).showMessage(R.string.failed_to_load_db_groups);
    }

    public void stubDataManagerGetGroups(Observable observable) {
        when(mDataManagerGroups.getGroups(true, offset, limit)).thenReturn(observable);
    }

    public void stubDatabaseGetAllGroups(Observable observable) {
        when(mDataManagerGroups.getDatabaseGroups()).thenReturn(observable);
    }

}