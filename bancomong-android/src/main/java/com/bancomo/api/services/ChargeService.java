/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.client.Charges;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.templates.clients.ChargeTemplate;
import com.bancomo.services.data.ChargesPayload;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author nellyk
 */
public interface ChargeService {

    @GET(APIEndPoint.CHARGES)
    Observable<ResponseBody> listAllCharges();

    @GET(APIEndPoint.CLIENTS + "/{clientId}/charges/template")
    Observable<ChargeTemplate> getAllChargesS(@Path("clientId") int clientId);

    @GET(APIEndPoint.LOANS + "/{loanId}/charges/template")
    Observable<ResponseBody> getAllChargev3(@Path("loanId") int loanId);

    @GET(APIEndPoint.CLIENTS + "/{clientId}/charges")
    Observable<Page<Charges>> getListOfCharges(@Path("clientId") int clientId,
                                               @Query("offset") int offset,
                                               @Query("limit") int limit);

    @POST(APIEndPoint.CLIENTS + "/{clientId}/charges")
    Observable<Charges> createCharges(@Path("clientId") int clientId,
                                      @Body ChargesPayload chargesPayload);

    @GET(APIEndPoint.LOANS + "/{loanId}/charges")
    Observable<Page<Charges>> getListOfLoanCharges(@Path("loanId") int loanId);


    @POST(APIEndPoint.LOANS + "/{loanId}/charges")
    Observable<Charges> createLoanCharges(@Path("loanId") int loanId,
                                          @Body ChargesPayload chargesPayload);

}
