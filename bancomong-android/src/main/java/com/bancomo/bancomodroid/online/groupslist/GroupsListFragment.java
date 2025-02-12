/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.bancomodroid.online.groupslist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.adapters.GroupNameListAdapter;
import com.bancomo.bancomodroid.core.EndlessRecyclerViewScrollListener;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.BancoMoBaseFragment;
import com.bancomo.bancomodroid.core.RecyclerItemClickListener;
import com.bancomo.bancomodroid.core.util.Toaster;
import com.bancomo.bancomodroid.dialogfragments.syncgroupsdialog.SyncGroupsDialogFragment;
import com.bancomo.bancomodroid.online.GroupsActivity;
import com.bancomo.bancomodroid.online.createnewgroup.CreateNewGroupFragment;
import com.bancomo.objects.group.Group;
import com.bancomo.utils.Constants;
import com.bancomo.utils.FragmentConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nellyk on 2/27/2016.
 * <p/>
 * This class loading and showing groups, Here is two way to load the Groups. First one to load
 * Groups from Rest API
 * <p/>
 * </>demo.openmf.org/bancomo-provider/api/v1/groups?paged=true&offset=offset_value&limit
 * =limit_value</>
 * <p/>
 * Offset : From Where index, Groups will be fetch.
 * limit : Total number of client, need to fetch
 * <p/>
 * and showing in the GroupList.
 * <p/>
 * and Second one is showing Groups provided by Parent(Fragment or Activity).
 * Parent(Fragment or Activity) load the GroupList and send the
 * Groups to GroupsListFragment newInstance(List<Group> groupList,
 * boolean isParentFragment) {...}
 * and unregister the ScrollListener and SwipeLayout.
 */
