/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bancomo.api.services.AuthService;
import com.bancomo.api.services.CenterService;
import com.bancomo.api.services.ChargeService;
import com.bancomo.api.services.ClientAccountsService;
import com.bancomo.api.services.ClientService;
import com.bancomo.api.services.DataTableService;
import com.bancomo.api.services.DocumentService;
import com.bancomo.api.services.GroupService;
import com.bancomo.api.services.LoanService;
import com.bancomo.api.services.OfficeService;
import com.bancomo.api.services.RunReportsService;
import com.bancomo.api.services.SavingsAccountService;
import com.bancomo.api.services.SearchService;
import com.bancomo.api.services.StaffService;
import com.bancomo.api.services.SurveyService;
import com.bancomo.utils.JsonDateSerializer;
import com.bancomo.utils.PrefManager;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author fomenkoo
 */
public class BaseApiManager {


    private static Retrofit mRetrofit;
    private static AuthService authApi;
    private static CenterService centerApi;
    private static ClientAccountsService accountsApi;
    private static ClientService clientsApi;
    private static DataTableService dataTableApi;
    private static LoanService loanApi;
    private static SavingsAccountService savingsApi;
    private static ChargeService chargeApi;
    private static SearchService searchApi;
    private static GroupService groupApi;
    private static DocumentService documentApi;
    private static OfficeService officeApi;
    private static StaffService staffApi;
    private static SurveyService surveyApi;
    private static RunReportsService runreportsService;

    public BaseApiManager() {
        createService();
    }

    public static void init() {
        authApi = createApi(AuthService.class);
        centerApi = createApi(CenterService.class);
        accountsApi = createApi(ClientAccountsService.class);
        clientsApi = createApi(ClientService.class);
        dataTableApi = createApi(DataTableService.class);
        loanApi = createApi(LoanService.class);
        savingsApi = createApi(SavingsAccountService.class);
        searchApi = createApi(SearchService.class);
        groupApi = createApi(GroupService.class);
        documentApi = createApi(DocumentService.class);
        officeApi = createApi(OfficeService.class);
        staffApi = createApi(StaffService.class);
        surveyApi = createApi(SurveyService.class);
        chargeApi = createApi(ChargeService.class);
        runreportsService = createApi(RunReportsService.class);
    }

    private static <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    public static void createService() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDateSerializer()).create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(PrefManager.getInstanceUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new BancoMoOkHttpClient().getBancoMoOkHttpClient())
                .build();
        init();
    }

    public AuthService getAuthApi() {
        return authApi;
    }

    public CenterService getCenterApi() {
        return centerApi;
    }

    public ClientAccountsService getAccountsApi() {
        return accountsApi;
    }

    public ClientService getClientsApi() {
        return clientsApi;
    }

    public DataTableService getDataTableApi() {
        return dataTableApi;
    }

    public LoanService getLoanApi() {
        return loanApi;
    }

    public SavingsAccountService getSavingsApi() {
        return savingsApi;
    }

    public SearchService getSearchApi() {
        return searchApi;
    }

    public GroupService getGroupApi() {
        return groupApi;
    }

    public DocumentService getDocumentApi() {
        return documentApi;
    }

    public OfficeService getOfficeApi() {
        return officeApi;
    }

    public StaffService getStaffApi() {
        return staffApi;
    }

    public SurveyService getSurveyApi() {
        return surveyApi;
    }

    public ChargeService getChargeApi() {
        return chargeApi;
    }

    public RunReportsService getRunReportsService() {
        return runreportsService;
    }
}
