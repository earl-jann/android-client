/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.organisation.Staff;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

;

/**
 * @author fomenkoo
 */
public interface StaffService {

    @GET(APIEndPoint.STAFF + "?status=all")
    Observable<List<Staff>> getStaffForOffice(@Query("officeId") int officeId);


    @GET(APIEndPoint.STAFF)
    Observable<List<Staff>> getAllStaff();

    @GET(APIEndPoint.STAFF + "?isLoanOfficer=true")
    Observable<List<Staff>> getFieldStaffForOffice();

}
