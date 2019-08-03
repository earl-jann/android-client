package com.bancomo.bancomodroid.online.clientidentifiers;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.noncore.Identifier;

import java.util.List;

/**
 * Created by Rajan Maurya on 06/06/16.
 */
public interface ClientIdentifiersMvpView extends MvpView {

    void showUserInterface();

    void showClientIdentifiers(List<Identifier> identifiers);

    void showEmptyClientIdentifier();

    void showFetchingError(int errorMessage);

    void identifierDeletedSuccessfully();
}