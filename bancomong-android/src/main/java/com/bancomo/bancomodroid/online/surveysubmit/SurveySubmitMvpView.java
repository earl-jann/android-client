package com.bancomo.bancomodroid.online.surveysubmit;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.survey.Scorecard;

/**
 * Created by Rajan Maurya on 08/06/16.
 */
public interface SurveySubmitMvpView extends MvpView {

    void showSurveySubmittedSuccessfully(Scorecard scorecard);

    void showError(int errorMessage);
}

