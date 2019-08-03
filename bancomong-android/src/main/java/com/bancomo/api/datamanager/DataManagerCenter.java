package com.bancomo.api.datamanager;

import com.bancomo.api.BaseApiManager;
import com.bancomo.api.GenericResponse;
import com.bancomo.api.local.databasehelper.DatabaseHelperCenter;
import com.bancomo.objects.client.ActivatePayload;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.group.Center;
import com.bancomo.objects.group.CenterWithAssociations;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.response.SaveResponse;
import com.bancomo.services.data.CenterPayload;
import com.bancomo.utils.PrefManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * This DataManager is for Managing Center API, In which Request is going to Server
 * and In Response, We are getting Center API Observable Response using Retrofit2.
 * DataManagerCenter saving response in Database and response to Presenter as accordingly.
 * Created by Rajan Maurya on 28/6/16.
 */
@Singleton
public class DataManagerCenter {

    public final BaseApiManager mBaseApiManager;
    public final DatabaseHelperCenter mDatabaseHelperCenter;

    @Inject
    public DataManagerCenter(BaseApiManager baseApiManager,
                             DatabaseHelperCenter databaseHelperCenter) {
        mBaseApiManager = baseApiManager;
        mDatabaseHelperCenter = databaseHelperCenter;
    }


    /**
     * This Method sending the Request to REST API if UserStatus is 0 and
     * get list of the centers. The response will pass Presenter to show in the view
     * <p/>
     * If the offset is zero and UserStatus is 1 then fetch all Center list from Database and show
     * on the view.
     *
     * else if offset is not zero and UserStatus is 1 then return default empty response to
     * presenter
     *
     * @param paged  True Enable the Pagination of the center list REST API
     * @param offset Value give from which position Fetch CentersList
     * @param limit  Maximum Number of centers will come in response
     * @return Centers List page from offset to max Limit
     */
    public Observable<Page<Center>> getCenters(boolean paged, int offset, int limit) {
        switch (PrefManager.getUserStatus()) {
            case 0:
                return mBaseApiManager.getCenterApi().getCenters(paged, offset, limit);

            case 1:
                /**
                 * Return All Centers List from DatabaseHelperCenter only one time.
                 * If offset is zero this means this is first request and
                 * return all clients from DatabaseHelperCenter
                 */
                if (offset == 0)
                    return mDatabaseHelperCenter.readAllCenters();

            default:
                return Observable.just(new Page<Center>());
        }
    }

    /**
     * Method Fetching CollectionSheet of the Center from :
     * demo.openmf.org/bancomo-provider/api/v1/centers/{centerId}
     * ?associations=groupMembers,collectionMeetingCalendar
     *
     * @param id of the center
     * @return Collection Sheet
     */
    public Observable<CenterWithAssociations> getCentersGroupAndMeeting(int id) {
        return mBaseApiManager
                .getCenterApi()
                .getCenterWithGroupMembersAndCollectionMeetingCalendar(id);
    }

    public Observable<SaveResponse> createCenter(CenterPayload centerPayload) {
        return mBaseApiManager.getCenterApi().createCenter(centerPayload);
    }

    public Observable<List<Office>> getOffices() {
        return mBaseApiManager.getOfficeApi().getAllOffices();
    }

    /**
     * This method is activating the center
     *
     * @param centerId
     * @return GenericResponse
     */
    public Observable<GenericResponse> activateCenter(int centerId,
                                                      ActivatePayload activatePayload) {
        return mBaseApiManager.getCenterApi().activateCenter(centerId, activatePayload);
    }
}