public class GroupsListFragment extends BancoMoBaseFragment implements GroupsListMvpView,
        RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_groups)
    RecyclerView rv_groups;

    @BindView(R.id.progressbar_group)
    ProgressBar pb_groups;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.noGroupsText)
    TextView mNoGroupsText;

    @BindView(R.id.noGroupsIcon)
    ImageView mNoGroupIcon;

    @BindView(R.id.rl_error)
    RelativeLayout rlError;

    @Inject
    GroupsListPresenter mGroupsListPresenter;

    @Inject
    GroupNameListAdapter mGroupListAdapter;

    LinearLayoutManager mLayoutManager;
    private List<Group> mGroupList;
    private List<Group> selectedGroups;
    private Boolean isParentFragment = false;
    private View rootView;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    /**
     * This method will be called, whenever GroupsListFragment will not have Parent Fragment.
     * So, Presenter make the call to Rest API and fetch the Client List and show in UI
     *
     * @return GroupsListFragment
     */
    public static GroupsListFragment newInstance() {
        Bundle arguments = new Bundle();
        GroupsListFragment groupListFragment = new GroupsListFragment();
        groupListFragment.setArguments(arguments);
        return groupListFragment;
    }

    /**
     * This Method will be called, whenever isParentFragment will be true
     * and Presenter do not need to make Rest API call to server. Parent (Fragment or Activity)
     * already fetched the groups and for showing, they call GroupsListFragment.
     * <p/>
     * Example : Showing Parent Groups.
     *
     * @param groupList        List<Group>
     * @param isParentFragment true
     * @return GroupsListFragment
     */
    public static GroupsListFragment newInstance(List<Group> groupList,
                                                 boolean isParentFragment) {
        GroupsListFragment groupListFragment = new GroupsListFragment();
        Bundle args = new Bundle();
        if (isParentFragment && groupList != null) {
            args.putParcelableArrayList(Constants.GROUPS,
                    (ArrayList<? extends Parcelable>) groupList);
            args.putBoolean(Constants.IS_A_PARENT_FRAGMENT, true);
            groupListFragment.setArguments(args);
        }
        return groupListFragment;
    }

    @Override
    public void onItemClick(View childView, int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {
            Intent groupActivityIntent = new Intent(getActivity(), GroupsActivity.class);
            groupActivityIntent.putExtra(Constants.GROUP_ID, mGroupList.get(position).getId());
            startActivity(groupActivityIntent);
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        if (actionMode == null) {
            actionMode = ((BancoMoBaseActivity) getActivity()).startSupportActionMode
                    (actionModeCallback);
        }
        toggleSelection(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BancoMoBaseActivity) getActivity()).getActivityComponent().inject(this);
        mGroupList = new ArrayList<>();
        selectedGroups = new ArrayList<>();
        actionModeCallback = new ActionModeCallback();
        if (getArguments() != null) {
            mGroupList = getArguments().getParcelableArrayList(Constants.GROUPS);
            isParentFragment = getArguments()
                    .getBoolean(Constants.IS_A_PARENT_FRAGMENT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_groups, container, false);

        ButterKnife.bind(this, rootView);
        mGroupsListPresenter.attachView(this);

        //setting all the UI content to the view
        showUserInterface();

        /**
         * This is the LoadMore of the RecyclerView. It called When Last Element of RecyclerView
         * is shown on the Screen.
         */
        rv_groups.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mGroupsListPresenter.loadGroups(true, totalItemsCount);
            }
        });

        /**
         * First Check the Parent Fragment is true or false. If parent fragment is true then no
         * need to fetch groupList from Rest API, just need to show parent fragment groupList
         * and if Parent Fragment is false then Presenter make the call to Rest API and fetch the
         * Group List to show. and Presenter make transaction to Database to load saved clients.
         * To show user that is there already any group is synced already or not.
         */
        if (isParentFragment) {
            mGroupsListPresenter.showParentClients(mGroupList);
        } else {
            mGroupsListPresenter.loadGroups(false, 0);
        }
        mGroupsListPresenter.loadDatabaseGroups();

        return rootView;
    }

    /**
     * This method Initializing the UI.
     */
    @Override
    public void showUserInterface() {
        setToolbarTitle(getResources().getString(R.string.groups));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_groups.setLayoutManager(mLayoutManager);
        rv_groups.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        rv_groups.setHasFixedSize(true);
        mGroupListAdapter.setContext(getActivity());
        rv_groups.setAdapter(mGroupListAdapter);
        swipeRefreshLayout.setColorSchemeColors(getActivity()
                .getResources().getIntArray(R.array.swipeRefreshColors));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @OnClick(R.id.fab_create_group)
    void onClickCreateNewGroup() {
        ((BancoMoBaseActivity) getActivity()).replaceFragment(CreateNewGroupFragment.newInstance(),
                true, R.id.container);
    }

    /**
     * This Method will be called. Whenever user will swipe down to refresh the group list.
     */
    @Override
    public void onRefresh() {
        mGroupsListPresenter.loadGroups(false, 0);
        mGroupsListPresenter.loadDatabaseGroups();
        if (actionMode != null) actionMode.finish();
    }

    /**
     * This method will be called, whenever first time error occurred during the fetching group
     * list from REST API.
     * As the error will occurred. user is able to see the error message and ability to reload
     * groupList.
     */
    @OnClick(R.id.noGroupsIcon)
    public void reloadOnError() {
        rlError.setVisibility(View.GONE);
        rv_groups.setVisibility(View.VISIBLE);
        mGroupsListPresenter.loadGroups(false, 0);
        mGroupsListPresenter.loadDatabaseGroups();
    }

    /**
     * Setting GroupList to the Adapter and updating the Adapter.
     */
    @Override
    public void showGroups(List<Group> groups) {
        mGroupList = groups;
        mGroupListAdapter.setGroups(groups);
    }


    /**
     * Adding the More Groups in List and Update the Adapter.
     *
     * @param groups
     */
    @Override
    public void showLoadMoreGroups(List<Group> groups) {
        mGroupList.addAll(groups);
        mGroupListAdapter.notifyDataSetChanged();
    }

    /**
     * This method will be called, if fetched groupList is Empty and show there is no Group to show.
     *
     * @param message String Message.
     */
    @Override
    public void showEmptyGroups(int message) {
        rv_groups.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        mNoGroupsText.setText(getStringMessage(message));
    }

    /**
     * This Method unregistered the SwipeLayout and OnScrollListener
     */
    @Override
    public void unregisterSwipeAndScrollListener() {
        rv_groups.clearOnScrollListeners();
        swipeRefreshLayout.setEnabled(false);
        mNoGroupIcon.setEnabled(false);
    }

    /**
     * This Method showing the Simple Taster Message to user.
     *
     * @param message String Message to show.
     */
    @Override
    public void showMessage(int message) {
        Toaster.show(rootView, getStringMessage(message));
    }


    /**
     * If Any any exception occurred during fetching the Groups. like No Internet or etc.
     * then this method show the error message to user and give the ability to refresh groups.
     */
    @Override
    public void showFetchingError() {
        rv_groups.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        String errorMessage = getStringMessage(R.string.failed_to_fetch_groups)
                + getStringMessage(R.string.new_line) + getStringMessage(R.string.click_to_refresh);
        mNoGroupsText.setText(errorMessage);
    }


    /**
     * This Method showing the Progressbar during fetching the group List on first time and
     * otherwise showing swipe refresh layout
     *
     * @param show Status of Progressbar or SwipeRefreshLayout
     */
    @Override
    public void showProgressbar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
        if (show && mGroupListAdapter.getItemCount() == 0) {
            pb_groups.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        } else {
            pb_groups.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGroupsListPresenter.detachView();
        //As the Fragment Detach Finish the ActionMode
        if (actionMode != null) actionMode.finish();
    }

    /**
     * Toggle the selection state of an item.
     * <p/>
     * If the item was the last one in the selection and is unselected, then selection will stopped.
     * Note that the selection must already be started (actionMode must not be null).
     *
     * @param position Position of the item to toggle the selection state
     */
    private void toggleSelection(int position) {
        mGroupListAdapter.toggleSelection(position);
        int count = mGroupListAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    /**
     * This ActionModeCallBack Class handling the User Event after the Selection of Clients. Like
     * Click of Menu Sync Button and finish the ActionMode
     */
    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String LOG_TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_sync, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_sync:

                    selectedGroups.clear();
                    for (Integer position : mGroupListAdapter.getSelectedItems()) {
                        selectedGroups.add(mGroupList.get(position));
                    }

                    SyncGroupsDialogFragment syncGroupsDialogFragment =
                            SyncGroupsDialogFragment.newInstance(selectedGroups);
                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(FragmentConstants.FRAG_GROUP_SYNC);
                    syncGroupsDialogFragment.setCancelable(false);
                    syncGroupsDialogFragment.show(fragmentTransaction,
                            getResources().getString(R.string.sync_groups));
                    mode.finish();

                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mGroupListAdapter.clearSelection();
            actionMode = null;
        }
    }
}

