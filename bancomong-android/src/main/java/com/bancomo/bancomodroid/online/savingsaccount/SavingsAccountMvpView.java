package com.bancomo.bancomodroid.online.savingsaccount;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.client.Savings;
import com.bancomo.objects.organisation.ProductSavings;
import com.bancomo.objects.templates.savings.SavingProductsTemplate;

import java.util.List;

/**
 * Created by Rajan Maurya on 8/6/16.
 */
public interface SavingsAccountMvpView extends MvpView {

    void showSavingsAccounts(List<ProductSavings> productSavings);

    void showSavingsAccountCreatedSuccessfully(Savings savings);

    void showSavingsAccountTemplateByProduct(SavingProductsTemplate savingProductsTemplate);

    void showFetchingError(int errorMessage);

    void showFetchingError(String errorMessage);
}
