/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.SearchedEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author fomenkoo
 */
public interface SearchService {

    @GET(APIEndPoint.SEARCH)
    Observable<List<SearchedEntity>> searchResources(@Query("query") String clientName,
                                                     @Query("resource") String resources,
                                                     @Query("exactMatch") Boolean exactMatch);
}
