package com.bancomo.bancomodroid.online.documentlist;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.noncore.Document;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface DocumentListMvpView extends MvpView {

    void showDocumentList(List<Document> documents);

    void showDocumentFetchSuccessfully(ResponseBody responseBody);

    void checkPermissionAndRequest();

    void requestPermission();

    void showDocumentActions(int documentId);

    void checkExternalStorageAndCreateDocument();

    void showDocumentRemovedSuccessfully();

    void showDocumentDialog(String documentAction);

    void showEmptyDocuments();

    void showFetchingError(int errorMessage);
}
