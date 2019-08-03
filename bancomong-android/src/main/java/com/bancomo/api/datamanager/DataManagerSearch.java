package com.bancomo.api.datamanager;

import com.bancomo.api.BaseApiManager;
import com.bancomo.objects.SearchedEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Rajan Maurya on 12/12/16.
 */
@Singleton
public class DataManagerSearch {

    public final BaseApiManager baseApiManager;

    @Inject
    public DataManagerSearch(BaseApiManager baseApiManager) {
        this.baseApiManager = baseApiManager;
    }

    public Observable<List<SearchedEntity>> searchResources(String query, String resources,
                                                            Boolean exactMatch) {
        return baseApiManager.getSearchApi().searchResources(query, resources, exactMatch);
    }
}
