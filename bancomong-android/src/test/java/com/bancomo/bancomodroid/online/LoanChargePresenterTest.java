package com.bancomo.bancomodroid.online;

import com.bancomo.api.DataManager;
import com.bancomo.bancomodroid.FakeRemoteDataSource;
import com.bancomo.bancomodroid.online.loancharge.LoanChargeMvpView;
import com.bancomo.bancomodroid.online.loancharge.LoanChargePresenter;
import com.bancomo.bancomodroid.util.RxSchedulersOverrideRule;
import com.bancomo.objects.client.Charges;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Rajan Maurya on 20/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanChargePresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    LoanChargePresenter mLoanChargePresenter;

    @Mock
    DataManager mDataManager;

    @Mock
    LoanChargeMvpView mLoanChargeMvpView;

    List<Charges> chargesList;

    int loanId = 441;

    @Before
    public void setUp() throws Exception {
        mLoanChargePresenter = new LoanChargePresenter(mDataManager);
        mLoanChargePresenter.attachView(mLoanChargeMvpView);

        chargesList = FakeRemoteDataSource.getLoanCharges();
    }

    @After
    public void tearDown() throws Exception {
        mLoanChargePresenter.detachView();
    }

    @Test
    public void testLoadLoanChargesList() throws Exception {
        when(mDataManager.getListOfLoanCharges(loanId))
                .thenReturn(Observable.just(chargesList));

        mLoanChargePresenter.loadLoanChargesList(loanId);

        verify(mLoanChargeMvpView).showLoanChargesList(chargesList);
        verify(mLoanChargeMvpView, never()).showFetchingError("Failed to load Charges");
    }

    @Test
    public void testLoadChargesFails() {

        when(mDataManager.getListOfLoanCharges(loanId))
                .thenReturn(Observable.<List<Charges>>error(new RuntimeException()));

        mLoanChargePresenter.loadLoanChargesList(loanId);

        verify(mLoanChargeMvpView).showFetchingError("Failed to load Charges");
        verify(mLoanChargeMvpView, never()).showLoanChargesList(chargesList);
    }
}