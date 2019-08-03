/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.survey.Scorecard;
import com.bancomo.objects.survey.Survey;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author
 */
public interface SurveyService {

    @GET(APIEndPoint.SURVEYS)
    Observable<List<Survey>> getAllSurveys();

    @GET(APIEndPoint.SURVEYS + "/{surveyId}")
    Observable<Survey> getSurvey(@Path("surveyId") int surveyId);

    @POST(APIEndPoint.SURVEYS + "/{surveyId}/scorecards")
    Observable<Scorecard> submitScore(@Path("surveyId") int surveyId,
                                      @Body Scorecard scorecardPayload);
}
