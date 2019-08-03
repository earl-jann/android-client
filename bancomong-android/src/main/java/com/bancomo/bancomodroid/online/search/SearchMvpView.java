package com.bancomo.bancomodroid.online.search;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.SearchedEntity;

import java.util.List;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface SearchMvpView extends MvpView {

    void showUserInterface();

    void showSearchedResources(List<SearchedEntity> searchedEntities);

    void showNoResultFound();

    void showMessage(int message);
}
