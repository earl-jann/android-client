package com.bancomo.bancomodroid.online.centerdetails;

import com.bancomo.api.datamanager.DataManagerCenter;
import com.bancomo.api.datamanager.DataManagerRunReport;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.base.BasePresenter;
import com.bancomo.objects.group.CenterInfo;
import com.bancomo.objects.group.CenterWithAssociations;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Rajan Maurya on 05/02/17.
 */

public class CenterDetailsPresenter extends BasePresenter<CenterDetailsMvpView> {

    private final DataManagerCenter dataManagerCenter;
    private final DataManagerRunReport dataManagerRunReport;
    private CompositeSubscription subscriptions;

    @Inject
    public CenterDetailsPresenter(DataManagerCenter dataManagerCenter,
                                  DataManagerRunReport dataManagerRunReport) {
        this.dataManagerCenter = dataManagerCenter;
        this.dataManagerRunReport = dataManagerRunReport;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(CenterDetailsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        subscriptions.clear();
    }

    public void loadCentersGroupAndMeeting(final int centerId) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        subscriptions.add(dataManagerCenter.getCentersGroupAndMeeting(centerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CenterWithAssociations>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showErrorMessage(R.string.failed_to_fetch_Group_and_meeting);
                    }

                    @Override
                    public void onNext(CenterWithAssociations centerWithAssociations) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMeetingDetails(centerWithAssociations);
                        getMvpView().showCenterDetails(centerWithAssociations);
                    }
                }));
    }

    public void loadSummaryInfo(int centerId) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        subscriptions.add(dataManagerRunReport.getCenterSummarInfo(centerId, false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<CenterInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showErrorMessage(R.string.failed_to_fetch_center_info);
                    }

                    @Override
                    public void onNext(List<CenterInfo> centerInfos) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showSummaryInfo(centerInfos);
                    }
                }));
    }
}
