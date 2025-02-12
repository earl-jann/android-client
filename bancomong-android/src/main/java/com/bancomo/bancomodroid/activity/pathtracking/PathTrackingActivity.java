/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.activity.pathtracking;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.adapters.PathTrackingAdapter;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.core.RecyclerItemClickListener;
import com.bancomo.objects.user.UserLatLng;
import com.bancomo.objects.user.UserLocation;
import com.bancomo.utils.CheckSelfPermissionAndRequest;
import com.bancomo.utils.Constants;
import com.bancomo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author fomenkoo
 */
public class PathTrackingActivity extends BancoMoBaseActivity implements PathTrackingMvpView,
        SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.rv_path_tacker)
    RecyclerView rvPathTracker;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_no_path_track)
    TextView tvNoPathTracker;

    @BindView(R.id.ll_error)
    LinearLayout llError;

    @BindView(R.id.iv_no_path_track)
    ImageView ivNoPathTracker;

    @BindView(R.id.pb_path_tracking)
    ProgressBar progressBar;

    @Inject
    PathTrackingPresenter pathTrackingPresenter;

    @Inject
    PathTrackingAdapter pathTrackingAdapter;

    private Intent intentLocationService;
    private BroadcastReceiver notificationReceiver;
    private List<UserLocation> userLocations;

    @Override
    public void onItemClick(View childView, int position) {
        List<UserLatLng> userLatLngs =
                pathTrackingAdapter.getLatLngList(userLocations.get(position).getLatlng());
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="
                + userLatLngs.get(0).getLat() + "," + userLatLngs.get(0).getLng() + "&daddr="
                + userLatLngs.get(userLatLngs.size() - 1).getLat() + "," + ""
                + userLatLngs.get(userLatLngs.size() - 1).getLng();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(Intent.createChooser(intent, getString(R.string.start_tracking)));
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_tracker);
        getActivityComponent().inject(this);
        pathTrackingPresenter.attachView(this);
        ButterKnife.bind(this);
        showBackButton();
        intentLocationService = new Intent(this, PathTrackingService.class);
        createNotificationReceiver();

        showUserInterface();
        pathTrackingPresenter.loadPathTracking(PrefManager.getUserId());
    }

    @Override
    public void showUserInterface() {
        userLocations = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pathTrackingAdapter.setContext(this);
        rvPathTracker.setLayoutManager(mLayoutManager);
        rvPathTracker.setHasFixedSize(true);
        rvPathTracker.setAdapter(pathTrackingAdapter);
        rvPathTracker.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        swipeRefreshLayout.setColorSchemeColors(this
                .getResources().getIntArray(R.array.swipeRefreshColors));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        pathTrackingPresenter.loadPathTracking(PrefManager.getUserId());
    }

    @Override
    public void showPathTracking(List<UserLocation> userLocations) {
        this.userLocations = userLocations;
        llError.setVisibility(View.GONE);
        pathTrackingAdapter.setPathTracker(userLocations);
    }

    @Override
    public void showEmptyPathTracking() {
        llError.setVisibility(View.VISIBLE);
        tvNoPathTracker.setText(getString(R.string.empty_path_tracking));
        ivNoPathTracker.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp);
    }

    @Override
    public void showError() {
        llError.setVisibility(View.VISIBLE);
        tvNoPathTracker.setText(getString(R.string.failed_to_fetch_path_tracking_details));
        ivNoPathTracker.setImageResource(R.drawable.ic_error_black_24dp);
    }

    /**
     * This Method Checking the Permission ACCESS_FINE_LOCATION is granted or not.
     * If not then prompt user a dialog to grant the ACCESS_FINE_LOCATION permission.
     * and If Permission is granted already then start Intent to get the
     */
    public boolean checkPermissionAndRequest() {
        if (CheckSelfPermissionAndRequest.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        } else {
            requestPermission();
            return false;
        }
    }

    /**
     * This Method is Requesting the Permission
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void requestPermission() {
        CheckSelfPermissionAndRequest.requestPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION,
                getResources().getString(
                        R.string.dialog_message_access_fine_location_permission_denied),
                getResources().getString(
                        R.string.dialog_message_permission_never_ask_again_fine_location),
                Constants.ACCESS_FINE_LOCATION_STATUS);
    }

    /**
     * This Method getting the Response after User Grant or denied the Permission
     *
     * @param requestCode  Request Code
     * @param permissions  Permission
     * @param grantResults GrantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    startService(intentLocationService);
                } else {
                    // permission denied
                    Toast.makeText(getApplicationContext(), getResources()
                                    .getString(R.string.permission_denied_to_access_fine_location) ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_path_track, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_start_path_track:
                if (checkPermissionAndRequest()) {
                    startService(intentLocationService);
                    PrefManager.putBoolean(Constants.SERVICE_STATUS, true);
                    invalidateOptionsMenu();
                }
                return true;
            case R.id.menu_stop_path_track:
                stopService(intentLocationService);
                PrefManager.putBoolean(Constants.SERVICE_STATUS, false);
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_start_path_track)
                .setVisible(!PrefManager.getBoolean(Constants.SERVICE_STATUS, false));
        menu.findItem(R.id.menu_stop_path_track)
                .setVisible(PrefManager.getBoolean(Constants.SERVICE_STATUS, false));
        return true;
    }

    public void createNotificationReceiver() {
        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Constants.STOP_TRACKING.equals(action)) {
                    invalidateOptionsMenu();
                    pathTrackingPresenter.loadPathTracking(PrefManager.getUserId());
                }
            }
        };
        registerReceiver(notificationReceiver, new IntentFilter(Constants.STOP_TRACKING));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pathTrackingPresenter.detachView();
        unregisterReceiver(notificationReceiver);
    }


    @Override
    public void showProgressbar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
        if (show && userLocations.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
