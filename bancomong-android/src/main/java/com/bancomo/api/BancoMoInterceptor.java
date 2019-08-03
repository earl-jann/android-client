/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.api;

import com.bancomo.utils.PrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * @author fomenkoo
 */
public class BancoMoInterceptor implements Interceptor {

    public static final String HEADER_TENANT = "BancoMo-Platform-TenantId";
    public static final String HEADER_AUTH = "Authorization";

    public BancoMoInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request chianrequest = chain.request();
        Builder builder = chianrequest.newBuilder()
                .header(HEADER_TENANT, PrefManager.getTenant());

        if (PrefManager.isAuthenticated())
            builder.header(HEADER_AUTH, PrefManager.getToken());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
