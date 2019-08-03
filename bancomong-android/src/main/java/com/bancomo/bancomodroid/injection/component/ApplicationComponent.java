package com.bancomo.bancomodroid.injection.component;

import android.app.Application;
import android.content.Context;

import com.bancomo.api.DataManager;
import com.bancomo.api.datamanager.DataManagerAuth;
import com.bancomo.api.datamanager.DataManagerCenter;
import com.bancomo.api.datamanager.DataManagerCharge;
import com.bancomo.api.datamanager.DataManagerClient;
import com.bancomo.api.datamanager.DataManagerDataTable;
import com.bancomo.api.datamanager.DataManagerDocument;
import com.bancomo.api.datamanager.DataManagerGroups;
import com.bancomo.api.datamanager.DataManagerLoan;
import com.bancomo.api.datamanager.DataManagerOffices;
import com.bancomo.api.datamanager.DataManagerRunReport;
import com.bancomo.api.datamanager.DataManagerSavings;
import com.bancomo.api.datamanager.DataManagerSearch;
import com.bancomo.api.datamanager.DataManagerStaff;
import com.bancomo.api.datamanager.DataManagerSurveys;
import com.bancomo.api.local.databasehelper.DatabaseHelperCenter;
import com.bancomo.api.local.databasehelper.DatabaseHelperCharge;
import com.bancomo.api.local.databasehelper.DatabaseHelperClient;
import com.bancomo.api.local.databasehelper.DatabaseHelperDataTable;
import com.bancomo.api.local.databasehelper.DatabaseHelperGroups;
import com.bancomo.api.local.databasehelper.DatabaseHelperLoan;
import com.bancomo.api.local.databasehelper.DatabaseHelperOffices;
import com.bancomo.api.local.databasehelper.DatabaseHelperSavings;
import com.bancomo.api.local.databasehelper.DatabaseHelperStaff;
import com.bancomo.api.local.databasehelper.DatabaseHelperSurveys;
import com.bancomo.bancomodroid.activity.pathtracking.PathTrackingService;
import com.bancomo.bancomodroid.injection.ApplicationContext;
import com.bancomo.bancomodroid.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author Rajan Maurya
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PathTrackingService pathTrackingService);

    @ApplicationContext
    Context context();

    Application application();
    DataManager dataManager();

    DataManagerClient dataManagerClient();
    DataManagerGroups dataManagerGroups();
    DataManagerCenter dataManagerCenters();
    DataManagerDataTable dataManagerDataTable();
    DataManagerCharge dataManagerCharge();
    DataManagerOffices dataManagerOffices();
    DataManagerStaff dataManagerStaff();
    DataManagerLoan dataManagerLoan();
    DataManagerSavings dataManagerSavings();
    DataManagerSurveys dataManagerSurveys();
    DataManagerDocument dataManagerDocument();
    DataManagerSearch dataManagerSearch();
    DataManagerRunReport dataManagerRunReport();
    DataManagerAuth dataManagerAuth();


    DatabaseHelperClient databaseHelperClient();
    DatabaseHelperCenter databaseHelperCenter();
    DatabaseHelperGroups databaseHelperGroup();
    DatabaseHelperDataTable databaseHelperDataTable();
    DatabaseHelperCharge databaseHelperCharge();
    DatabaseHelperOffices databaseHelperOffices();
    DatabaseHelperStaff databaseHelperStaff();
    DatabaseHelperLoan databaseHelperLoan();
    DatabaseHelperSavings databaseHelperSavings();
    DatabaseHelperSurveys databaseHelperSurveys();

}
