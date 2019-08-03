/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bancomo.bancomodroid.R;
import com.bancomo.objects.db.Client;
import com.bancomo.objects.db.Loan;
import com.bancomo.objects.db.BancoMoGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ishankhanna on 17/07/14.
 */
public class CollectionListAdapter extends BaseExpandableListAdapter {

    //Map for RepaymentTransaction<Loan Id, Transaction Amount>
    //TODO Check about SparseArray in Android and try to convert Map into SparseArray Implementation
    public static final Map<Integer, Double> sRepaymentTransactions = new HashMap<Integer,
            Double>();
    public static List<BancoMoGroup> sBancoMoGroups = new ArrayList<BancoMoGroup>();
    Context context;
    LayoutInflater layoutInflater;

    public CollectionListAdapter(Context context, List<BancoMoGroup> bancoMoGroups) {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        sBancoMoGroups = bancoMoGroups;

        for (BancoMoGroup bancoMoGroup : sBancoMoGroups) {
            for (Client client : bancoMoGroup.getClients()) {
                for (Loan loan : client.getLoans()) {
                    sRepaymentTransactions.put(loan.getLoanId(), loan.getTotalDue());
                }

            }
        }

    }


    @Override
    public int getGroupCount() {
        return sBancoMoGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sBancoMoGroups.get(groupPosition).getClients().size();
    }

    @Override
    public BancoMoGroup getGroup(int groupPosition) {
        return sBancoMoGroups.get(groupPosition);
    }

    @Override
    public Client getChild(int groupPosition, int childPosition) {
        return sBancoMoGroups.get(groupPosition).getClients().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {

        BancoMoGroupReusableViewHolder bancomoGroupReusableViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_collection_list_group, null);
            bancomoGroupReusableViewHolder = new BancoMoGroupReusableViewHolder(convertView);
            convertView.setTag(bancomoGroupReusableViewHolder);
        } else {
            bancomoGroupReusableViewHolder = (BancoMoGroupReusableViewHolder) convertView.getTag();
        }

        double groupTotalDue = 0;

        for (Client client : sBancoMoGroups.get(groupPosition).getClients()) {
            for (Loan loan : client.getLoans()) {
                groupTotalDue += sRepaymentTransactions.get(loan.getLoanId());
            }

        }

        bancomoGroupReusableViewHolder.tv_groupName.setText(sBancoMoGroups.get(groupPosition)
                .getGroupName());
        bancomoGroupReusableViewHolder.tv_groupTotal.setText(String.valueOf(groupTotalDue));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {

        ClientReusableViewHolder clientReusableViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_collection_list_group_client, null);
            clientReusableViewHolder = new ClientReusableViewHolder(convertView);
            convertView.setTag(clientReusableViewHolder);
        } else {
            clientReusableViewHolder = (ClientReusableViewHolder) convertView.getTag();
        }

        Client client = sBancoMoGroups.get(groupPosition).getClients().get(childPosition);
        double totalDue = 0;
        List<Loan> loans = client.getLoans();

        for (Loan loan : loans) {
            totalDue += loan.getTotalDue();
        }

        clientReusableViewHolder.tv_clientId.setText(String.valueOf(client.getClientId()));
        clientReusableViewHolder.tv_clientName.setText(client.getClientName());
        clientReusableViewHolder.tv_clientTotal.setText(String.valueOf(totalDue));

        CollectionSheetLoanAccountListAdapter collectionSheetLoanAccountListAdapter
                = new CollectionSheetLoanAccountListAdapter(context, loans, groupPosition,
                childPosition);
        clientReusableViewHolder.lv_loans.setAdapter(collectionSheetLoanAccountListAdapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class BancoMoGroupReusableViewHolder {

        @BindView(R.id.tv_groupName)
        TextView tv_groupName;
        @BindView(R.id.tv_groupTotal)
        TextView tv_groupTotal;

        public BancoMoGroupReusableViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static class ClientReusableViewHolder {

        @BindView(R.id.tv_clientId)
        TextView tv_clientId;
        @BindView(R.id.tv_clientName)
        TextView tv_clientName;
        @BindView(R.id.tv_clientTotal)
        TextView tv_clientTotal;
        @BindView(R.id.lv_loans)
        ListView lv_loans;

        public ClientReusableViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
