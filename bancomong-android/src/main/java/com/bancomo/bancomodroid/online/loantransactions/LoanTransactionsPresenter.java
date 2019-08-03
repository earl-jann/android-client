package com.bancomo.bancomodroid.online.loantransactions;

import com.bancomo.api.DataManager;
import com.bancomo.bancomodroid.base.BasePresenter;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rajan Maurya on 7/6/16.
 */
public class LoanTransactionsPresenter extends BasePresenter<LoanTransactionsMvpView> {


    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public LoanTransactionsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoanTransactionsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadLoanTransaction(int loan) {
        checkViewAttached();
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = mDataManager.getLoanTransactions(loan)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LoanWithAssociations>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showFetchingError("Failed to fetch LoanTransaction");
                    }

                    @Override
                    public void onNext(LoanWithAssociations loanWithAssociations) {
                        getMvpView().showLoanTransaction(loanWithAssociations);
                    }
                });
    }

}
