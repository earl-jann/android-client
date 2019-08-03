package com.bancomo.bancomodroid.online.clientidentifiers;

import com.bancomo.api.GenericResponse;
import com.bancomo.api.datamanager.DataManagerClient;
import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.base.BasePresenter;
import com.bancomo.objects.noncore.Identifier;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public class ClientIdentifiersPresenter extends BasePresenter<ClientIdentifiersMvpView> {

    private final DataManagerClient mDataManagerClient;
    private CompositeSubscription mSubscriptions;

    @Inject
    public ClientIdentifiersPresenter(DataManagerClient dataManagerClient) {
        mDataManagerClient = dataManagerClient;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(ClientIdentifiersMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.clear();
    }

    public void loadIdentifiers(int clientId) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        mSubscriptions.add(mDataManagerClient.getClientIdentifiers(clientId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Identifier>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showFetchingError(R.string.failed_to_fetch_identifier);
                    }

                    @Override
                    public void onNext(List<Identifier> identifiers) {
                        getMvpView().showProgressbar(false);
                        if (!identifiers.isEmpty()) {
                            getMvpView().showClientIdentifiers(identifiers);
                        } else {
                            getMvpView().showEmptyClientIdentifier();
                        }
                    }
                }));
    }

    public void deleteIdentifier(final int clientId, int identifierId) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        mSubscriptions.add(mDataManagerClient.deleteClientIdentifier(clientId, identifierId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GenericResponse>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showFetchingError(R.string.failed_to_delete_identifier);
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        getMvpView().identifierDeletedSuccessfully();
                        getMvpView().showProgressbar(false);
                    }
                }));
    }

}