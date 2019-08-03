package com.bancomo.bancomodroid.online.surveylist;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.survey.Survey;

import java.util.List;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface SurveyListMvpView extends MvpView {

    void showAllSurvey(List<Survey> surveys);

    void showFetchingError(int errorMessage);
}
