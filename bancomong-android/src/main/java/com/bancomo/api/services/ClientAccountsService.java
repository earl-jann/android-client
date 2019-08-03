/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.accounts.ClientAccounts;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author fomenkoo
 */
public interface ClientAccountsService {

    @GET(APIEndPoint.CLIENTS + "/{clientId}/accounts")
    Observable<ClientAccounts> getAllAccountsOfClient(@Path("clientId") int clientId);
}
