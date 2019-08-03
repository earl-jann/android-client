package com.bancomo.bancomodroid.online.datatablelistfragment;

import com.bancomo.api.DataManager;
import com.bancomo.api.datamanager.DataManagerClient;
import com.bancomo.api.datamanager.DataManagerLoan;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.base.BasePresenter;
import com.bancomo.objects.accounts.loan.Loans;
import com.bancomo.objects.client.Client;
import com.bancomo.objects.client.ClientPayload;
import com.bancomo.services.data.GroupLoanPayload;
import com.bancomo.services.data.LoansPayload;
import com.bancomo.utils.MFErrorParser;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DataTableListPresenter extends BasePresenter<DataTableListMvpView> {

    private final DataManagerLoan mDataManagerLoan;
    private final DataManager mDataManager;
    private final DataManagerClient dataManagerClient;
    private CompositeSubscription mSubscription;

    @Inject
    public DataTableListPresenter(DataManagerLoan dataManager, DataManager manager,
                                  DataManagerClient dataManagerClient) {
        mDataManagerLoan = dataManager;
        mDataManager = manager;
        this.dataManagerClient = dataManagerClient;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(DataTableListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void createLoansAccount(LoansPayload loansPayload) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        mSubscription.add(mDataManagerLoan.createLoansAccount(loansPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Loans>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMessage(R.string.generic_failure_message);
                    }

                    @Override
                    public void onNext(Loans loans) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMessage(R.string.loan_creation_success);

                    }
                }));
    }

    public void createGroupLoanAccount(GroupLoanPayload loansPayload) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        mSubscription.add(mDataManager.createGroupLoansAccount(loansPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Loans>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMessage(R.string.generic_failure_message);
                    }

                    @Override
                    public void onNext(Loans loans) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMessage(R.string.loan_creation_success);
                    }
                })
        );
    }

    public void createClient(ClientPayload clientPayload) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        mSubscription.add(dataManagerClient.createClient(clientPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Client>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showMessage(MFErrorParser.errorMessage(e));
                    }

                    @Override
                    public void onNext(Client client) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showClientCreatedSuccessfully(client);
                    }
                }));
    }

}
