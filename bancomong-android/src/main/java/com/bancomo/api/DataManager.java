package com.bancomo.api;

import com.bancomo.api.datamanager.DataManagerClient;
import com.bancomo.api.model.CollectionSheetPayload;
import com.bancomo.api.model.Payload;
import com.bancomo.objects.accounts.loan.LoanApproval;
import com.bancomo.objects.accounts.loan.LoanWithAssociations;
import com.bancomo.objects.accounts.loan.Loans;
import com.bancomo.objects.client.Charges;
import com.bancomo.objects.client.Page;
import com.bancomo.objects.db.CollectionSheet;
import com.bancomo.objects.db.OfflineCenter;
import com.bancomo.objects.group.Center;
import com.bancomo.objects.group.CenterWithAssociations;
import com.bancomo.objects.group.Group;
import com.bancomo.objects.group.GroupWithAssociations;
import com.bancomo.objects.organisation.LoanProducts;
import com.bancomo.objects.organisation.Office;
import com.bancomo.objects.organisation.Staff;
import com.bancomo.objects.response.SaveResponse;
import com.bancomo.objects.templates.clients.ChargeTemplate;
import com.bancomo.objects.templates.loans.GroupLoanTemplate;
import com.bancomo.services.data.ChargesPayload;
import com.bancomo.services.data.GroupLoanPayload;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Rajan Maurya on 4/6/16.
 */

@Singleton
public class DataManager {

    private final BaseApiManager mBaseApiManager;
    private DataManagerClient mDataManagerClient;


    //TODO : This Constructor is temp after splitting the Datamanager layer into Sub DataManager
    public DataManager(BaseApiManager baseApiManager) {
        mBaseApiManager = baseApiManager;
    }

    @Inject
    public DataManager(BaseApiManager baseApiManager,
                       DataManagerClient dataManagerClient) {
        mBaseApiManager = baseApiManager;
        mDataManagerClient = dataManagerClient;
    }

    /**
     * Center API
     */

    public Observable<CenterWithAssociations> getGroupsByCenter(int id) {
        return mBaseApiManager.getCenterApi().getAllGroupsForCenter(id);
    }

    public Observable<List<Center>> getCentersInOffice(int id, Map<String, Object> params) {
        return mBaseApiManager.getCenterApi().getAllCentersInOffice(id, params);
    }

    public Observable<CollectionSheet> getCollectionSheet(long id, Payload payload) {
        return mBaseApiManager.getCenterApi().getCollectionSheet(id, payload);
    }

    public Observable<SaveResponse> saveCollectionSheet(int centerId, CollectionSheetPayload
            collectionSheetPayload) {
        return mBaseApiManager.getCenterApi().saveCollectionSheet(
                centerId, collectionSheetPayload);
    }

    public Observable<SaveResponse> saveCollectionSheetAsync(int id,
                                                             CollectionSheetPayload payload) {
        return mBaseApiManager.getCenterApi().saveCollectionSheetAsync(id, payload);
    }

    public Observable<List<OfflineCenter>> getCenterList(
            String dateFormat, String locale, String meetingDate, int officeId, int staffId) {
        return mBaseApiManager.getCenterApi().getCenterList(dateFormat, locale, meetingDate,
                officeId, staffId);
    }

    /**
     * Charges API
     */
    //TODO Remove this Method After fixing the Charge Test
    public Observable<Page<Charges>> getClientCharges(int clientId, int offset, int limit) {
        return mBaseApiManager.getChargeApi().getListOfCharges(clientId, offset, limit);
    }

    public Observable<ChargeTemplate> getAllChargesV2(int clientId) {
        return mBaseApiManager.getChargeApi().getAllChargesS(clientId);
    }

    public Observable<Charges> createCharges(int clientId, ChargesPayload payload) {
        return mBaseApiManager.getChargeApi().createCharges(clientId, payload);
    }

    public Observable<ResponseBody> getAllChargesV3(int loanId) {
        return mBaseApiManager.getChargeApi().getAllChargev3(loanId);
    }

    public Observable<Charges> createLoanCharges(int loanId, ChargesPayload chargesPayload) {
        return mBaseApiManager.getChargeApi().createLoanCharges(loanId, chargesPayload);
    }

    /**
     * Groups API
     */
    public Observable<GroupWithAssociations> getGroups(int groupid) {
        return mBaseApiManager.getGroupApi().getGroupWithAssociations(groupid);
    }

    public Observable<List<Group>> getGroupsByOffice(int office, Map<String, Object> params) {
        return mBaseApiManager.getGroupApi().getAllGroupsInOffice(office, params);
    }

    /**
     * Offices API
     */
    public Observable<List<Office>> getOffices() {
        return mBaseApiManager.getOfficeApi().getAllOffices();
    }


    /**
     * Staff API
     */
    public Observable<List<Staff>> getStaffInOffice(int officeId) {
        return mBaseApiManager.getStaffApi().getStaffForOffice(officeId);
    }

    public Observable<List<Staff>> getAllStaff() {
        return mBaseApiManager.getStaffApi().getAllStaff();
    }

    /**
     * Loans API
     */

    public Observable<LoanWithAssociations> getLoanTransactions(int loan) {
        return mBaseApiManager.getLoanApi().getLoanWithTransactions(loan);
    }

    public Observable<List<LoanProducts>> getAllLoans() {
        return mBaseApiManager.getLoanApi().getAllLoans();
    }

    public Observable<GroupLoanTemplate> getGroupLoansAccountTemplate(int groupId, int productId) {
        return mBaseApiManager.getLoanApi().getGroupLoansAccountTemplate(groupId, productId);
    }

    public Observable<Loans> createGroupLoansAccount(GroupLoanPayload loansPayload) {
        return mBaseApiManager.getLoanApi().createGroupLoansAccount(loansPayload);
    }


    public Observable<LoanWithAssociations> getLoanRepaySchedule(int loanId) {
        return mBaseApiManager.getLoanApi().getLoanRepaymentSchedule(loanId);
    }

    public Observable<GenericResponse> approveLoan(int loanId, LoanApproval loanApproval) {
        return mBaseApiManager.getLoanApi().approveLoanApplication(loanId, loanApproval);
    }

    public Observable<List<Charges>> getListOfLoanCharges(int loanId) {
        return mBaseApiManager.getLoanApi().getListOfLoanCharges(loanId);
    }

    public Observable<Page<Charges>> getListOfCharges(int clientId) {
        return mBaseApiManager.getLoanApi().getListOfCharges(clientId);
    }
}
