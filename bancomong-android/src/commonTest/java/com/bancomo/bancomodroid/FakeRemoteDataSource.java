package com.bancomo.bancomodroid;

import com.google.gson.reflect.TypeToken;
import com.bancomo.objects.SearchedEntity;
import com.bancomo.objects.client.Charges;
import com.bancomo.objects.client.Client;
import com.bancomo.objects.client.ClientPayload;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.group.Center;
import com.bancomo.objects.group.CenterWithAssociations;
import com.bancomo.objects.group.Group;
import com.bancomo.objects.bancomoerror.BancoMoError;
import com.bancomo.objects.noncore.Document;

import java.util.List;

/**
 * Created by Rajan Maurya on 18/6/16.
 */
public class FakeRemoteDataSource {

    private static TestDataFactory mTestDataFactory = new TestDataFactory();


    public static Page<Client> getClientList() {
        return mTestDataFactory.getListTypePojo(new TypeToken<Page<Client>>() {
        }, FakeJsonName.CLIENTS_JSON);
    }

    public static List<SearchedEntity> getSearchedEntity() {
        return mTestDataFactory.getListTypePojo(new TypeToken<List<SearchedEntity>>() {
        }, FakeJsonName.SEARCHED_ENTITY_JSON);
    }

    public static Page<Center> getCenters() {
        return mTestDataFactory.getListTypePojo(new TypeToken<Page<Center>>() {
        }, FakeJsonName.CENTERS_JSON);
    }

    public static CenterWithAssociations getCentersGroupAndMeeting() {
        return mTestDataFactory.getObjectTypePojo(CenterWithAssociations.class,
                FakeJsonName.CENTER_WITH_ASSOCIATIONS_JSON);
    }

    public static Page<Charges> getClientCharges() {
        return mTestDataFactory.getListTypePojo(new TypeToken<Page<Charges>>() {
        }, FakeJsonName.CHARGES_JSON);
    }

    public static List<Charges> getLoanCharges() {
        return mTestDataFactory.getListTypePojo(new TypeToken<List<Charges>>() {
        }, FakeJsonName.LOAN_CHARGES_JSON);
    }

    public static List<Document> getDocuments() {
        return mTestDataFactory.getListTypePojo(new TypeToken<List<Document>>() {
        }, FakeJsonName.DOCUMENTS_JSON);
    }

    public static Page<Group> getGroups() {
        return mTestDataFactory.getListTypePojo(new TypeToken<Page<Group>>() {
        }, FakeJsonName.GROUPS_JSON);
    }

    public static List<ClientPayload> getClientPayloads() {
        return mTestDataFactory.getListTypePojo(new TypeToken<List<ClientPayload>>() {
        }, FakeJsonName.CLIENT_PAYLOADS);
    }

    public static BancoMoError getFailureServerResponse() {
        return mTestDataFactory.getObjectTypePojo(BancoMoError.class,
                FakeJsonName.FAILURE_SERVER_RESPONSE);
    }

}
