package com.bancomo.bancomodroid.injection.component;

import com.bancomo.bancomodroid.activity.pathtracking.PathTrackingActivity;
import com.bancomo.bancomodroid.activity.pinpointclient.PinpointClientActivity;
import com.bancomo.bancomodroid.dialogfragments.chargedialog.ChargeDialogFragment;
import com.bancomo.bancomodroid.dialogfragments.datatablerowdialog.DataTableRowDialogFragment;
import com.bancomo.bancomodroid.dialogfragments.documentdialog.DocumentDialogFragment;
import com.bancomo.bancomodroid.dialogfragments.identifierdialog.IdentifierDialogFragment;
import com.bancomo.bancomodroid.online.activate.ActivateFragment;
import com.bancomo.bancomodroid.online.centerdetails.CenterDetailsFragment;
import com.bancomo.bancomodroid.online.datatable.DataTableFragment;
import com.bancomo.bancomodroid.online.loanaccountapproval.LoanAccountApproval;
import com.bancomo.bancomodroid.online.loanaccountdisbursement.LoanAccountDisbursementFragment;
import com.bancomo.bancomodroid.dialogfragments.loanchargedialog.LoanChargeDialogFragment;
import com.bancomo.bancomodroid.online.savingsaccountapproval.SavingsAccountApprovalFragment;
import com.bancomo.bancomodroid.dialogfragments.syncclientsdialog.SyncClientsDialogFragment;
import com.bancomo.bancomodroid.dialogfragments.syncgroupsdialog.SyncGroupsDialogFragment;
import com.bancomo.bancomodroid.injection.PerActivity;
import com.bancomo.bancomodroid.injection.module.ActivityModule;
import com.bancomo.bancomodroid.login.LoginActivity;
import com.bancomo.bancomodroid.offline.offlinedashbarod.OfflineDashboardFragment;
import com.bancomo.bancomodroid.offline.syncclientpayloads.SyncClientPayloadsFragment;
import com.bancomo.bancomodroid.offline.syncgrouppayloads.SyncGroupPayloadsFragment;
import com.bancomo.bancomodroid.offline.syncloanrepaymenttransacition.SyncLoanRepaymentTransactionFragment;
import com.bancomo.bancomodroid.offline.syncsavingsaccounttransaction.SyncSavingsAccountTransactionFragment;
import com.bancomo.bancomodroid.online.centerlist.CenterListFragment;
import com.bancomo.bancomodroid.online.clientcharge.ClientChargeFragment;
import com.bancomo.bancomodroid.online.clientdetails.ClientDetailsFragment;
import com.bancomo.bancomodroid.online.clientidentifiers.ClientIdentifiersFragment;
import com.bancomo.bancomodroid.online.clientlist.ClientListFragment;
import com.bancomo.bancomodroid.online.datatablelistfragment.DataTableListFragment;
import com.bancomo.bancomodroid.online.search.SearchFragment;
import com.bancomo.bancomodroid.online.collectionsheet.CollectionSheetFragment;
import com.bancomo.bancomodroid.online.createnewcenter.CreateNewCenterFragment;
import com.bancomo.bancomodroid.online.createnewclient.CreateNewClientFragment;
import com.bancomo.bancomodroid.online.createnewgroup.CreateNewGroupFragment;
import com.bancomo.bancomodroid.online.datatabledata.DataTableDataFragment;
import com.bancomo.bancomodroid.online.documentlist.DocumentListFragment;
import com.bancomo.bancomodroid.online.generatecollectionsheet.GenerateCollectionSheetFragment;
import com.bancomo.bancomodroid.online.groupdetails.GroupDetailsFragment;
import com.bancomo.bancomodroid.online.grouplist.GroupListFragment;
import com.bancomo.bancomodroid.online.grouploanaccount.GroupLoanAccountFragment;
import com.bancomo.bancomodroid.online.groupslist.GroupsListFragment;
import com.bancomo.bancomodroid.online.loanaccount.LoanAccountFragment;
import com.bancomo.bancomodroid.online.loanaccountsummary.LoanAccountSummaryFragment;
import com.bancomo.bancomodroid.online.loancharge.LoanChargeFragment;
import com.bancomo.bancomodroid.online.loanrepayment.LoanRepaymentFragment;
import com.bancomo.bancomodroid.online.loanrepaymentschedule.LoanRepaymentScheduleFragment;
import com.bancomo.bancomodroid.online.loantransactions.LoanTransactionsFragment;
import com.bancomo.bancomodroid.online.savingaccountsummary.SavingsAccountSummaryFragment;
import com.bancomo.bancomodroid.online.savingaccounttransaction.SavingsAccountTransactionFragment;
import com.bancomo.bancomodroid.online.savingsaccount.SavingsAccountFragment;
import com.bancomo.bancomodroid.online.surveylist.SurveyListFragment;
import com.bancomo.bancomodroid.online.surveysubmit.SurveySubmitFragment;

