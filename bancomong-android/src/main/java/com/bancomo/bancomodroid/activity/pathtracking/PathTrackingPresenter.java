package com.bancomo.bancomodroid.activity.pathtracking;

import com.bancomo.api.datamanager.DataManagerDataTable;
import com.bancomo.bancomodroid.base.BasePresenter;
import com.bancomo.objects.user.UserLocation;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Rajan Maurya on 24/01/17.
 */

public class PathTrackingPresenter extends BasePresenter<PathTrackingMvpView> {

    private final DataManagerDataTable dataManagerDataTable;
    private CompositeSubscription subscriptions;

    @Inject
    public PathTrackingPresenter(DataManagerDataTable dataManagerDataTable) {
        this.dataManagerDataTable = dataManagerDataTable;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(PathTrackingMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        subscriptions.clear();
    }

    public void loadPathTracking(int userId) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        subscriptions.add(dataManagerDataTable.getUserPathTracking(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<UserLocation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onNext(List<UserLocation> userLocations) {
                        getMvpView().showProgressbar(false);
                        if (!userLocations.isEmpty()) {
                            getMvpView().showPathTracking(userLocations);
                        } else {
                            getMvpView().showEmptyPathTracking();
                        }
                    }
                })
        );
    }
}
