package com.bancomo.api.datamanager;

import com.bancomo.api.BaseApiManager;
import com.bancomo.api.local.databasehelper.DatabaseHelperStaff;
import com.bancomo.objects.organisation.Staff;
import com.bancomo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Rajan Maurya on 7/7/16.
 */
@Singleton
public class DataManagerStaff {

    public final BaseApiManager mBaseApiManager;
    public final DatabaseHelperStaff mDatabaseHelperStaff;

    @Inject
    public DataManagerStaff(BaseApiManager baseApiManager,
                            DatabaseHelperStaff databaseHelperStaff) {
        mBaseApiManager = baseApiManager;
        mDatabaseHelperStaff = databaseHelperStaff;
    }


    /**
     * @param officeId
     * @return
     */
    public Observable<List<Staff>> getStaffInOffice(int officeId) {
        switch (PrefManager.getUserStatus()) {
            case 0:
                return mBaseApiManager.getStaffApi().getStaffForOffice(officeId)
                        .concatMap(new Func1<List<Staff>, Observable<? extends List<Staff>>>() {
                            @Override
                            public Observable<? extends List<Staff>> call(List<Staff> staffs) {
                                mDatabaseHelperStaff.saveAllStaffOfOffices(staffs);
                                return Observable.just(staffs);
                            }
                        });
            case 1:
                /**
                 * return all List of Staffs of Office from DatabaseHelperOffices
                 */
                return mDatabaseHelperStaff.readAllStaffOffices(officeId);

            default:
                List<Staff> staffs = new ArrayList<>();
                return Observable.just(staffs);
        }

    }

}
