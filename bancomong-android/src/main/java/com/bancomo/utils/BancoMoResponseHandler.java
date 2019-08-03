package com.bancomo.utils;

/**
 * Created by Rajan Maurya on 08/07/16.
 */
public class BancoMoResponseHandler {

    public BancoMoResponseHandler() {

    }

    public static String getResponse() {
        switch (PrefManager.getUserStatus()) {

            case 0:
                return "created successfully";
            case 1:
                return "Saved into DB Successfully";

            default:
                return "";
        }

    }
}
