/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */
package com.bancomo.api.services;

import com.bancomo.api.GenericResponse;
import com.bancomo.api.model.APIEndPoint;
import com.bancomo.objects.accounts.GroupAccounts;
import com.bancomo.objects.client.ActivatePayload;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.group.Group;
import com.bancomo.objects.group.GroupWithAssociations;
import com.bancomo.objects.group.GroupPayload;
import com.bancomo.objects.response.SaveResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author fomenkoo
 */
public interface GroupService {

    @GET(APIEndPoint.GROUPS)
    Observable<Page<Group>> getGroups(@Query("paged") boolean b,
                                      @Query("offset") int offset,
                                      @Query("limit") int limit);

    @GET(APIEndPoint.GROUPS + "/{groupId}?associations=all")
    Observable<GroupWithAssociations> getGroupWithAssociations(@Path("groupId") int groupId);

    @GET(APIEndPoint.GROUPS)
    Observable<List<Group>> getAllGroupsInOffice(@Query("officeId") int officeId,
                                                 @QueryMap Map<String, Object> params);

    @POST(APIEndPoint.GROUPS)
    Observable<SaveResponse> createGroup(@Body GroupPayload groupPayload);

    @GET(APIEndPoint.GROUPS + "/{groupId}")
    Observable<Group> getGroup(@Path("groupId") int groupId);

    @GET(APIEndPoint.GROUPS + "/{groupId}/accounts")
    Observable<GroupAccounts> getGroupAccounts(@Path("groupId") int groupId);

    /**
     * This is the service to activate the Group
     * REST ENT POINT
     * https://demo.openmf.org/bancomo-provider/api/v1/groups/{groupId}?command=activate
     *
     * @param groupId
     * @return GenericResponse
     */
    @POST(APIEndPoint.GROUPS + "/{groupId}?command=activate")
    Observable<GenericResponse> activateGroup(@Path("groupId") int groupId,
                                               @Body ActivatePayload activatePayload);

}
