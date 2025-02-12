package com.bancomo.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bancomo.bancomodroid.R;
import com.bancomo.objects.PaymentTypeOption;
import com.bancomo.objects.accounts.loan.LoanAccount;
import com.bancomo.objects.accounts.savings.SavingsAccount;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Rajan Maurya on 18/08/16.
 */
public class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * This Method transforming the PaymentTypeOption to String(PaymentTypeName).
     * In Which Observable.from takes the List<PaymentTypeOptions> and flatmap the
     * PaymentTypeOptions one by one. finally returns the List<String> paymentTypeNames
     *
     * @param paymentTypeOptions List<PaymentTypeOption>
     * @return List<String>
     */
    public static List<String> getPaymentTypeOptions(List<PaymentTypeOption> paymentTypeOptions) {
        final List<String> paymentOptions = new ArrayList<>();
        Observable.from(paymentTypeOptions)
                .flatMap(new Func1<PaymentTypeOption, Observable<String>>() {
                    @Override
                    public Observable<String> call(PaymentTypeOption paymentTypeOption) {
                        return Observable.just(paymentTypeOption.getName());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        paymentOptions.add(s);
                    }
                });
        return paymentOptions;
    }

    /**
     * This Method filtering the List<PaymentTypeOption> and if any PaymentTypeOption id is equal to
     * the paymentId. and return the match PaymentType Name.
     *
     * @param paymentId Payment Type Id
     * @param paymentTypeOptions PaymentTypeOptions
     * @return PaymentType Name
     */
    public static String getPaymentTypeName(final int paymentId,
                                     List<PaymentTypeOption> paymentTypeOptions) {
        final String[] paymentTypeName = new String[1];
        Observable.from(paymentTypeOptions)
                .filter(new Func1<PaymentTypeOption, Boolean>() {
                    @Override
                    public Boolean call(PaymentTypeOption paymentTypeOption) {
                        return (paymentTypeOption.getId() == paymentId);
                    }
                })
                .subscribe(new Action1<PaymentTypeOption>() {
                    @Override
                    public void call(PaymentTypeOption paymentTypeOption) {
                        paymentTypeName[0] = paymentTypeOption.getName();
                    }
                });
        return paymentTypeName[0];
    }

    public static List<LoanAccount> getActiveLoanAccounts(List<LoanAccount> loanAccountList) {
        final List<LoanAccount> loanAccounts = new ArrayList<>();
        Observable.from(loanAccountList)
                .filter(new Func1<LoanAccount, Boolean>() {
                    @Override
                    public Boolean call(LoanAccount loanAccount) {
                        return loanAccount.getStatus().getActive();
                    }
                })
                .subscribe(new Action1<LoanAccount>() {
                    @Override
                    public void call(LoanAccount loanAccount) {
                        loanAccounts.add(loanAccount);
                    }
                });
        return loanAccounts;
    }

    public static List<SavingsAccount> getActiveSavingsAccounts(List<SavingsAccount>
                                                                        savingsAccounts) {
        final List<SavingsAccount> accounts = new ArrayList<>();
        Observable.from(savingsAccounts)
                .filter(new Func1<SavingsAccount, Boolean>() {
                    @Override
                    public Boolean call(SavingsAccount savingsAccount) {
                        return (savingsAccount.getStatus().getActive() &&
                                !savingsAccount.isRecurring());
                    }
                })
                .subscribe(new Action1<SavingsAccount>() {
                    @Override
                    public void call(SavingsAccount savingsAccount) {
                        accounts.add(savingsAccount)
                        ;
                    }
                });
        return accounts;
    }

    /**
     * This Method Converting the List<Integer> of Activation Date to String.
     *
     * @param context Context
     * @param dateObj List<Integer> of Date
     * @return
     */
    public static String getStringOfDate(Context context, List<Integer> dateObj) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy",
                context.getResources().getConfiguration().locale);
        Date date = null;
        try {
            date = simpleDateFormat.parse(DateHelper.getDateAsString(dateObj));
        } catch (ParseException e) {
            Log.d(LOG_TAG, e.getLocalizedMessage());
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return df.format(date);
    }

    public static LayerDrawable setCircularBackground(int colorId, Context context) {
        Drawable color = new ColorDrawable(ContextCompat.getColor(context, colorId));
        Drawable image = ContextCompat.getDrawable(context, R.drawable.circular_background);
        LayerDrawable ld = new LayerDrawable(new Drawable[]{image, color});
        return ld;
    }
}