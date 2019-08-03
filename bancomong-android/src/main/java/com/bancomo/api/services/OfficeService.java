/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.organisation.Office;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

;

/**
 * @author fomenkoo
 */
public interface OfficeService {
    /**
     * Fetches List of All the Offices
     *
     * @param listOfOfficesCallback
     */
    @GET(APIEndPoint.OFFICES)
    Observable<List<Office>> getAllOffices();
}
