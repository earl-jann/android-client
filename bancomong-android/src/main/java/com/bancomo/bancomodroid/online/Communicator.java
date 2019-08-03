/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */


package com.bancomo.bancomodroid.online;

import com.bancomo.objects.survey.Scorecard;

/**
 * Created by Nasim Banu on 28,January,2016.
 */
public interface Communicator {
    void passScoreCardData(Scorecard scorecard, int surveyid);
}

