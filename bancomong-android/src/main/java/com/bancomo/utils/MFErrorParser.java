/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.utils;


import com.google.gson.Gson;
import com.bancomo.objects.bancomoerror.BancoMoError;

import retrofit2.adapter.rxjava.HttpException;
import rx.plugins.RxJavaPlugins;

public class MFErrorParser {

    public static final String LOG_TAG = "MFErrorParser";

    private static Gson gson = new Gson();

    public static BancoMoError parseError(String serverResponse) {
        return gson.fromJson(serverResponse, BancoMoError.class);
    }

    public static String errorMessage(Throwable throwableError) {
        String errorMessage = "";
        try {
            if (throwableError instanceof HttpException) {
                errorMessage = ((HttpException) throwableError).response().errorBody().string();
                errorMessage =  MFErrorParser.parseError(errorMessage).getErrors()
                        .get(0).getDefaultUserMessage();
            }
        } catch (Throwable throwable) {
            RxJavaPlugins.getInstance().getErrorHandler().handleError(throwable);
        }
        return errorMessage;
    }
}
