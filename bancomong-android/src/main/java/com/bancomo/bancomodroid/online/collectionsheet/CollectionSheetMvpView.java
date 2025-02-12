package com.bancomo.bancomodroid.online.collectionsheet;

import com.bancomo.objects.response.SaveResponse;
import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.db.CollectionSheet;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Rajan Maurya on 7/6/16.
 */
public interface CollectionSheetMvpView extends MvpView {

    void showCollectionSheet(CollectionSheet collectionSheet);

    void showCollectionSheetSuccessfullySaved(SaveResponse saveResponse);

    void showFailedToSaveCollectionSheet(HttpException response);

    void showFetchingError(String s);
}