import dagger.Component;

/**
 * @author Rajan Maurya
 *         This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(CenterListFragment centerListFragment);

    void inject(ClientChargeFragment clientChargeFragment);

    void inject(ClientListFragment clientListFragment);

    void inject(ClientIdentifiersFragment clientIdentifiersFragment);

    void inject(SearchFragment clientSearchFragment);

    void inject(DocumentListFragment documentListFragment);

    void inject(GroupListFragment groupListFragment);

    void inject(GenerateCollectionSheetFragment generateCollectionSheetFragment);

    void inject(CreateNewCenterFragment createNewCenterFragment);

    void inject(CreateNewGroupFragment createNewGroupFragment);

    void inject(CreateNewClientFragment createNewClientFragment);

    void inject(DataTableDataFragment dataTableDataFragment);

    void inject(GroupDetailsFragment groupDetailsFragment);

    void inject(ClientDetailsFragment clientDetailsFragment);

    void inject(LoanAccountSummaryFragment loanAccountSummaryFragment);

    void inject(SavingsAccountSummaryFragment savingsAccountSummaryFragment);

    void inject(LoanChargeFragment loanChargeFragment);

    void inject(SavingsAccountTransactionFragment savingsAccountTransactionFragment);

    void inject(CollectionSheetFragment collectionSheetFragment);

    void inject(GroupsListFragment groupsListFragment);

    void inject(LoanTransactionsFragment loanTransactionsFragment);

    void inject(SavingsAccountFragment savingsAccountFragment);

    void inject(LoanRepaymentFragment loanRepaymentFragment);

    void inject(GroupLoanAccountFragment groupLoanAccountFragment);

    void inject(LoanAccountFragment loanAccountFragment);

    void inject(LoanRepaymentScheduleFragment loanRepaymentScheduleFragment);

    void inject(SurveyListFragment surveyListFragment);

    void inject(SurveySubmitFragment surveySubmitFragment);

    void inject(PinpointClientActivity pinpointClientActivity);

    void inject(ChargeDialogFragment chargeDialogFragment);

    void inject(DataTableRowDialogFragment dataTableRowDialogFragment);

    void inject(DataTableListFragment dataTableListFragment);

    void inject(DocumentDialogFragment documentDialogFragment);

    void inject(LoanAccountApproval loanAccountApproval);

    void inject(LoanAccountDisbursementFragment loanAccountDisbursement);

    void inject(LoanChargeDialogFragment loanChargeDialogFragment);

    void inject(SavingsAccountApprovalFragment savingsAccountApproval);

    void inject(SyncClientPayloadsFragment syncPayloadsFragment);

    void inject(SyncGroupPayloadsFragment syncGroupPayloadsFragment);

    void inject(OfflineDashboardFragment offlineDashboardFragment);

    void inject(SyncLoanRepaymentTransactionFragment syncLoanRepaymentTransactionFragment);

    void inject(SyncClientsDialogFragment syncClientsDialogFragment);

    void inject(SyncSavingsAccountTransactionFragment syncSavingsAccountTransactionFragment);

    void inject(SyncGroupsDialogFragment syncGroupsDialogFragment);

    void inject(IdentifierDialogFragment identifierDialogFragment);

    void inject(PathTrackingActivity pathTrackingActivity);

    void inject(CenterDetailsFragment centerDetailsFragment);

    void inject(ActivateFragment activateClientFragment);

    void inject(DataTableFragment dataTableFragment);
}
