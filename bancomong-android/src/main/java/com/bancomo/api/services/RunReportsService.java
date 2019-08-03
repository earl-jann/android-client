package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.group.CenterInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Rajan Maurya on 05/02/17.
 */
public interface RunReportsService {

    @GET(APIEndPoint.RUNREPORTS + "/GroupSummaryCounts")
    Observable<List<CenterInfo>> getCenterSummaryInfo(
            @Query("R_groupId") int centerId,
            @Query("genericResultSet") boolean genericResultSet);
}
